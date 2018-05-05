package engine;

import database.DBConnector;
import enumeration.TypeUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BankUser {

    private String email;
    private PaymentMethod paymentMethod;

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
            int cvv = rs.getInt("CVV");
            double amount = rs.getDouble("AMOUNT");

            this.paymentMethod = new PaymentMethod(num, ownerName, ownerSurname, expirationDate, cvv, amount);

            dbConnector.closeConnection();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public String toString() {
        return "email: " + email + "\ncard number: " + paymentMethod.getNumber() + "\nowner surname: " + paymentMethod.getSurname() + "\nowner name: " + paymentMethod.getSurname() + "\nCVV: " + paymentMethod.getCvv() + "\nexpiration date: " + paymentMethod.getExpirationDate() + "\nAmount: " + paymentMethod.getAmount();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
