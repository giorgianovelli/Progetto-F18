/**
 * This class contains some methods of the actions that a dog sitter can do.
 */

package server;

import database.DBConnector;
import interfaces.InterfaceDogSitter;
import server.bank.PaymentMethod;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;


public class DogSitter extends User implements InterfaceDogSitter {
    /**
     * The places where the dog sitter works.
     */
    private Area area;

    /**
     * The sizes of dogs that the dog sitter accepts.
     */
    private HashSet<DogSize> listDogSize;

    /**
     * The number of dogs that dog sitter accepts simultaneously.
     */
    private int dogsNumber;

    /**
     * A short biography.
     */
    private String biography;

    /**
     * The days in which dog sitter works.
     */
    private Availability dateTimeAvailability;

    /**
     * The flag indicating if dog sitter accepts cash payment
     */
    private boolean acceptCash;

    /**
     * A int costant indicating the number of days in a week.
     */
    final int NWEEKDAYS = 7;



    /**
     * Create a new DogSitter using the following parameters.
     *
     * @param email
     * @param name
     * @param surname
     * @param password
     * @param phoneNumber
     * @param dateOfBirth
     * @param address
     * @param paymentMethod
     * @param area
     * @param listDogSize
     * @param dogsNumber
     * @param biography
     * @param dateTimeAvailability
     * @param acceptCash
     */
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


    /**
     * Create a new DogSitter using the following parameters.
     *
     * @param email
     * @param name
     * @param surname
     * @param password
     * @param phoneNumber
     * @param dateOfBirth
     * @param address
     * @param paymentMethod
     */
    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod) {
        //metodo provvisorio per eseguire test
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }


    /**
     *
     * @return the HashMap of dog sitter's assignments. The Integer key is the code of Assignment.
     */
    public HashMap<Integer, Assignment> getAssignmentList(){
        Singleton singleton = new Singleton();
        return singleton.getDogSitterListAssignmentFromDB(email);
    }


    /**
     *
     * @return the HashMap of reviews. The Integer key is the code of the assignment associated to Review.
     */
    public HashMap<Integer, Review> getReviewList(){
        return reviewList;
    }


    /**
     *
     * @return the Availability of DogSitter.
     */
    public Availability getDateTimeAvailability() {
        return dateTimeAvailability;
    }


    /**
     *
     * @return true if DogSitter accecepts cash payment.
     */
    public boolean isAcceptingCash() {
        return acceptCash;
    }


    /**
     *
     * @return the email address of the dog sitter.
     */
    public String getEmail(){
        return email;
    }


    /**
     *
     * @return the number of dogs that the dog sitter accepts simultaneously.
     */
    public int getDogsNumber() {
        return dogsNumber;
    }


    /**
     *
     * @return the HashSet of DogSize that the dog sitter accepts.
     */
    public HashSet<DogSize> getListDogSize() {
        return listDogSize;
    }


    /**
     *
     * @return the object of type Area containing the cities where the dog sitter works.
     */
    public Area getArea() {
        return area;
    }


    /**
     *
     * @return the dog sitter's Address.
     */
    public Address getAddress(){
        return address;
    }


    /**
     * Print area attribute.
     */
    public void printArea(){
        area.printPlaces();
    }


    /**
     *
     * @return dog sitter's name.
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return dog sitter's surname
     */
    public String getSurname(){
        return surname;
    }


    /**
     * Update the dog sitter's name.
     * @param name
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's surname
     * @param surname
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's password
     * @param password
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's phone number.
     * @param phoneNumber
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's date of birth.
     * @param dateOfBirth
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's Address.
     * @param country
     * @param city
     * @param street
     * @param number
     * @param cap
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's PaymentMethod.
     * @param number
     * @param name
     * @param surname
     * @param expirationDate
     * @param cvv
     * @return true if the update is successfully performed.
     */
    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv){
        double amount = this.paymentMethod.getAmount();
        String oldNum = this.paymentMethod.getNumber();
        this.paymentMethod = new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strExpiration = dateFormat.format(expirationDate);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + number + "', '" + name + "', '" + surname + "', '" + strExpiration + "', '" + cvv + "', " + amount + ")");
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


    /**
     * Update the dog sitter's cash flag.
     * @param acceptCash
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the state of the Assignment related to code.
     * @param code
     * @param state
     * @return true if the update is successfully performed.
     */
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


    /**
     * Add a new city where the dog sitter can work.
     * @param city
     * @return true if the update is successfully performed.
     */
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


    /**
     * Remove a city where the dog sitter can't work no longer.
     * @param city
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the number of dogs that the dog sitter accepts simultaneously.
     * @param nDogs
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the DogSize that the dog sitter accepts.
     * @param small
     * @param medium
     * @param big
     * @param giant
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the dog sitter's availability
     * @param availability
     * @return true if the update is successfully performed.
     */
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
