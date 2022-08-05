/**
 * Interface to listen for a bell
 *
 * @author Nicolaus Nahler
 */
public interface IBellListener {
    /**
     * an enum to represent the different alarm intensities
     */
    enum AlarmIntensity {
        OFF, ON_33_PERCENT, ON_67_PERCENT, ON_100_PERCENT;
    }

    /**
     * sets the wanted alarm intensity
     *
     * @param alarmIntensity the alarm intensity
     */
    void ringBell(AlarmIntensity alarmIntensity);
}
