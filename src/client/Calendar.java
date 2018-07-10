/**
 * This class contains some methods for handling a calendar
 */

package client;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Calendar {

    /**
     * Check if a year is leap.
     * @param yearToCheck the year we want to know if it is leap.
     * @return true if yearToCheck is leap.
     */
    public static boolean isLeap(Date yearToCheck){
        SimpleDateFormat date = new SimpleDateFormat("yyyy");
        String strYear = date.format(yearToCheck);
        int year = Integer.parseInt(strYear);
        if (year % 4 == 0){
            if (year % 100 == 0){
                if (year % 400 == 0){
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Get the number of the days of the month.
     * @param monthNumber is the number of the month we want to get the number of day.
     * @param year year is useful for leap check.
     * @return the number of days of a month.
     */
    public static int getNDayOfMonth(int monthNumber, Date year){
        int nd;
        switch (monthNumber){
            case 4:
            case 6:
            case 9:
            case 11:
                nd = 30;
                break;
            case 2:
                if (isLeap(year)){
                    nd = 29;
                } else {
                    nd = 28;
                }
                break;
            default:
                nd = 31;
                break;
        }
        return nd;
    }

    /**
     * Get an int value corresponding to the current year.
     * @return an int corresponding to the current year.
     */
    public static int getCurrentYear(){
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
         Date now = new Date();
         String strDate = dateFormat.format(now);
         return Integer.parseInt(strDate);
    }
}
