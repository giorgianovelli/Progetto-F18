package server.bank;

import java.util.Date;

public class PaymentMethod {
    private String name;
    private String surname;
    private String number;
    private Date expirationDate;
    private int cvv;
    private double amount;

    public PaymentMethod(String number, String name, String surname, Date expirationDate, int cvv, double amount) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
