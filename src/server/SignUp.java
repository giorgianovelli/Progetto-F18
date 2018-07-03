package server;

import database.DBConnector;
import server.bank.PaymentMethod;
import server.dateTime.WorkingTime;
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

    public boolean dogSitterSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                                   Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogsNumber,
                                   String biography, Availability dateTimeAvailability, boolean acceptCash){
        DBConnector dbConnector = new DBConnector();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());
        HashSet<String> placeList = area.getPlaces();
        WorkingTime[] workingTimes = dateTimeAvailability.getArrayDays();

        boolean small, medium, big, giant;
        if (listDogSize.contains(DogSize.SMALL)){
            small = true;
        } else {
            small = false;
        }
        if (listDogSize.contains(DogSize.MEDIUM)){
            medium = true;
        } else {
            medium = false;
        }
        if (listDogSize.contains(DogSize.BIG)){
            big = true;
        } else {
            big = false;
        }
        if (listDogSize.contains(DogSize.GIANT)){
            giant = true;
        } else {
            giant = false;
        }


        try {
            dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + paymentMethod.getNumber() + "', '" + paymentMethod.getName() + "', '" + paymentMethod.getSurname() + "', '" + strExpiration + "', " + paymentMethod.getCvv() + ", " + 0 + ");");
            dbConnector.updateDB("INSERT INTO ADDRESS VALUES ('" + email + "', '" + address.getCountry() + "', '" + address.getCity() + "', '" + address.getStreet() + "', '" + address.getNumber() + "', '" + address.getCap() + "');");

            dbConnector.updateDB("INSERT INTO DOGSITTERS VALUES ('" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + phoneNumber + "', '" + strBirth + "', '" + paymentMethod.getNumber() + "', " + acceptCash + ", " + dogsNumber + ", '" + biography + "');");

            for (String place : placeList) {
                dbConnector.updateDB("INSERT INTO DOGSITTER_AREA VALUES ('" + email + "', '" + place + "');");
            }

            dbConnector.updateDB("INSERT INTO AVAILABILITY VALUES ('" + email + "', '" + workingTimes[0].getStart() + "', '" + workingTimes[0].getEnd() + "', '" + workingTimes[1].getStart() + "', '" + workingTimes[1].getEnd() + "', '" + workingTimes[2].getStart()
                    + "', '" + workingTimes[2].getEnd() + "', '" + workingTimes[3].getStart() + "', '" + workingTimes[3].getEnd() + "', '" + workingTimes[4].getStart() + "', '" + workingTimes[4].getEnd() + "', '" + workingTimes[5].getStart() + "', '" + workingTimes[5].getEnd()
                    + "', '" + workingTimes[6].getStart() + "', '" + workingTimes[6].getEnd() + "');");

            dbConnector.updateDB("INSERT INTO DOGS_ACCEPTED VALUES ('" + email + "', " + small + ", " + medium + ", " + big + ", " + giant + ");");

            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
