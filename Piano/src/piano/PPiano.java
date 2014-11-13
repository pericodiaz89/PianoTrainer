package piano;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 *
 * @author Rodrigo
 */
public class PPiano extends javax.swing.JPanel {

    private PReader parent;
    int pianoKeyLength = 20;
    int pianoBlackKeyLength = 15;

    public PPiano(PReader parent) {
        this.parent = parent;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    // <editor-fold defaultstate="collapsed" desc="Drawing Methods">
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStaff(g);
    }

    private void drawStaff(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();

        int numberKeys = width/pianoKeyLength;

        int rangeMin = 60 - numberKeys;
        int rangeMax = 60 + numberKeys;
        int base = 0;
        for (int i = rangeMin; i < rangeMax; i++) {
            if (PianoHelper.isSharp((byte) Math.abs(i % 12))) {
                if (parent.getPlayingNotes().containsKey((byte)i)){
                    g2.setColor(Color.red);
                }else{
                    g2.setColor(Color.black);
                }
                g2.fillRect(base - pianoBlackKeyLength/2, 0, pianoBlackKeyLength, getHeight() / 2);
            } else {

                if (parent.getPlayingNotes().containsKey((byte)i)){
                    g2.setColor(Color.red);
                    g2.fillRect(base, 0, pianoKeyLength, getHeight());
                }else{
                    g2.setColor(Color.black);
                }
                g2.setColor(Color.black);
                g2.drawRect(base, 0, pianoKeyLength, getHeight());
                base += pianoKeyLength;
            }
        }
    }

    // </editor-fold>
}
