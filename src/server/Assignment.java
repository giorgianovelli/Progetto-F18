package server;

import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Assignment {

    /**
     * The assignment's code.
     */
    private int code;

    /**
     * The list of dogs to be assigned.
     */
    private HashSet<Dog> dogList;

    /**
     * The assignment's start.
     */
    private Date dateStart;

    /**
     * The assignment's end.
     */
    private Date dateEnd;

    /**
     * The assignment's state. True if the assignment is confirmed,
     * null if it is not already confirmed,
     * false if it is rejected by the dog sitter.
     */
    private Boolean state;

    /**
     * The place where customer and dog sitter will meet.
     */
    private Address meetingPoint;


    /**
     * Create a new assignment.
     * @param code the assignment's code.
     * @param dogList the list of dogs to be assigned.
     * @param dateStart the assignment's start.
     * @param dateEnd the assignment's end.
     * @param meetingPoint the place where customer and dog sitter will meet.
     */
    public Assignment(int code, HashSet<Dog> dogList, Date dateStart, Date dateEnd, Address meetingPoint) {
        this.code = code;
        this.dogList = dogList;      //Sostituire tipo String con tipo server.Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = true;
        this.meetingPoint = meetingPoint;
    }


    /**
     * Create a new assignment.
     * @param code the assignment's code.
     * @param dogList the list of dogs to be assigned.
     * @param dateStart the assignment's start.
     * @param dateEnd the assignment's end.
     * @param meetingPoint the place where customer and dog sitter will meet.
     */
    public Assignment(int code, HashSet<Dog> dogList, Date dateStart, Date dateEnd, Boolean state, Address meetingPoint) {
        this.code = code;
        this.dogList = dogList;      //Sostituire tipo String con tipo server.Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = state;
        this.meetingPoint = meetingPoint;
    }


    /**
     * Return the string of information about an assignment.
     * @return the string of information about an assignment.
     */
    @Override
    public String toString() {
        try {
            //return "Customer: " + customer.email + "\nDog sitter: " + dogSitter.email + "\nStart: " + dateStringConverter(dateStart) + " at " + timeStringConverter(dateStart) + "\nEnd: "  + dateStringConverter(dateEnd) + " at " + timeStringConverter(dateEnd) + "\nState: " + state + "\nMeeting point: " + meetingPoint.toString() + "\nDogs:\n" + printDogList();
            return "Code: " + code + "\nStart: " + dateStringConverter(dateStart) + " at " + timeStringConverter(dateStart) + "\nEnd: "  + dateStringConverter(dateEnd) + " at " + timeStringConverter(dateEnd) + "\nState: " + state + "\nMeeting point: " + meetingPoint.toString() + "\nDogs:\n" + printDogList();
        } catch (ParseException e) {
            return "error!";
        }
    }


    /**
     * Convert a date into a string.
     * @param dateToConvert the date to be converted.
     * @return the string corresponding to the date.
     * @throws ParseException
     */
    private String dateStringConverter(Date dateToConvert) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dateToConvert);
    }


    /**
     * Convert a time into a string
     * @param dateToConvert the time to be converted.
     * @return the string corresponding to the date.
     * @throws ParseException
     */
    private String timeStringConverter(Date dateToConvert) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dateToConvert);
    }


    /**
     * Get a string corresponding to the list of dogs.
     * @return the string corresponfing to the list of dogs.
     */
    public String printDogList() {
        String toReturn = "";
        for (Dog d : dogList) {
            toReturn = toReturn + "Dog name: " + d.getName() + "\tBreed: " + d.getBreed() + "\tSize: " + d.getSize() + "\tAge: " + d.getAge() + "\n";
        }
        return toReturn;
    }


    /**
     * Get the string corresponding to the list of the dog's name.
     * @return the string corresponding to the list of dog's name.
     */
    public String printDogNames(){
        String toReturn = "";
        for (Dog d : dogList) {
            toReturn = toReturn + "<html><br>" + d.getName() + "<html/>" + "\n";
        }
        return toReturn;
    }


    /**
     * Get the string corresponding to the list of meeting points.
     * @return
     */
    public String printMeetingPoint() {
        String toReturn = "";
        for (String token: meetingPoint.toString().split(";")) {
            if (!token.isEmpty()) {
                toReturn = toReturn + "<html><br>" +  token +  "<html/>" + "\n";
            }
        }
        return toReturn;
    }


    /**
     * Get the assigment's start.
     * @return the assignment's start.
     */
    public Date getDateStart() {
        return dateStart;
    }


    /**
     * Get the assignment's end.
     * @return the assignment's end.
     */
    public Date getDateEnd() {
        return dateEnd;
    }


    /**
     * Get the assignment's code.
     * @return the assignment's code.
     */
    public int getCode() {
        return code;
    }


    /**
     * Get the list of dogs to be assigned.
     * @return the list of dogs to be assigned.
     */
    public HashSet<Dog> getDogList() {
        return dogList;
    }


    /**
     * Get the assignment's state.
     * @return the assignment's state.
     */
    public Boolean getState() {
        return state;
    }


    /**
     * Get the place where customer and dog sitter will meet.
     * @return the place where customer and dog sitter will meet.
     */
    public Address getMeetingPoint() {
        return meetingPoint;
    }


    /**
     * Set the assignment's state.
     * @param state the assignment's state.
     */
    public void setState(Boolean state) {
        this.state = state;
    }
}
