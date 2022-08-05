/**
 * Interface to listen for a Display to be updated
 *
 * @author Nicolaus Nahler
 */
public interface IDisplayListener {
    /**
     * called when the Display is displayed
     *
     * @param seconds seconds to display
     */
    void displayTime(int seconds);
}
