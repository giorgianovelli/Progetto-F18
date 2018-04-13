import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class User {
    protected String email;
    protected String name;
    protected String surname;
    protected String password;
    protected String phoneNumber;
    protected Date dateOfBirth;
    //protected Address address                   //Creare la classe relativa al tipo Address
    //protected PaymentMethod paymentMethod       //Creare la classe relativa al tipo PaymentMethod

    public User(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

}
