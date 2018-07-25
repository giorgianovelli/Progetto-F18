package server.tools.dateTime;

/**
 *  This class returns type parameters such as : days,hours,minutes and seconds.
 */
public class DateTimeDHMS {
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    /**
     * The constructor of the class.
     */
    public DateTimeDHMS(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Methods getter.
     */
    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * Methods getter.
     */
    public void setDays(int days) {
        this.days = days;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
