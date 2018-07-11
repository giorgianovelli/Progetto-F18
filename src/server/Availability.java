/**
 * The dog sitter's availability.
 */

package server;

import server.dateTime.WorkingTime;
import server.dateTime.WeekDays;

public class Availability {

    /**
     * The array of dog sitter's working time.
     */
    private WorkingTime arrayDays[];

    /**
     * Create a new Availability object,
     */
    public Availability() {
        arrayDays = new WorkingTime[7];
        int i;
        int nWeekDays = 7;
        for (i = 0; i < nWeekDays; i++){
            arrayDays[i] = null;
        }
    }


    /**
     * Set the dog sitter's availability in the specified day.
     * @param workingTime the dog sitter's working time in the specified day.
     * @param weekDay the specified day.
     */
    public void setDayAvailability(WorkingTime workingTime, WeekDays weekDay){
        switch (weekDay){
            case MON:
                arrayDays[0] = workingTime;
                break;
            case TUE:
                arrayDays[1] = workingTime;
                break;
            case WED:
                arrayDays[2] = workingTime;
                break;
            case THU:
                arrayDays[3] = workingTime;
                break;
            case FRI:
                arrayDays[4] = workingTime;
                break;
            case SAT:
                arrayDays[5] = workingTime;
                break;
            case SUN:
                arrayDays[6] = workingTime;
                break;
        }
    }


    /**
     * Get the array of the dog sitter's working time.
     * @return the array of the dog's siiter working time.
     */
    public WorkingTime[] getArrayDays() {
        return arrayDays;
    }
}

