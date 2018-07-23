package server.bank;

import database.DBConnector;
import enumeration.TypeUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * In this class identifies the types of users with their respective credit card.
 */
public class BankUser {

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's payment method.
     */
    private PaymentMethod paymentMethod;


    /**
     * Create a new bank user.
     * @param email the user's email.
     * @param typeUser the user's type.
     */
    public BankUser(String email, TypeUser typeUser) {
        this.email = email;
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        try {
            if (typeUser.equals(TypeUser.CUSTOMER)){
                rs = dbConnector.askDB("SELECT NUM, OWNER_NAME, OWNER_SURNAME, EXPIRATION_DATE, CVV, AMOUNT FROM CUSTOMERS JOIN CREDIT_CARDS ON CUSTOMERS.PAYMENT = CREDIT_CARDS.NUM WHERE EMAIL = '" + email + "'");
            } else {
                rs = dbConnector.askDB("SELECT NUM, OWNER_NAME, OWNER_SURNAME, EXPIRATION_DATE, CVV, AMOUNT FROM DOGSITTERS JOIN CREDIT_CARDS ON DOGSITTERS.PAYMENT = CREDIT_CARDS.NUM WHERE EMAIL = '" + email + "'");
            }

            rs.next();
            String num = rs.getString("NUM");
            String ownerName = rs.getString("OWNER_NAME");
            String ownerSurname = rs.getString("OWNER_SURNAME");
            Date expirationDate = rs.getDate("EXPIRATION_DATE");
            String cvv = rs.getString("CVV");
            double amount = rs.getDouble("AMOUNT");

            this.paymentMethod = new PaymentMethod(num, ownerName, ownerSurname, expirationDate, cvv, amount);

            dbConnector.closeConnection();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }


    /**
     * Get a string of information for a bank user.
     * @return a string of information for a bank user.
     */
    public String toString() {
        return "email: " + email + "\ncard number: " + paymentMethod.getNumber() + "\nowner surname: " + paymentMethod.getSurname() + "\nowner name: " + paymentMethod.getSurname() + "\nCVV: " + paymentMethod.getCvv() + "\nexpiration date: " + paymentMethod.getExpirationDate() + "\nAmount: " + paymentMethod.getAmount();
    }


    /**
     * Get the user's email address.
     * @return
     */
    public String getEmail() {
        return email;
    }


    /**
     * Set the user's email address.
     * @param email the user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Get the user's payment method.
     * @return the user's payment method.
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Set the user's payment method.
     * @param paymentMethod the user's payment method.
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
