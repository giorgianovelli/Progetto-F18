package server;

import database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import engine.*;
import server.bank.Bank;
import server.bank.PaymentMethod;
import server.places.Address;

//import static staticClasses.ObjectCreator.createDogFromDB;
//import static staticClasses.ObjectCreator.getCustomerListAssignmentFromDB;

public class Customer extends User {
    private HashSet<Dog> dogList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        dogList = new HashSet<Dog>(3);
        assignmentList = new HashMap<Integer, Assignment>();
        reviewList = new HashMap<Integer, Review>();
        Singleton singleton = new Singleton();
        assignmentList = singleton.getCustomerListAssignmentFromDB(email);
        reviewList = singleton.getCustomerReviewList(this);
        dogList = getDogListFromDB(email);
    }

    public Assignment addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint) {
        String emailDogSitter = ds.email;
        PlatformEngine platformEngine = new PlatformEngine();

        //chiamata alla classe banca per effettuare la transazione
        boolean testTransaction = true;
        DBConnector dbConnector = new DBConnector();
        int code = -1;
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM ASSIGNMENT");
            rs.last();
            code = rs.getRow() + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bank bank = new Bank();

        //implementare funzione per il calcolo del prezzo della prestazione
        //double amount = 10;
        double price = platformEngine.estimatePriceAssignment(selectedDogs, dateStartAssignment, dateEndAssignment);

        if (bank.isTransactionPossible(email, price)) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            //Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date.setLenient(false);
            Date startAssignment = new Date();
            Date endAssignment = new Date();

            String dateStringStartAssigment = date.format(dateStartAssignment);
            String dateStringEndAssigment = date.format(dateEndAssignment);
            try {
                startAssignment = date.parse(dateStringStartAssigment);
                endAssignment = date.parse(dateStringEndAssigment);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            assignmentList.put(code, assignment);

            //salva la prenotazione nel database

            Timestamp sqlStart = new Timestamp(startAssignment.getTime());
            Timestamp sqlEnd = new Timestamp(endAssignment.getTime());

            try {
                dbConnector.updateDB("INSERT INTO ASSIGNMENT VALUES (" + code + ", '" + email + "', '" + ds.getEmail() + "', TRUE, '" + dateStringStartAssigment + "', '" + dateStringEndAssigment + "')");
                dbConnector.updateDB("INSERT INTO MEETING_POINT VALUES (" + code + ", '" + meetingPoint.getCountry() + "', '" + meetingPoint.getCity() + "', '" + meetingPoint.getStreet() + "', '" + meetingPoint.getCap() + "', '" + meetingPoint.getCap() + "')");
                for (Dog d : dogList) {
                    dbConnector.updateDB("INSERT INTO DOG_ASSIGNMENT VALUES (" + code + ", " + d.getID() + ")");
                }
                //bank.makeBankTransaction(email, emailDogSitter, code, amount);
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            bank.makeBankTransaction(email, emailDogSitter, code, price);

            System.out.println("Assignment completed successfully!");
            System.out.println(assignment.toString());
            return assignment;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return null;
        }
    }

    public boolean removeAssignment(Integer key) {
        Assignment a = null;
        a = assignmentList.get(key);
        if (a != null) {
            assignmentList.remove(key);
            System.out.println("Selected assignment removed!");

            //aggiungere codice per rimuovere la prenotazione dal database
            DBConnector dbConnector = new DBConnector();
            Bank bank = new Bank();
            try {
                //dbConnector.updateDB("DELETE FROM TRANSACTIONS WHERE CODE_ASSIGNMENT = " + a.getCode());
                bank.refundCustomer(a.getCode());
                dbConnector.updateDB("DELETE FROM MEETING_POINT WHERE CODE = " + a.getCode());
                dbConnector.updateDB("DELETE FROM DOG_ASSIGNMENT WHERE CODE = " + a.getCode());
                dbConnector.updateDB("DELETE FROM ASSIGNMENT WHERE CODE = " + a.getCode());
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            System.out.println("Error in removing the selected assignment!");
            return false;
        }
    }

    public Review addReview(int codeAssignment, DogSitter ds, Date dateReview, int rating, String title, String comment) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        String dateStringReview = date.format(dateReview);
        Review review = new Review(codeAssignment, this, ds, dateReview, rating, title.toUpperCase(), comment);

        //salva la recensione nel database
        //sottometodo da implementare

        reviewList.put(codeAssignment, review);
        System.out.println(review.toString());
        return review;
    }

    public boolean removeReview(Integer key) {
        Review r = null;
        r = reviewList.get(key);
        if (r != null) {
            reviewList.remove(key);
            System.out.println("Selected review removed!");

            //aggiungere codice per rimuovere la recensione dal database

            return true;
        } else {
            System.out.println("Error in removing the selected review!");
            return false;
        }
    }

    public HashMap<Integer, Assignment> listAssignment() {
        Assignment a = null;
        for (Integer key : assignmentList.keySet()) {
            a = assignmentList.get(key);
            System.out.println(a.toString());
        }
        if (a == null) {
            System.out.println("There are no assignment available!");
        }
        return assignmentList;
    }

    public HashMap<Integer, Review> listReview() {
        Review r = null;
        for (Integer key : reviewList.keySet()) {
            r = reviewList.get(key);
            System.out.println(r.toString());
        }
        if (r == null) {
            System.out.println("There are no reviews available!");
        }
        return reviewList;
    }

    public HashSet<Dog> addDog(String name, String breed, DogSize size, int age, double weight, int ID) {
        Dog dog = new Dog(name, breed, size, age, weight, ID);
        dogList.add(dog);
        return dogList;
    }

    public HashSet<Dog> removeDog(Dog dog) {
        if (dogList.contains(dog)) {
            dogList.remove(dog);
            return dogList;
        } else {
            return null;
        }
    }

    public HashMap<Integer, Assignment> getAssignmentList() {
        return assignmentList;
    }

    public HashMap<Integer, Review> getReviewList() {
        return reviewList;
    }

    public Address getAddress() {
        return address;
    }

    public HashSet<Dog> getDogList() {
        return dogList;
    }

    private HashSet<Dog> getDogListFromDB(String email) {
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT ID FROM DOGS WHERE OWNER_EMAIL = '" + email + "'");
            while (rs.next()) {
                int dogID = rs.getInt("ID");
                Singleton singleton = new Singleton();
                Dog dog = singleton.createDogFromDB(dogID);
                dogList.add(dog);
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogList;
    }

    public void updateCustomerSettings() {
        //implementare il metodo che inserisce nel DB le nuove impostazioni dell'utente
        //funzione che dovrà chiamare Sam quando svilupperà l'interfaccia GUISettings
    }
}
