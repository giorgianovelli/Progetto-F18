package server.dateTime;

import java.sql.Time;
import java.util.*;

public class WorkingTime {
    private Time start;
    private Time end;

    public WorkingTime(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}