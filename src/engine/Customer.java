package engine;

import database.DBConnector;
import engine.Address;
import engine.Assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import enumeration.DogSize;

import static engine.ObjectCreator.createCustomerFromDB;

public class Customer extends User {
    private HashSet<Dog> dogList;        //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
    private HashMap<String, Assignment> assignmentList;
    private HashMap<String, Review> reviewList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod){
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        dogList = new HashSet<Dog>(3);    //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
        assignmentList = new HashMap<String, Assignment>();
        reviewList = new HashMap<String, Review>();
        getAssignmentsFromDB();
    }

    public Assignment addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog>selectedDogs, Address meetingPoint){
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //crea un oggetto di tipo engine.Assignment e lo aggiunge all'HashMap assignmentList
            Assignment assignment = new Assignment(this, ds, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            date.setLenient(false);
            String dateStringStartAssigment = date.format(dateStartAssignment);
            assignmentList.put(dateStringStartAssigment  + "_" + ds.email + "_" + this.email, assignment);

            //salva la prenotazione nel database
            //sottometodo da implementare

            System.out.println("engine.Assignment completed successfully!");
            System.out.println(assignment.toString());
            return assignment;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return null;
        }
    }

    public boolean removeAssignment(String key){
        Assignment a = null;
        a = assignmentList.get(key);
        if (a != null){
            assignmentList.remove(key);
            System.out.println("Selected assignment removed!");

            //aggiungere codice per rimuovere la prenotazione dal database

            return true;
        } else {
            System.out.println("Error in removing the selected assignment!");
            return false;
        }
    }

    public Review addReview(DogSitter ds, Date dateReview, int rating, String title, String comment){
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        String dateStringReview = date.format(dateReview);
        Review review = new Review(this, ds, dateReview, rating, title.toUpperCase(), comment);

        //salva la recensione nel database
        //sottometodo da implementare

        reviewList.put(dateStringReview  + "_" + ds.email + "_" + this.email, review);
        System.out.println(review.toString());
        return review;
    }

    public boolean removeReview(String key){
        Review r = null;
        r = reviewList.get(key);
        if (r != null){
            reviewList.remove(key);
            System.out.println("Selected review removed!");

            //aggiungere codice per rimuovere la recensione dal database

            return true;
        } else {
            System.out.println("Error in removing the selected review!");
            return false;
        }
    }

    public HashMap<String, Assignment> listAssignment(){
        Assignment a = null;
        for(String key : assignmentList.keySet()){
            a = assignmentList.get(key);
            System.out.println(a.toString());
        }
        if (a == null){
            System.out.println("There are no assignment available!");
        }
        return assignmentList;
    }

    public HashMap<String, Review> listReview(){
        Review r = null;
        for(String key : reviewList.keySet()){
            r = reviewList.get(key);
            System.out.println(r.toString());
        }
        if (r == null){
            System.out.println("There are no reviews available!");
        }
        return reviewList;
    }

    public HashSet<Dog> addDog(String name, String breed, DogSize size, int age, int weight, String ownerName, String ownerSurname, int ID){
        Dog dog = new Dog(name, breed, size, age, weight, ownerName, ownerSurname, ID);
        dogList.add(dog);
        return dogList;
    }

    public HashSet<Dog> removeDog(Dog dog){
        if (dogList.contains(dog)){
            dogList.remove(dog);
            return dogList;
        } else {
            return null;
        }
    }

    private void getAssignmentsFromDB(){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT CODE, CUSTOMER, DOGSITTER, CONFIRMATION, DATE_START, DATE_END FROM ASSIGNMENT WHERE CUSTOMER = '" + email + "'");
            while (rs.next()){
                String code = rs.getString("CODE");
                String customer = rs.getString("CUSTOMER");
                String dogSitter = rs.getString("DOGSITTER");
                boolean state = rs.getBoolean("CONFIRMATION");
                Date dateStart = rs.getDate("DATE_START");
                Date dateEnd = rs.getDate("DATE_END");
                Address meetingPoint = getMeetingPointFromDB(code);
                Customer c = createCustomerFromDB(customer);

                //Completare il metodo quando sarà disponibile createDogSitterFromDB

                //Assignment assignment = new Assignment();
                dbConnector.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Address getMeetingPointFromDB(String code){
        DBConnector dbConnector = new DBConnector();
        Address address = null;
        try {
            ResultSet rs = dbConnector.askDB("SELECT CODE, COUNTRY, CITY, STREET, CNUMBER, CAP FROM MEETING_POINT WHERE CODE = '" + code + "'");
            while (rs.next()){
                String country = rs.getString("COUNTRY");
                String city = rs.getString("CITY");
                String street = rs.getString("STREET");
                String number = rs.getString("CNUMBER");
                String cap = rs.getString("CAP");
                address = new Address(country, city, street, number, cap);
                dbConnector.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}
