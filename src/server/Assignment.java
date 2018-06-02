package server;

import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Assignment {
    private int code;
    private HashSet<Dog> dogList;
    private Date dateStart;
    private Date dateEnd;
    private boolean state;
    private Address meetingPoint;

    public Assignment(int code, HashSet<Dog> dogList, Date dateStart, Date dateEnd, Address meetingPoint) {
        this.code = code;
        this.dogList = dogList;      //Sostituire tipo String con tipo server.Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = true;
        this.meetingPoint = meetingPoint;
    }

    public Assignment(int code, HashSet<Dog> dogList, Date dateStart, Date dateEnd, boolean state, Address meetingPoint) {
        this.code = code;
        this.dogList = dogList;      //Sostituire tipo String con tipo server.Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = state;
        this.meetingPoint = meetingPoint;
    }

    @Override
    public String toString() {
        try {
            //return "Customer: " + customer.email + "\nDog sitter: " + dogSitter.email + "\nStart: " + dateStringConverter(dateStart) + " at " + timeStringConverter(dateStart) + "\nEnd: "  + dateStringConverter(dateEnd) + " at " + timeStringConverter(dateEnd) + "\nState: " + state + "\nMeeting point: " + meetingPoint.toString() + "\nDogs:\n" + printDogList();
            return "Code: " + code + "\nStart: " + dateStringConverter(dateStart) + " at " + timeStringConverter(dateStart) + "\nEnd: "  + dateStringConverter(dateEnd) + " at " + timeStringConverter(dateEnd) + "\nState: " + state + "\nMeeting point: " + meetingPoint.toString() + "\nDogs:\n" + printDogList();
        } catch (ParseException e) {
            return "error!";
        }
    }

    private String dateStringConverter(Date dateToConvert) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = sdf.format(dateToConvert);
        return reportDate;
    }

    private String timeStringConverter(Date dateToConvert) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String reportDate = sdf.format(dateToConvert);
        return reportDate;
    }

    public String printDogList() {
        String toReturn = "";
        for (Dog d : dogList) {
            toReturn = toReturn + "Dog name: " + d.getName() + "\tBreed: " + d.getBreed() + "\tSize: " + d.getSize() + "\tAge: " + d.getAge() + "\n";
        }
        return toReturn;
    }

    public String printDogNames(){
        String toReturn = "";
        for (Dog d : dogList) {
            toReturn = toReturn + "<html><br>" + d.getName() + "<html/>" + "\n";
        }
        return toReturn;
    }

    public String printMeetingPoint() {
        String toReturn = "";
        for (String token: meetingPoint.toString().split(";")) {
            if (!token.isEmpty()) {
                toReturn = toReturn + "<html><br>" +  token +  "<html/>" + "\n";
            }
        }
        return toReturn;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int getCode() {
        return code;
    }

    public HashSet<Dog> getDogList() {
        return dogList;
    }

    public boolean getState() {
        return state;
    }

    public Address getMeetingPoint() {
        return meetingPoint;
    }
}
