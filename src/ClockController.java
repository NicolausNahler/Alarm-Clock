/**
 * Class to virtualize a clock state machine.
 */
public class ClockController implements IButtonListener, IClockListener {
    /**
     * Constructor
     *
     * @param displayListener the clock display listener
     * @param bellListener    the clock bell listener
     */
    public ClockController(ClockDisplayListenerDouble displayListener, ClockBellListenerDouble bellListener) {
        this.displayListener = displayListener;
        this.bellListener = bellListener;
    }

    /**
     * Represents the reset button.
     */
    boolean reset = false;
    /**
     * Represents the m button.
     */
    boolean m = false;
    /**
     * Represents the s button.
     */
    boolean s = false;
    /**
     * Represents the start/stop button.
     */
    boolean start_stop = false;
    /**
     * Represents the beep state.
     */
    boolean beep = false;
    /**
     * Represents the current time, represented by seconds.
     */
    int sec;
    /**
     * Copies the old time, before counting down.
     */
    int old_sec;
    /**
     * Represents the starting state.
     */
    State state = State.DEFAULT;
    /**
     * Represents the clock display listener.
     */
    private final ClockDisplayListenerDouble displayListener;
    /**
     * Represents the clock bell listener.
     */
    private final ClockBellListenerDouble bellListener;

    @Override
    public void tick() {
        state = state.handleTick(this);
        displayListener.displayTime(sec);
        if (beep) {
            bellListener.ringBell(IBellListener.AlarmIntensity.ON_33_PERCENT);
        }
    }

    @Override
    public void buttonPress(ButtonType buttonType) {
        switch (buttonType) {
            case S:
                s = true;
                break;
            case M:
                m = true;
                break;
            case START_STOP:
                start_stop = true;
                break;
            case RESET:
                reset = true;
                break;
        }
    }

    @Override
    public void buttonRelease(ButtonType buttonType) {
        switch (buttonType) {
            case S:
                s = false;
                break;
            case M:
                m = false;
                break;
            case START_STOP:
                start_stop = false;
                break;
            case RESET:
                reset = false;
                break;
        }
    }

    /**
     * states that can be used
     */
    enum State {
        DEFAULT {
            @Override
            State handleTick(ClockController context) {
                context.sec = 0;
                if (context.start_stop) {
                    return UP;
                }
                if (context.m) {
                    context.sec += 60;
                    return M;
                }
                if (context.s) {
                    context.sec++;
                    return S;
                }
                return this;
            }
        }, UP {
            @Override
            State handleTick(ClockController context) {
                if (context.reset || (context.m && context.s)) {
                    return DEFAULT;
                }
                if (context.start_stop || context.m || context.s) {
                    return UP_PAUSE;
                }
                context.sec++;
                return this;
            }
        }, DOWN {
            @Override
            State handleTick(ClockController context) {
                if (context.reset || (context.m && context.s)) {
                    return DEFAULT;
                }
                if (context.start_stop || context.m || context.s) {
                    return DOWN_PAUSE;
                }
                if (context.sec == 0) {
                    return BEEP;
                }
                context.sec--;
                return this;
            }
        }, M {
            @Override
            State handleTick(ClockController context) {
                if (!context.m) {
                    context.old_sec = context.sec;
                    return DOWN_PAUSE;
                }
                context.sec += 60;
                return this;
            }
        }, S {
            @Override
            State handleTick(ClockController context) {
                if (!context.s) {
                    context.old_sec = context.sec;
                    return DOWN_PAUSE;
                }
                context.sec++;
                return this;
            }
        }, UP_PAUSE {
            @Override
            State handleTick(ClockController context) {
                if (context.reset || (context.m && context.s)) {
                    return DEFAULT;
                }
                if (context.start_stop) {
                    context.sec++;
                    return UP;
                }
                if (context.s) {
                    return S;
                }
                if (context.m) {
                    return M;
                }
                return this;
            }
        }, DOWN_PAUSE {
            @Override
            State handleTick(ClockController context) {
                if (context.reset || (context.m && context.s)) {
                    return DEFAULT;
                }
                if (context.start_stop) {
                    context.sec--;
                    return DOWN;
                }
                if (context.m) {
                    return M;
                }
                if (context.s) {
                    return S;
                }
                return this;
            }
        }, BEEP {
            @Override
            State handleTick(ClockController context) {
                if (context.reset || (context.m && context.s)) {
                    return DEFAULT;
                }
                if (context.start_stop) {
                    context.sec = context.old_sec;
                    return DOWN_PAUSE;
                }
                if (context.m || context.s) {
                    return SAVED_TIME;
                }
                return this;
            }
        }, SAVED_TIME {
            @Override
            State handleTick(ClockController context) {
                if (context.start_stop) {
                    context.sec = context.old_sec;
                    return DOWN_PAUSE;
                }
                if (context.m) {
                    context.sec += 60;
                    return M;
                }
                if (context.s) {
                    context.sec++;
                    return S;
                }
                return this;
            }
        };

        /**
         * handles the tick
         *
         * @param context context of the clock
         * @return the next state
         */
        abstract State handleTick(ClockController context);
    }
}
