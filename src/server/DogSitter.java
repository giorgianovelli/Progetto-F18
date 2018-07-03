package server;

import database.DBConnector;
import interfaces.InterfaceDogSitter;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

//import static staticClasses.ObjectCreator.getDogSitterListAssignmentFromDB;

public class DogSitter extends User implements InterfaceDogSitter {
    private Area area;
    private HashSet<DogSize> listDogSize;
    private int dogsNumber;
    private String biography;
    private Availability dateTimeAvailability;
    private boolean acceptCash;
    final int NWEEKDAYS = 7;

    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogsNumber,
                     String biography, Availability dateTimeAvailability, boolean acceptCash) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        this.area = area;
        this.listDogSize = listDogSize;
        this.dogsNumber = dogsNumber;
        this.biography = biography;
        this.dateTimeAvailability = dateTimeAvailability;
        this.acceptCash = acceptCash;
        Singleton singleton = new Singleton();
        this.assignmentList = getAssignmentList();
        this.reviewList = singleton.getDogSitterReviewList(this);
    }

    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod) {
        //metodo provvisorio per eseguire test
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }

    public HashMap<Integer, Assignment> getAssignmentList(){
        Singleton singleton = new Singleton();
        return singleton.getDogSitterListAssignmentFromDB(email);
    }

    public HashMap<Integer, Review> getReviewList(){
        return reviewList;
    }

    public Availability getDateTimeAvailability() {
        return dateTimeAvailability;
    }

    public boolean isAcceptingCash() {
        return acceptCash;
    }

    public String getEmail(){
        return email;
    }

    public int getDogsNumber() {
        return dogsNumber;
    }

    public HashSet<DogSize> getListDogSize() {
        return listDogSize;
    }

    public Area getArea() {
        return area;
    }

    public Address getAddress(){
        return address;
    }

    public void printArea(){
        area.printPlaces();
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public boolean updateName(String name){
        this.name = name;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET NAME = '" + name + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Name for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating name for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSurname(String surname){
        this.surname = surname;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET SURNAME = '" + surname + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Surname for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating surname for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String password){
        this.password = password;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET PASSWORD = '" + password.toUpperCase() + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Password for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating password for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET PHONE_NUMB = '" + phoneNumber + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Phone number for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating phone number for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(dateOfBirth);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET BIRTHDATE = '" + strDate + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Date of birth for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating date of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAddress(String country, String city, String street, String number, String cap){
        this.address = new Address(country, city, street, number, cap);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE ADDRESS SET COUNTRY = '" + country + "', CITY = '" + city + "', STREET = '" + street + "', CNUMBER = '" + number + "', CAP = '" + cap + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Address for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv){
        double amount = this.paymentMethod.getAmount();
        String oldNum = this.paymentMethod.getNumber();
        this.paymentMethod = new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strExpiration = dateFormat.format(expirationDate);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + number + "', '" + name + "', '" + surname + "', '" + strExpiration + "', " + cvv + ", " + amount + ")");
            dbConnector.closeUpdate();

            if (!(isUpdated)) {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

            isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET PAYMENT = '" + number + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (!(isUpdated)) {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

            isUpdated = dbConnector.updateDB("DELETE FROM CREDIT_CARDS WHERE NUM = '" + oldNum + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Payment method for " + this.email + " is now up to date!");
                return true;
            } else {
                System.out.println("Error in updating payment method of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCashFlag(boolean acceptCash){
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET CASH_FLAG = " + acceptCash + " WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Cash flag for " + this.email + " is now up to date!");
                this.acceptCash = acceptCash;
                return true;
            } else {
                System.out.println("Error in updating cash flag for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAssignmentState(int code, Boolean state){
        DBConnector dbConnector = new DBConnector();

        String strState;
        if (state == null){
            strState = "NULL";
        } else {
            strState = state.toString().toUpperCase();
        }

        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE ASSIGNMENT SET CONFIRMATION = '" + strState + "' WHERE CODE = " + code + ";");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("State for assignment with code " + code + " is now up to date!");
                if (state == null){
                    assignmentList.get(code).setState(null);
                } else {
                    assignmentList.get(code).setState(state);
                }
                return true;
            } else {
                System.out.println("Error in updating state for assignment with code " + code + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addNewPlaceArea(String city){
        DBConnector dbConnector = new DBConnector();

        if (area.contains(city)){
            return false;
        } else {
            area.addPlace(city);
            try {
                dbConnector.updateDB("INSERT INTO DOGSITTER_AREA VALUES ('" + email + "', '" + city + "')");
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean removePlaceArea(String city){
        DBConnector dbConnector = new DBConnector();
        if (area.contains(city)){
            area.removePlaces(city);
            try {
                dbConnector.updateDB("DELETE FROM DOGSITTER_AREA WHERE DOGSITTER = '" + email + "' AND CITY = '" + city + "'");
                dbConnector.closeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateDogsNumber(int nDogs){
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGSITTERS SET NDOGS = " + nDogs + " WHERE EMAIL = '" + email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Number of dogs per assignment is now up to date!");
                this.dogsNumber = nDogs;
                return true;
            } else {
                System.out.println("Error in updating number of dogs per assignment!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant){
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGS_ACCEPTED SET SMALL = " + small + ", MEDIUM = " + medium + ", BIG = " + big + ", GIANT = " + giant + " WHERE DOGSITTER = '" + email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("The list of dogs accepted is now up to date!");
                HashSet<DogSize> newDogSizeList = new HashSet<DogSize>();
                if (small){
                    newDogSizeList.add(DogSize.SMALL);
                }
                if (medium){
                    newDogSizeList.add(DogSize.MEDIUM);
                }
                if (big){
                    newDogSizeList.add(DogSize.BIG);
                }
                if (giant){
                    newDogSizeList.add(DogSize.GIANT);
                }
                return true;
            } else {
                System.out.println("Error in updating the list of dogs accepted!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDateTimeAvailability(Availability availability){
        int i;
        WorkingTime[] workingTimeArray = availability.getArrayDays();
        DBConnector dbConnector = new DBConnector();
        String[] strStart = new String[7];
        String[] strEnd = new String[7];
        for (i = 0; i < NWEEKDAYS; i++){
            if ((workingTimeArray[i].getStart() != null) && (workingTimeArray[i].getEnd() != null)){
                strStart[i] = "'" + workingTimeArray[i].getStart().toString() + "'";
                strEnd[i] = "'" + workingTimeArray[i].getEnd().toString() + "'";
            } else {
                strStart[i] = "null";
                strEnd[i] = "null";
            }
        }
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE AVAILABILITY SET MON_START = "
                    + strStart[0] + ", MON_END = " + strEnd[0]
                    + ", TUE_START = " + strStart[1] + ", TUE_END = " + strEnd[1]
                    + ", WED_START = " + strStart[2] + ", WED_END = " + strEnd[2]
                    + ", THU_START = " + strStart[3] + ", THU_END = " + strEnd[3]
                    + ", FRI_START = " + strStart[4] + ", FRI_END = " + strEnd[4]
                    + ", SAT_START = " + strStart[5] + ", SAT_END = " + strEnd[5]
                    + ", SUN_START = " + strStart[6] + ", SUN_END = " + strEnd[6]
                    + " WHERE DOGSITTER = '" + email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Availability is now up to date!");
                this.dateTimeAvailability = availability;
                return true;
            } else {
                System.out.println("Error in updating availability!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
