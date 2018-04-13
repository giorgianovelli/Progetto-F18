import javafx.scene.input.DataFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class Customer extends User {
    private HashSet<String> dogList;        //Sostituire tipo String con tipo Dog quando sarà disponibile la classe

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth){
        super(email, name, surname, password, phoneNumber, dateOfBirth);
        dogList = new HashSet<String>(3);    //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
    }

    public boolean addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<String>selectedDogs) throws ParseException {
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione (blocco provvisorio)
        boolean testTransaction = true;

        if (testTransaction) {

            //salva la prenotazione nel database
            //sottometodo da implementare

            System.out.println("Assignment completed successfully!\nDog sitter: " + ds.email + "\nStart: " + dateStringConverter(dateStartAssignment) + " at " + timeStringConverter(dateStartAssignment) + "\nEnd: " + dateStringConverter(dateEndAssignment) + " at " + timeStringConverter(dateEndAssignment));
            return true;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return false;
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
}
