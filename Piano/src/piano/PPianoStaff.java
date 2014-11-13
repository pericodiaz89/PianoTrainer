/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piano;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import piano.model.Note;

/**
 *
 * @author Rodrigo
 */
public class PPianoStaff extends javax.swing.JPanel {

    private BufferedImage treble;
    private BufferedImage base;
    private BufferedImage sharp;
    private PReader parent;

    public PPianoStaff(PReader parent) {
        try {
            this.parent = parent;
            initComponents();
            treble = ImageIO.read(new File("data/treble_clef.png"));
            base = ImageIO.read(new File("data/base_clef.png"));
            sharp = ImageIO.read(new File("data/note_sharp.png"));
        } catch (IOException ex) {
            Logger.getLogger(PPianoStaff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Drawing Methods">
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStaff(g);
    }

    private void drawStaff(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(getHeight() * 0.005f));
        int heigth = getHeight();
        int deltaY = heigth / 26;
        for (int i = 5; i < 10; i++) {
            g.drawLine(10, (int) (i * deltaY + deltaY * 1.5f), getWidth() - 20, (int) (i * deltaY + deltaY * 1.5f));
        }
        for (int i = 15; i < 20; i++) {
            g.drawLine(10, (int) (i * deltaY + deltaY * 1.5f), getWidth() - 20, (int) (i * deltaY + deltaY * 1.5f));
        }
        g2.setStroke(new BasicStroke(getHeight() * 0.01f));
        g.drawLine(10, (int) (5 * deltaY + deltaY * 1.5f), 10, (int) (19 * deltaY + deltaY * 1.5f));

        int imgHeigth = deltaY * 7;
        int imgWidth = (imgHeigth * treble.getWidth()) / treble.getHeight();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(treble, 10, (int) (4 * deltaY + deltaY * 1.5f), imgWidth, imgHeigth, null);

        imgHeigth = deltaY * 3;
        imgWidth = (imgHeigth * base.getWidth()) / base.getHeight();
        g.drawImage(base, 15, (int) (15 * deltaY + deltaY * 1.5f), imgWidth, imgHeigth, null);

        g2.setStroke(new BasicStroke(getHeight() * 0.005f));
        String notes = "";
        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (getHeight() * 0.1f)));
        synchronized (parent) {
            for (Note n : parent.getPlayingNotes().values()) {
                //System.out.println(n);
                int midiBase = n.getKey() % 12;
                int octave = n.getOctave();
                int step = PianoHelper.getLinePosition(midiBase);
                float diff = ((octave - 4) * 7 + step) / 2f;
                //Draw in the Trebble Section
                if (n.getKey() >= 47) {
                    // Divided by two since the lines and spaces count in the staff
                    int y = (int) ((25 - (15.5 + diff)) * deltaY + deltaY * 1.5f);
                    g2.setColor(Color.blue);
                    g.drawOval((int) (getWidth() * 0.1f), y, (int) (deltaY * 0.9f), (int) (deltaY * 0.9f));
                    if (PianoHelper.isSharp(midiBase)) {
                        g.drawImage(sharp, (int) (getWidth() * 0.075f), y, deltaY, deltaY, null);
                    }
                }
                if (n.getKey() <= 74) {
                    //Draw in the Base Section
                    g2.setColor(Color.red);
                    int y = (int) ((25 - (11.5 + diff)) * deltaY + deltaY * 1.5f);
                    g.drawOval((int) (getWidth() * 0.1f), y, (int) (deltaY * 0.9f), (int) (deltaY * 0.9f));
                    if (PianoHelper.isSharp(midiBase)) {
                        g.drawImage(sharp, (int) (getWidth() * 0.075f), y, deltaY, deltaY, null);
                    }
                }
                notes += n.getKeyName() + " ";
            }
        }
        g2.setColor(Color.black);
        g.drawString(notes, (int) (getWidth() * 0.01f), (int) (getHeight() * 0.1f));
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
