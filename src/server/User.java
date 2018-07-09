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
        this.assignmentList = new HashMap<>();
        this.reviewList = new HashMap<>();
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
