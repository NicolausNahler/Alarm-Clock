/**
 * Class to virtualize a clock bell.
 *
 * @author Nicolaus Nahler
 */
public class ClockBellListenerDouble implements IBellListener {
    /**
     * The intensity of the ringing.
     */
    AlarmIntensity alarmIntensity = AlarmIntensity.ON_33_PERCENT;

    @Override
    public void ringBell(AlarmIntensity alarmIntensity) {
        this.alarmIntensity = alarmIntensity;
        System.out.println(alarmIntensity);
    }
}
