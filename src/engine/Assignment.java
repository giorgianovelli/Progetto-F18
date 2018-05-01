package engine;

import engine.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Assignment {
    private String code;
    private HashSet<Dog> dogList;    //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
    private Date dateStart;
    private Date dateEnd;
    private boolean state;
    private Address meetingPoint;

    public Assignment(String code, HashSet<Dog> dogList, Date dateStart, Date dateEnd, Address meetingPoint) {
        this.code = code;
        this.dogList = dogList;      //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = true;
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

    private String printDogList() {
        String toReturn = "";
        for (Dog d : dogList) {
            toReturn = toReturn + "Dog name: " + d.getName() + "\tBreed: " + d.getBreed() + "\tSize: " + d.getSize() + "\tAge: " + d.getAge() + "\n";
        }
        return toReturn;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }
}
