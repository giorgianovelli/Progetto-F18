package engine;

import enumeration.DogSize;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static staticClasses.ObjectCreator.getDogSitterListAssignmentFromDB;

public class DogSitter extends User {
    private Area area;
    private HashSet<DogSize> listDogSize;
    private int dogNumber;
    private String biography;
    private Availability dateTimeAvailability;
    private boolean acceptCash;
    private HashMap<String, Assignment> listAssignment;
    private HashMap<String, Review> listReview;

    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogNumber,
                     String biography, Availability dateTimeAvailability, boolean acceptCash) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        this.area = area;
        this.listDogSize = listDogSize;
        this.dogNumber = dogNumber;
        this.biography = biography;
        this.dateTimeAvailability = dateTimeAvailability;
        this.acceptCash = acceptCash;
        this.listAssignment = listAssignment = getDogSitterListAssignmentFromDB(this);
    }

    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod) {
        //metodo provvisorio per eseguire test
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }

    public HashMap<String, Assignment> getListAssignment() {
        return listAssignment;
    }

    public Availability getDateTimeAvailability() {
        return dateTimeAvailability;
    }

    public boolean isAcceptingCash() {
        return acceptCash;
    }

    public String getEmail(){
        return email;
    }

    public int getDogNumber() {
        return dogNumber;
    }

    public HashSet<DogSize> getListDogSize() {
        return listDogSize;
    }

    public Area getArea() {
        return area;
    }

    public Address getAddress(){
        return address;
    }

    public void printArea(){
        area.printPlaces();
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }
}
