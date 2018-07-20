package server.dateTime;

import java.sql.Time;

/**
 * This class contains informations about the working time of a dog sitter in a day.
 */
public class WorkingTime {

    /**
     * The time when the dog sitter stats to work.
     */
    private Time start;

    /**
     * The time when the dog sitter ends to work.
     */
    private Time end;



    /**
     * Create a new WorkingTime object.
     * @param start
     * @param end
     */
    public WorkingTime(Time start, Time end) {
        this.start = start;
        this.end = end;
    }


    /**
     * Get the time when the dog sitter starts to work.
     * @return the time when the dog sitter starts to work.
     */
    public Time getStart() {
        return start;
    }


    /**
     * Get the time when the dog sitter ends to work.
     * @return the time when the dog sitter ends to work.
     */
    public Time getEnd() {
        return end;
    }
}