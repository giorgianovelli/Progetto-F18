package staticClasses.dateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTools {
    public static DateTimeDHMS dateTimeDiff(Date d2, Date d1){
        try {
            long millisDiff = d2.getTime() - d1.getTime();
            int seconds = (int) (millisDiff / 1000 % 60);
            int minutes = (int) (millisDiff / 60000 % 60);
            int hours = (int) (millisDiff / 3600000 % 24);
            int days = (int) (millisDiff / 86400000);
            return new DateTimeDHMS(days, hours, minutes, seconds);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
