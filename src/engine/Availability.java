package engine;

import enumeration.WeekDays;

import java.util.*;

public class Availability {

    private WorkingTime arrayDays[];
    private final int NWEEKDAYS = 7;

    public Availability() {
        arrayDays = new WorkingTime[7];
        int i;
        for (i = 0; i < NWEEKDAYS; i++){
            arrayDays[i] = null;
        }
    }

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

    public WorkingTime[] getArrayDays() {
        return arrayDays;
    }
}

