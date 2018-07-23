package server;

import database.DBConnector;
import server.bank.PaymentMethod;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * This class allows registration for new types of users.
 */
public class SignUp {
    /**
     * The number of days for a week.
     */
    private final int NWEEKDAYS = 7;

    /**
     * This method contains all the parameters for a new registration as costumer.
     * @return  false if the email is already used else
     * @return true and update the database with the now costumer registration.
     */
    public boolean customerSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod){

        if (!(checkCustomerEmail(email))){
            System.out.println("Error: email address already used!");
            return false;
        }

        DBConnector dbConnector = new DBConnector();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());

        try {
            if (!(checkIfPaymentMethodExists(paymentMethod.getNumber(), paymentMethod.getCvv()))){
                dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + paymentMethod.getNumber() + "', '" + paymentMethod.getName() + "', '" + paymentMethod.getSurname() + "', '" + strExpiration + "', " + paymentMethod.getCvv() + ", " + paymentMethod.getAmount() + ");");
            } else {
                if (!(checkCvv(paymentMethod.getNumber(), paymentMethod.getCvv()))){
                    return false;
                }
            }

            if (checkCustomerEmail(email) && checkDogSitterEmail(email)){
                dbConnector.updateDB("INSERT INTO ADDRESS VALUES ('" + email + "', '" + address.getCountry() + "', '" + address.getCity() + "', '" + address.getStreet() + "', '" + address.getNumber() + "', '" + address.getCap() + "');");
            }
            dbConnector.updateDB("INSERT INTO CUSTOMERS VALUES ('" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + phoneNumber + "', '" + strBirth + "', '" + paymentMethod.getNumber() + "');");
            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method contains all the parameters for a new registration as dogsitter.
     * @return  false if the email is already used else
     * @return true and update the database with the now dogsitter registration.
     */
    public boolean dogSitterSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                                   Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogsNumber,
                                   String biography, Availability dateTimeAvailability, boolean acceptCash){

        if (!(checkDogSitterEmail(email))){
            System.out.println("Error: email address already used!");
            return false;
        }

        int i;
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
            if (!(checkIfPaymentMethodExists(paymentMethod.getNumber(), paymentMethod.getCvv()))){
                dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + paymentMethod.getNumber() + "', '" + paymentMethod.getName() + "', '" + paymentMethod.getSurname() + "', '" + strExpiration + "', '" + paymentMethod.getCvv() + "', " + paymentMethod.getAmount() + ");");
            } else if (!checkCvv(paymentMethod.getNumber(), paymentMethod.getCvv())){
                return false;
            }

            if (checkCustomerEmail(email) && checkDogSitterEmail(email)){
                dbConnector.updateDB("INSERT INTO ADDRESS VALUES ('" + email + "', '" + address.getCountry() + "', '" + address.getCity() + "', '" + address.getStreet() + "', '" + address.getNumber() + "', '" + address.getCap() + "');");
            }

            dbConnector.updateDB("INSERT INTO DOGSITTERS VALUES ('" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + phoneNumber + "', '" + strBirth + "', '" + paymentMethod.getNumber() + "', " + acceptCash + ", " + dogsNumber + ", '" + biography + "');");

            for (String place : placeList) {
                dbConnector.updateDB("INSERT INTO DOGSITTER_AREA VALUES ('" + email + "', '" + place + "');");
            }

            String[] strStart = new String[7];
            String[] strEnd = new String[7];
            for (i = 0; i < NWEEKDAYS; i++){
                if ((workingTimes[i].getStart() != null) && (workingTimes[i].getEnd() != null)){
                    strStart[i] = "'" + workingTimes[i].getStart().toString() + "'";
                    strEnd[i] = "'" + workingTimes[i].getEnd().toString() + "'";
                } else {
                    strStart[i] = "null";
                    strEnd[i] = "null";
                }
            }

            dbConnector.updateDB("INSERT INTO AVAILABILITY VALUES ('" + email + "', " + strStart[0] + ", " + strEnd[0] + ", " + strStart[1] + ", " + strEnd[1] + ", " + strStart[2]
                    + ", " + strEnd[2] + ", " + strStart[3] + ", " + strEnd[3] + ", " + strStart[4] + ", " + strEnd[4] + ", " + strStart[5] + ", " + strEnd[5]
                    + ", " + strStart[6] + ", " + strEnd[6] + ");");

            dbConnector.updateDB("INSERT INTO DOGS_ACCEPTED VALUES ('" + email + "', " + small + ", " + medium + ", " + big + ", " + giant + ");");

            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method makes a check in the database about
     * @param email of the costumer
     * @return true if the e-mail is not in the database
     */
    private boolean checkCustomerEmail(String email){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        try {
            rs = dbConnector.askDB("SELECT EMAIL FROM CUSTOMERS WHERE EMAIL = '" + email + "'");
            rs.last();
            if (rs.getRow() == 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method makes a check in the database about
     * @param email of the dogsitter
     * @return true if the e-mail is not in the database
     */
    private boolean checkDogSitterEmail(String email){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        try {
            rs = dbConnector.askDB("SELECT EMAIL FROM DOGSITTERS WHERE EMAIL = '" + email + "'");
            rs.last();
            if (rs.getRow() == 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkIfPaymentMethodExists(String number, String cvv){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        try {
            rs = dbConnector.askDB("SELECT NUM FROM CREDIT_CARDS WHERE NUM = '" + number + "'");
            rs.last();
            if (rs.getRow() == 0){
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkCvv(String number, String cvvToVerify){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        try {
            rs = dbConnector.askDB("SELECT CVV FROM CREDIT_CARDS WHERE NUM = '" + number + "'");
            rs.next();
            String cvv = rs.getString("CVV");
            if (cvv.equals(cvvToVerify)){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
