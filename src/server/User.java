package server;

import server.bank.PaymentMethod;
import server.places.Address;

import java.util.Date;
import java.util.HashMap;

public abstract class User {
    protected String email;
    protected String name;
    protected String surname;
    protected String password;
    protected String phoneNumber;
    protected Date dateOfBirth;
    protected Address address;
    protected PaymentMethod paymentMethod;
    protected HashMap<Integer, Assignment> assignmentList;
    protected HashMap<Integer, Review> reviewList;

    public User(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.assignmentList = new HashMap<Integer, Assignment>();
        this.reviewList = new HashMap<Integer, Review>();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
