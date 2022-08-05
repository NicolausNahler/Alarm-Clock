/**
 * Class to virtualize a clock display
 *
 * @author Nicolaus Nahler
 */
public class ClockDisplayListenerDouble implements IDisplayListener {
    /**
     * The seconds with should be displayed
     */
    public int seconds;

    /**
     * Constructor
     */
    public ClockDisplayListenerDouble() {
        seconds = 0;
    }

    @Override
    public void displayTime(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Getter for the seconds
     *
     * @return the seconds
     */
    public String getSeconds() {
        return String.format("%02d", seconds % 60);
    }

    /**
     * Getter for the minutes
     *
     * @return the minutes
     */
    public String getMinutes() {
        return String.format("%02d", seconds / 60);
    }
}
