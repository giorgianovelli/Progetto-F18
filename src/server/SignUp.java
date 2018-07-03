package server;

import database.DBConnector;
import server.bank.PaymentMethod;
import server.places.Address;
import server.places.Area;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class SignUp {
    public boolean customerSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod){
        DBConnector dbConnector = new DBConnector();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());
        try {
            dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + paymentMethod.getNumber() + "', '" + paymentMethod.getName() + "', '" + paymentMethod.getSurname() + "', '" + strExpiration + "', " + paymentMethod.getCvv() + ", " + 0 + ");");
            dbConnector.updateDB("INSERT INTO ADDRESS VALUES ('" + email + "', '" + address.getCountry() + "', '" + address.getCity() + "', '" + address.getStreet() + "', '" + address.getNumber() + "', '" + address.getCap() + "');");
            dbConnector.updateDB("INSERT INTO CUSTOMERS VALUES ('" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + phoneNumber + "', '" + strBirth + "', '" + paymentMethod.getNumber() + "');");
            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public boolean dogSitterSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                                   Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogsNumber,
                                   String biography, Availability dateTimeAvailability, boolean acceptCash){
        DBConnector dbConnector = new DBConnector();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());
        try {
            dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + paymentMethod.getNumber() + "', '" + paymentMethod.getName() + "', '" + paymentMethod.getSurname() + "', '" + strExpiration + "', " + paymentMethod.getCvv() + ", " + 0 + ");");
            dbConnector.updateDB("INSERT INTO ADDRESS VALUES ('" + email + "', '" + address.getCountry() + "', '" + address.getCity() + "', '" + address.getStreet() + "', '" + address.getNumber() + "', '" + address.getCap() + "');");
            dbConnector.updateDB("INSERT INTO CUSTOMERS VALUES ('" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + phoneNumber + "', '" + strBirth + "', '" + paymentMethod.getNumber() + "');");
            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    //TODO metodo signup dogsitter
}
