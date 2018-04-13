import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class TestCustomer {
    public static void main(String[] args) throws ParseException {
        HashSet<String> selectedDogs = new HashSet<String>(2);
        selectedDogs.add("Fuffi");
        selectedDogs.add("Scooby");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        String strBirthC = "01/01/1970 00:00";
        Date birthC = date.parse(strBirthC);
        String strBirthDS = "03/04/1980 00:00";
        Date birthDS = date.parse(strBirthC);
        String strDateStartAssignment = "30/04/2018 11:00";
        Date dateStartAssignment = date.parse(strDateStartAssignment);
        String strDateEndAssignment = "30/05/2018 18:00";
        Date dateEndAssignment = date.parse(strDateEndAssignment);
        Customer c = new Customer("pippo@email.it", "Pippo", "Baudo", "password", "1234567890", birthC);
        DogSitter ds = new DogSitter("paperino@email.it", "Paperino", "Paperini", "pass", "0987654321", birthDS);
        c.addAssignment(ds, dateStartAssignment, dateEndAssignment, selectedDogs);
    }
}
