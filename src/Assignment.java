import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Assignment {
    private Customer customer;
    private DogSitter dogSitter;
    private HashSet<String> dogList;    //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
    private Date dateStart;
    private Date dateEnd;

    public Assignment(Customer customer, DogSitter dogSitter, HashSet<String> dogList, Date dateStart, Date dateEnd) {
        this.customer = customer;
        this.dogSitter = dogSitter;
        this.dogList = dogList;      //Sostituire tipo String con tipo Dog quando sarà disponibile la classe
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        try {
            return "Customer: " + customer.email + "\nDog sitter: " + dogSitter.email + "\nStart: " + dateStringConverter(dateStart) + " at " + timeStringConverter(dateStart) + "\nEnd: "  + dateStringConverter(dateEnd) + " at " + timeStringConverter(dateEnd);
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
}
