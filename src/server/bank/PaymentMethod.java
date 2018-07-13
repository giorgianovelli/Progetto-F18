package server.bank;

import java.util.Date;

public class PaymentMethod {
    /**
     * The owner's name
     */
    private String name;

    /**
     * The owner's surname.
     */
    private String surname;

    /**
     * The card's number.
     */
    private String number;

    /**
     * The card's expiration date.
     */
    private Date expirationDate;

    /**
     * The card's security code.
     */
    private String cvv;

    /**
     * The owner's amount.
     */
    private double amount;


    /**
     * Create a new card.
     * @param number the card's number.
     * @param name the owner's name.
     * @param surname the owner's surname.
     * @param expirationDate the card's expiration date.
     * @param cvv the card's security code.
     * @param amount the owner's amount.
     */
    public PaymentMethod(String number, String name, String surname, Date expirationDate, String cvv, double amount) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.amount = amount;
    }


    /**
     * Get the owner's name.
     * @return the owner's name.
     */
    public String getName() {
        return name;
    }


    /**
     * Get the owner's surname.
     * @return the owner's surname.
     */
    public String getSurname() {
        return surname;
    }


    /**
     * Get the card's number.
     * @return the card's number.
     */
    public String getNumber() {
        return number;
    }


    /**
     * Get the card's expiration date.
     * @return the card's expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }


    /**
     * Get the card's secure code.
     * @return the card's secure code.
     */
    public String getCvv() {
        return cvv;
    }


    /**
     * Get the owner's amount.
     * @return the owner's amount.
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Set the owner's amount.
     * @param amount the owner's amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
