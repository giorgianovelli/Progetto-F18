package server;

import server.bank.PaymentMethod;
import server.places.Address;

import java.util.Date;

public abstract class User {
    protected String email;
    protected String name;
    protected String surname;
    protected String password;
    protected String phoneNumber;
    protected Date dateOfBirth;
    protected Address address;
    protected PaymentMethod paymentMethod;

    public User(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public String getEmail() {
        return email;
    }
}
