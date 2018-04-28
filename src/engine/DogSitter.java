package engine;

import engine.Address;
import enumeration.DogSize;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
    }

    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod) {
        //metodo provvisorio per eseguire test
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }
}
