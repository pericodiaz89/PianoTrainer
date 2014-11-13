package piano;

/**
 *
 * @author Rodrigo
 */
public class PianoHelper {

    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static enum ENotes {

        C, CSharp, D, DSharp, E, F, FSharp, G, GSharp, A, ASharp, B
    };

    public static final int getOctave(byte key) {
        return (key / 12) - 1;
    }

    public static final String getKeyName(byte key) {
        return NOTE_NAMES[key % 12];
    }

    public static final int getLinePosition(int b) {
        switch (b) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
            case 6:
                return 3;
            case 7:
            case 8:
                return 4;
            case 9:
            case 10:
                return 5;
            case 11:
                return 6;
        }
        return -1;
    }

    public static final boolean isSharp(int b) {
        return (b == 1 || b == 3 || b == 6 || b == 8 || b == 10);
    }
}
