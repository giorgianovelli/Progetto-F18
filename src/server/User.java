/**
 * User
 */

package server;

import server.bank.PaymentMethod;
import server.places.Address;

import java.util.Date;
import java.util.HashMap;

public abstract class User {

    /**
     * The user's email address.
     */
    protected String email;

    /**
     * The user's name.
     */
    protected String name;

    /**
     * The user's surname.
     */
    protected String surname;

    /**
     * The user's password.
     */
    protected String password;

    /**
     * The user's phone number.
     */
    protected String phoneNumber;

    /**
     * The user's date of birth.
     */
    protected Date dateOfBirth;

    /**
     * The user's address.
     */
    protected Address address;

    /**
     * The user's payment method.
     */
    protected PaymentMethod paymentMethod;

    /**
     * The user's list of assignments.
     */
    protected HashMap<Integer, Assignment> assignmentList;

    /**
     * The user's list of reviews.
     */
    protected HashMap<Integer, Review> reviewList;


    /**
     * Create a user with properties of the class that extends this class.
     * @param email the user's email.
     * @param name the user's name.
     * @param surname the user's surname.
     * @param password the user's password.
     * @param phoneNumber the user's phone number.
     * @param dateOfBirth the user's date of birth.
     * @param address the user's address.
     * @param paymentMethod the user's payment method.
     */
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


    /**
     * Get the user's email.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Get the user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }


    /**
     * Get the user's surname.
     * @return the user's surname.
     */
    public String getSurname() {
        return surname;
    }


    /**
     * Get the user's password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }


    /**
     * Get the user's phone number.
     * @return the user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * Get the user's date of birth.
     * @return the user's date of birth.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    /**
     * Get the user's address.
     * @return the user's address.
     */
    public Address getAddress() {
        return address;
    }


    /**
     * Get the user's payment method.
     * @return the user's payment method.
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Set the user's email address.
     * @param email the user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Set the user's name.
     * @param name the user's name.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Set the user's surname.
     * @param surname the user's surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }


    /**
     * Set the user's password.
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Set the user's phone number.
     * @param phoneNumber the user's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Set the user's date of birth.
     * @param dateOfBirth the user's date of birth.
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    /**
     * Set the user's address.
     * @param address the user's address.
     */
    public void setAddress(Address address) {
        this.address = address;
    }


    /**
     * Set user's payment method.
     * @param paymentMethod the user's payment method.
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
