import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Customer extends User {
    private HashSet<String> dogList;        //Sostituire tipo String con tipo Dog quando sarà disponibile la classe

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth){
        super(email, name, surname, password, phoneNumber, dateOfBirth);
        dogList = new HashSet<String>(3);    //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
    }

    public boolean addAssignment(DogSitter ds, Date dateAssignment, HashSet<String>selectedDogs){
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //salva la prenotazione nel database
            //sottometodo da implementare

            System.out.println("Assignment completed successfully!\nDog sitter: " + ds.email + "\nStart: " + dateStringConverter() + " at " + timeStringConverter() + "\nEnd: " + dateStringConverter() + " at " + timeStringConverter());
            return true;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return false;
        }
    }

    private String dateStringConverter(){
        //metodo da implementare
        return "dd/mm/yyyy";
    }

    private String timeStringConverter(){
        //metodo da implementare
        return "dd/mm/yyyy";
    }
}
