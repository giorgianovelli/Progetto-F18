import javafx.scene.input.DataFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Customer extends User {
    private HashSet<String> dogList;        //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
    private HashMap<String, Assignment> assignmentList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth){
        super(email, name, surname, password, phoneNumber, dateOfBirth);
        dogList = new HashSet<String>(3);    //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
        assignmentList = new HashMap<String, Assignment>();
    }

    public boolean addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<String>selectedDogs){
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            Assignment assignment = new Assignment(this, ds, selectedDogs, dateStartAssignment, dateEndAssignment);
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            date.setLenient(false);
            String dateStringStartAssigment = date.format(dateStartAssignment);
            assignmentList.put(ds.email + "_" + dateStringStartAssigment, assignment);

            //salva la prenotazione nel database
            //sottometodo da implementare

            System.out.println("Assignment completed successfully!");
            System.out.println(assignment.toString());
            return true;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return false;
        }
    }

    public boolean removeAssignment(String key){
        Assignment a = null;
        a = assignmentList.get(key);
        if (a != null){
            assignmentList.remove(key);
            System.out.println("Selected assignment removed!");
            return true;
        } else {
            System.out.println("Error in removing the selected assignment!");
            return false;
        }
    }
}
