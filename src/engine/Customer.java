package engine;

import database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import enumeration.DogSize;

import static staticClasses.ObjectCreator.createDogFromDB;
import static staticClasses.ObjectCreator.getCustomerListAssignmentFromDB;

public class Customer extends User {
    private HashSet<Dog> dogList;        //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
    private HashMap<String, Assignment> assignmentList;
    private HashMap<String, Review> reviewList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod){
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        dogList = new HashSet<Dog>(3);    //Sostituire tipo String con tipo engine.Dog quando sarà disponibile la classe
        assignmentList = new HashMap<String, Assignment>();
        reviewList = new HashMap<String, Review>();
        assignmentList = getCustomerListAssignmentFromDB(email);
        dogList = getDogListFromDB(email);
    }

    public Assignment addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog>selectedDogs, Address meetingPoint){
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            //Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            date.setLenient(false);
            String dateStringStartAssigment = date.format(dateStartAssignment);
            String code = dateStringStartAssigment  + "_" + ds.email + "_" + this.email;
            Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            assignmentList.put(code, assignment);

            //salva la prenotazione nel database
            //sottometodo da implementare

            System.out.println("Assignment completed successfully!");
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

    public HashSet<Dog> addDog(String name, String breed, DogSize size, int age, double weight, int ID){
        Dog dog = new Dog(name, breed, size, age, weight, ID);
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

    public HashMap<String, Assignment> getAssignmentList() {
        return assignmentList;
    }

    public Address getAddress(){
        return address;
    }

    public HashSet<Dog> getDogList() {
        return dogList;
    }

    private HashSet<Dog> getDogListFromDB(String email){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT ID FROM DOGS WHERE OWNER_EMAIL = '" + email + "'");
            while (rs.next()){
                int dogID = rs.getInt("ID");
                Dog dog = createDogFromDB(dogID);
                dogList.add(dog);
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogList;
    }
}
