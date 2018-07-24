/**
 * This class contains some methods of the actions that a dog sitter can do.
 */

package server;

import database.DBConnector;
import interfaces.InterfaceDogSitter;
import server.bank.Bank;
import server.bank.PaymentMethod;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import javax.print.attribute.standard.NumberUp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
/**
 * This class show the description of a dogsitter.
 */
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
     * @param email dog's sitter email address.
     * @param name dog sitter's name.
     * @param surname dog sitter's surname.
     * @param password dog sitter's password.
     * @param phoneNumber dog sitter's phone number.
     * @param dateOfBirth dog sitter's date of birth.
     * @param address dog sitter's address.
     * @param paymentMethod dog sitter's payment method.
     * @param area dog sitter's area of work.
     * @param listDogSize the list of dogs' sizes accepted by the dog sitter.
     * @param dogsNumber the number of dogs that dog sitter accepts simultaneously.
     * @param biography a short biography.
     * @param dateTimeAvailability dog sitter's availability.
     * @param acceptCash true if dog sitter accepts cash payment.
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
     * @param email dog's sitter email address.
     * @param name dog sitter's name.
     * @param surname dog sitter's surname.
     * @param password dog sitter's password.
     * @param phoneNumber dog sitter's phone number.
     * @param dateOfBirth dog sitter's date of birth.
     * @param address dog sitter's address.
     * @param paymentMethod dog sitter's payment method.
     */
    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                     Address address, PaymentMethod paymentMethod) {
        //metodo provvisorio per eseguire test
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }


    /**
     * Get the list of the assignment of the dog sitter.
     * @return the HashMap of dog sitter's assignments. The Integer key is the code of Assignment.
     */
    public HashMap<Integer, Assignment> getAssignmentList(){
        Singleton singleton = new Singleton();
        return singleton.getDogSitterListAssignmentFromDB(email);
    }


    /**
     * Get the list of the reviews of the dog sitter.
     * @return the HashMap of reviews. The Integer key is the code of the assignment associated to Review.
     */
    public HashMap<Integer, Review> getReviewList(){
        return reviewList;
    }


    /**
     * Get dog sitter's availability.
     * @return dog sitter's availability.
     */
    public Availability getDateTimeAvailability() {
        return dateTimeAvailability;
    }


    /**
     * Get true id dog sitter accepts cash payment.
     * @return true if dog sitter accecepts cash payment.
     */
    public boolean isAcceptingCash() {
        return acceptCash;
    }


    /**
     * Get the dog sitter's email address.
     * @return the email address of the dog sitter.
     */
    public String getEmail(){
        return email;
    }


    /**
     * Get the number of dogs that the dog sitter accepts simultaneously.
     * @return the number of dogs that the dog sitter accepts simultaneously.
     */
    public int getDogsNumber() {
        return dogsNumber;
    }


    /**
     * Get the list of dogs' sizes that the dog sitter accepts.
     * @return the HashSet of DogSize that the dog sitter accepts.
     */
    public HashSet<DogSize> getListDogSize() {
        return listDogSize;
    }


    /**
     * Get the dog sitter's area of work.
     * @return the object of type Area containing the cities where the dog sitter works.
     */
    public Area getArea() {
        return area;
    }


    /**
     * Get the dog sitter's address.
     * @return the dog sitter's address.
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
     * Get the dog sitter's name.
     * @return dog sitter's name.
     */
    public String getName(){
        return name;
    }

    /**
     * Get the dog sitter's surname.
     * @return dog sitter's surname
     */
    public String getSurname(){
        return surname;
    }


    /**
     * Update the dog sitter's name.
     * @param name new dog sitter's name.
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
     * @param surname new dog sitter's surname.
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
     * @param password new dog sitter's password.
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
     * @param phoneNumber new dog sitter's phone number.
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
     * @param dateOfBirth new dog sitter's date of birth.
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
     * @param country the country in which the dog sitter lives.
     * @param city the city where the dog sitter lives.
     * @param street the street in which the dog sitter lives.
     * @param number the civic number
     * @param cap the cap of the city where the dog sitter lives.
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
     * @param number card's number.
     * @param name the owner's name.
     * @param surname thw owner's surname.
     * @param expirationDate the expiration date of the card.
     * @param cvv the card's secure code.
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
     * @param acceptCash true if dog sitter accepts cash payment.
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
     * @param code the code of the assignment.
     * @param state the new state of the assignment.
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
                    if (state == false){
                        if (!refundCustomer(code)){
                           return false;
                        }
                    }
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
     * @param city the city to add.
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
     * @param city the city to remove.
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
     * @param nDogs the new number of dogs that the dog sitter accepts simultaneously.
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
     * @param small if dog sitter will accept small dogs.
     * @param medium if dog sitter will accept medium dogs.
     * @param big if dog sitter will accept big dogs.
     * @param giant if dog sitter will accept giant dogs.
     * @return true if the update is successfully performed.
     */
    public boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant){
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGS_ACCEPTED SET SMALL = " + small + ", MEDIUM = " + medium + ", BIG = " + big + ", GIANT = " + giant + " WHERE DOGSITTER = '" + email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("The list of dogs accepted is now up to date!");
                HashSet<DogSize> newDogSizeList = new HashSet<>();
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
     * @param availability the new dog sitter's availability.
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


    /**
     * Get dog sitter's biography.
     * @return the dog sitter's biography.
     */
    public String getBiography() {
        return biography;
    }


    /**
     * Reply to the customer's review.
     * @param code the code of the assignment associated to the review.
     * @param dogSitterReply the dog sitter's reply.
     * @return true if the update is successfully performed.
     */
    public boolean replyToReview(int code, String dogSitterReply){
        Review r;
        r = reviewList.get(code);
        if (r == null){
            return false;
        }

        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE REVIEW SET REPLY = '" + dogSitterReply + "' WHERE ASSIGNMENT_CODE = " + code + ";");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Reply successfully saved!");
                reviewList.get(code).setReply(dogSitterReply);
                return true;
            } else {
                System.out.println("Error in saving reply!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Get the customer's email of the assignment specified.
     * @param code the code of the assignment specified.
     * @return the customer's email of the assignment specified.
     */
    public String getCustomerEmailOfAssignment(int code){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs;
        String customerEmail;
        try {
            rs = dbConnector.askDB("SELECT CUSTOMER FROM ASSIGNMENT WHERE CODE = " + code);
            rs.next();
            customerEmail = rs.getString("CUSTOMER");
            return customerEmail;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Refund the customer if the payment was with credit card.
     * @param code the assignment's code.
     * @return true if the customer is correctly refunded.
     */
    private boolean refundCustomer(int code){
        Bank bank = new Bank();
        if (bank.refundCustomer(code)){
            return true;
        } else {
            return false;
        }
    }

}
