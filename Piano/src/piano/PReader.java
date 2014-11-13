/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piano;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import piano.model.Note;

/**
 *
 * @author Rodrigo
 */
public class PReader extends javax.swing.JFrame implements Receiver {

    private MidiDevice.Info[] infos;
    private final int channel = 0; // piano
    private Synthesizer synth;
    private MidiChannel[] channels;
    private final HashMap<Byte, Note> playingNotes;
    private MidiDevice device;

    /**
     * Creates new form PTeacher
     */
    public PReader() {
        initComponents();
        playingNotes = new HashMap<>();
        refreshDevices();
        startMidi();
        setLocationByPlatform(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbDevices = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        bRefresh = new javax.swing.JButton();
        pPianoStaff = new PPianoStaff(this);
        pPiano = new PPiano(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Note Reader");

        cbDevices.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select your device" }));
        cbDevices.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDevicesItemStateChanged(evt);
            }
        });

        jLabel1.setText("Device:");

        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });

        pPianoStaff.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pPianoStaffLayout = new javax.swing.GroupLayout(pPianoStaff);
        pPianoStaff.setLayout(pPianoStaffLayout);
        pPianoStaffLayout.setHorizontalGroup(
            pPianoStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pPianoStaffLayout.setVerticalGroup(
            pPianoStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        pPiano.setBackground(new java.awt.Color(255, 255, 255));
        pPiano.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pPianoLayout = new javax.swing.GroupLayout(pPiano);
        pPiano.setLayout(pPianoLayout);
        pPianoLayout.setHorizontalGroup(
            pPianoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        pPianoLayout.setVerticalGroup(
            pPianoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pPianoStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbDevices, 0, 498, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bRefresh))
                    .addComponent(pPiano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDevices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(bRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pPianoStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pPiano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbDevicesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDevicesItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED && cbDevices.getSelectedIndex() > 0) {
            try {
                if (device != null) {
                    device.close();
                }
                device = MidiSystem.getMidiDevice((MidiDevice.Info) cbDevices.getSelectedItem());
                //does the device have any transmitters?
                //if it does, add it to the device list

                //get all transmitters
                List<Transmitter> transmitters = device.getTransmitters();
                //and for each transmitter

                for (int j = 0; j < transmitters.size(); j++) {
                    //create a new receiver
                    transmitters.get(j).setReceiver(this);
                }
                Transmitter trans = device.getTransmitter();
                trans.setReceiver(this);

                //open each device
                device.open();
                System.out.println(device.getDeviceInfo() + " Was Opened");
            } catch (MidiUnavailableException ex) {
                Logger.getLogger(PReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbDevicesItemStateChanged

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        refreshDevices();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void refreshDevices() {
        String first = (String) cbDevices.getItemAt(0);
        cbDevices.removeAllItems();
        cbDevices.addItem(first);
        infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            cbDevices.addItem(info);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bRefresh;
    private javax.swing.JComboBox cbDevices;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pPiano;
    private javax.swing.JPanel pPianoStaff;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="Receiver Methods">
    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] event = message.getMessage();
        synchronized (this) {
            if (event[2] == 0) {
                channels[channel].noteOff(event[1]);
                playingNotes.remove(event[1]);
            } else {
                channels[channel].noteOn(event[1], event[2]);
                playingNotes.put(event[1], new Note(event[1], event[2]));
            }
        }
        pPianoStaff.repaint();
        pPiano.repaint();
    }

    @Override
    public void close() {
        channels[channel].allNotesOff();
    }
    // </editor-fold>

    private void startMidi() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(PReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized HashMap<Byte, Note> getPlayingNotes() {
        return playingNotes;
    }

}
