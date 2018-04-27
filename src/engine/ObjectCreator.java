package engine;

import database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ObjectCreator {
    public static Customer createCustomerFromDB(String customerEmail){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL, NAME, SURNAME, PASSWORD, PHONE_NUMB, BIRTHDATE, PAYMENT FROM CUSTOMERS WHERE EMAIL = '" + customerEmail + "'");
            rs.next();
            String email = rs.getString("EMAIL");
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            String password = rs.getString("PASSWORD");
            String phone = rs.getString("PHONE_NUMB");
            Date birthdate = rs.getDate("BIRTHDATE");
            String payment = rs.getString("PAYMENT");
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT NUM, OWNER_NAME, OWNER_SURNAME, EXPIRATION_DATE, CVV, AMOUNT FROM CREDIT_CARDS WHERE NUM = '" + payment + "'");
            rs.next();
            String num = rs.getString("NUM");
            String ownerName = rs.getString("OWNER_NAME");
            String ownerSurname = rs.getString("OWNER_SURNAME");
            Date expirationDate = rs.getDate("EXPIRATION_DATE");
            int cvv = rs.getInt("CVV");
            double amount = rs.getDouble("AMOUNT");
            PaymentMethod paymentMethod = new PaymentMethod(num, ownerName, ownerSurname, expirationDate, cvv, amount);
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT COUNTRY, CITY, STREET, CNUMBER, CAP FROM ADDRESS WHERE EMAIL = '" + customerEmail + "'");
            rs.next();
            String country = rs.getString("COUNTRY");
            String city = rs.getString("CITY");
            String street = rs.getString("STREET");
            String cnumber = rs.getString("CNUMBER");
            String cap = rs.getString("CAP");
            Address address = new Address(country, city, street, cnumber, cap);
            dbConnector.closeConnection();

            return new Customer(email, name, surname, password, phone, birthdate, address, paymentMethod);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
