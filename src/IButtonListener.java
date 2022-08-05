/**
 * Interface to listen for a Button to be pressed and/or released
 *
 * @author Nicolaus Nahler
 */
public interface IButtonListener {
    /**
     * an enum to represent the different states of the button
     *
     * @param button The button that was pressed or released
     */
    enum ButtonType {
        S, M, START_STOP, RESET
    }

    /**
     * called when a button is pressed
     *
     * @param buttonType button that was pressed
     */
    void buttonPress(ButtonType buttonType);

    /**
     * called when a button is released
     *
     * @param buttonType button that was released
     */
    void buttonRelease(ButtonType buttonType);
}
