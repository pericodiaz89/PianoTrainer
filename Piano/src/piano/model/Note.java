package piano.model;

import piano.PianoHelper;

/**
 *
 * @author Rodrigo
 */
public class Note {

    private byte key;
    private byte volume;

    public Note(byte key, byte volume) {
        this.key = key;
    }

    @Override
    public String toString() {
        return getOctave() + "|" + getKeyName() + " (" + key + ")";
    }

    // <editor-fold defaultstate="collapsed" desc="Get and Sets">

    public byte getKey() {
        return key;
    }

    public void setKey(byte key) {
        this.key = key;
    }

    public byte getVolume() {
        return volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }
    public int getOctave() {
        return PianoHelper.getOctave(key);
    }

    public String getKeyName() {
        return PianoHelper.getKeyName(key);
    }
    // </editor-fold>

}
