import javafx.scene.input.DataFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;

public class Customer extends User {
    private HashSet<Dog> dogList;        //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
    private HashMap<String, Assignment> assignmentList;
    private HashMap<String, Review> reviewList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth){
        super(email, name, surname, password, phoneNumber, dateOfBirth);
        dogList = new HashSet<Dog>(3);    //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
        assignmentList = new HashMap<String, Assignment>();
        reviewList = new HashMap<String, Review>();
    }

    public Assignment addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog>selectedDogs){
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            Assignment assignment = new Assignment(this, ds, selectedDogs, dateStartAssignment, dateEndAssignment);
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            date.setLenient(false);
            String dateStringStartAssigment = date.format(dateStartAssignment);
            assignmentList.put(dateStringStartAssigment  + "_" + ds.email + "_" + this.email, assignment);

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
}
