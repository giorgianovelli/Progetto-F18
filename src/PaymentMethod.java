import java.util.Date;

public class PaymentMethod {
    private String name;
    private String surname;
    private String number;
    private Date expirationDate;
    private int cvv;
    private double amount;

    public PaymentMethod(String name, String surname, String number, Date expirationDate, int cvv, double amount) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.amount = amount;
    }
}
