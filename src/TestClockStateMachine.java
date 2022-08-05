import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test a clock state machine.
 */
public class TestClockStateMachine {
    /**
     * The clock state machine.
     */
    static ClockController csm;
    /**
     * The clock display listener.
     */
    static ClockDisplayListenerDouble display;
    /**
     * The clock bell listener.
     */
    static ClockBellListenerDouble bell;

    @Before
    public void setUp() {
        display = new ClockDisplayListenerDouble();
        bell = new ClockBellListenerDouble();
        csm = new ClockController(display, bell);
    }

    @After
    public void tearDown() {
        csm = null;
        bell = null;
        display = null;
    }

    /**
     * Given the initial state,
     * when no button has been pressed,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testDefaultState() {
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the start/stop button has been pressed and one tick has been made,
     * then the clock should display a time of 00:01.
     */
    @Test
    public void testStart() {
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("01", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the S button has been pressed and one tick has been made,
     * then the clock should display a time of 00:04.
     */
    @Test
    public void testButtonS() {
        csm.buttonPress(IButtonListener.ButtonType.S);
        csm.tick();
        csm.tick();
        csm.tick();
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.S);
        csm.tick();

        assertEquals("00", display.getMinutes());
        assertEquals("04", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the M button has been pressed and one tick has been made,
     * then the clock should display a time of 03:00.
     */
    @Test
    public void testButtonM() {
        csm.buttonPress(IButtonListener.ButtonType.M);
        csm.tick();
        csm.tick();
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.M);
        csm.tick();

        assertEquals("03", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the Start/Stop and Reset buttons have been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testResetFromUP() {
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.buttonPress(IButtonListener.ButtonType.RESET);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.RESET);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the S, Start/Stop and Reset buttons have been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testResetFromDOWN() {
        csm.buttonPress(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.buttonPress(IButtonListener.ButtonType.RESET);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.RESET);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the Start/Stop button has been pressed two times, the Reset button has been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testResetFromUP_PAUSE() {
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.RESET);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.RESET);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the Start/Stop button has been pressed two times, the Reset button has been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testResetFromDOWN_PAUSE() {
        csm.buttonPress(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.RESET);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.RESET);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the S, Start/Stop and Reset buttons have been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testResetFromBEEP() {
        csm.buttonPress(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.RESET);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.RESET);
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }

    /**
     * Given the initial state,
     * when the S, Start/Stop and Reset buttons have been pressed and ticks have been made in between,
     * then the clock should display a time of 00:00.
     */
    @Test
    public void testGoToBEEP() {
        csm.buttonPress(IButtonListener.ButtonType.S);
        csm.tick();
        csm.tick();
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.S);
        csm.tick();
        csm.buttonPress(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.buttonRelease(IButtonListener.ButtonType.START_STOP);
        csm.tick();
        csm.tick();
        csm.tick();
        assertEquals("00", display.getMinutes());
        assertEquals("00", display.getSeconds());
    }
}

