/**
 * This class extends the functionalities of Proxy class
 * with some methods useful for comunitaction between a dog sitter client
 * and the server.
 */

package client.proxy;

import interfaces.InterfaceDogSitter;
import server.*;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class DogSitterProxy extends Proxy implements InterfaceDogSitter {

    /**
     * The dog sitter's email address.
     */
    private String email;


    /**
     * Create a new DogSitterProxy using the email address of a dog sitter.
     * @param email
     */
    public DogSitterProxy(String email) {
        this.email = email;
    }

    /**
     * Create a new DogSitterProxy object.
     * WARNING: use this constructor only for calling method.
     * dogSitterAccessDataVerifier(String inputUser, String inputPasword).
     */
    public DogSitterProxy() {
        this.email = null;
    }


    /**
     * Perform the login of a dog sitter.
     * @param inputUser the email address inserted by the user.
     * @param inputPasword the password inserted by the user.
     * @return true if login is successfully performed.
     */
    public boolean dogSitterAccessDataVerifier(String inputUser, String inputPasword) {
        String clientMsg = "DOGSITTER#ACCESSVERIFIER#" + inputUser + "#" + inputPasword;
        return getReply(clientMsg).equals("true");
    }

    /**
     *
     * @return the HashMap of dog sitter's assignments. The Integer key is the code of the Assignment.
     */
    public HashMap<Integer, Assignment> getAssignmentList() {
        String serverMsg = getReply("DOGSITTER#GETLISTASSIGNMENT#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        HashMap<Integer, Assignment> customerListAssignment = new HashMap<Integer, Assignment>();
        while (tokenMsg.hasMoreTokens()) {
            int code = Integer.parseInt(tokenMsg.nextToken());
            HashSet<Dog> dogList = decodeDogList(tokenMsg.nextToken());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dateStart = new Date();
            Date dateEnd = new Date();
            try {
                dateStart = dateFormat.parse(tokenMsg.nextToken());
                dateEnd = dateFormat.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Boolean state;
            String strState = tokenMsg.nextToken();
            if (strState.equals("true")) {
                state = true;
            } else if (strState.equals("false")) {
                state = false;
            } else {
                state = null;
            }
            Address meetingPoint = decodeMeetingPoint(tokenMsg.nextToken());
            Assignment a = new Assignment(code, dogList, dateStart, dateEnd, state, meetingPoint);
            customerListAssignment.put(code, a);
        }
        return customerListAssignment;
    }

    /**
     * Decode a String corresponding to an HashSet of dogs.
     * @param msg (or fragment of a message) received from the server.
     * @return an HashMap of dogs.
     */
    private HashSet<Dog> decodeDogList(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        HashSet<Dog> dogList = new HashSet<Dog>();
        int ID;
        while (tokenMsg.hasMoreTokens()) {
            StringTokenizer tokenDog = new StringTokenizer(tokenMsg.nextToken(), "&");
            String strID = tokenDog.nextToken();
            ID = Integer.parseInt(strID);
            String name = tokenDog.nextToken();
            String breed = tokenDog.nextToken();
            DogSize size = DogSize.valueOf(tokenDog.nextToken());
            int age = Integer.parseInt(tokenDog.nextToken());
            double weight = Double.parseDouble(tokenDog.nextToken());
            boolean isEnabled;
            isEnabled = tokenDog.nextToken().equals("true");
            Dog d = new Dog(name, breed, size, age, weight, ID, isEnabled);
            dogList.add(d);
        }
        return dogList;
    }


    /**
     *
     * @param code of the assignment.
     * @return the customer's name of the assignment related to code.
     */
    public String getCustomerNameOfAssignment(int code) {
        return getReply("DOGSITTER#GETCUSTOMERNAMEOFASSIGNMENT#" + code);
    }


    /**
     *
     * @param code of the assignment.
     * @return the customer's surname of the assignment related to code.
     */
    public String getCustomerSurnameOfAssignment(int code) {
        return getReply("DOGSITTER#GETCUSTOMERSURNAMEOFASSIGNMENT#" + code);
    }


    /**
     *
     * @param code of the assignment.
     * @return the review related to assignment indicated with code.
     */
    public Review getReview(int code){
        String serverMsg = getReply("DOGSITTER#GETREVIEW#" + code);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(tokenMsg.nextToken());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int rating = Integer.parseInt(tokenMsg.nextToken());
        String title = tokenMsg.nextToken();
        String description = tokenMsg.nextToken();
        String reply = tokenMsg.nextToken();
        return new Review(code, date, rating, title,description, reply);
    }


    /**
     *
     * @return the dog sitter's name.
     */
    public String getName(){
        return getReply("DOGSITTER#GETNAME#" + email);
    }


    /**
     *
     * @return the dog sitter's surname.
     */
    public String getSurname(){
        return getReply("DOGSITTER#GETSURNAME#" + email);
    }


    /**
     *
     * @return the dog sitter's password.
     */
    public String getPassword(){
        return getReply("DOGSITTER#GETPASSWORD#" + email);
    }


    /**
     *
     * @return the dog sitter's phone number.
     */
    public String getPhoneNumber(){
        return getReply("DOGSITTER#GETPHONENUMBER#" + email);
    }


    /**
     *
     * @return the dog sitter's date of birth.
     */
    public Date getDateOfBirth(){
        String serverMsg = getReply("DOGSITTER#GETDATEOFBIRTH#" + email);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(serverMsg);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *
     * @return the dog sitter's address.
     */
    public Address getAddress(){
        String serverMsg = getReply("DOGSITTER#GETADDRESS#" + email);
        return decodeAddress(serverMsg);
    }


    /**
     *
     * @return the dog sitter's payment method.
     */
    public PaymentMethod getPaymentMethod(){
        String serverMsg = getReply("DOGSITTER#GETPAYMENTMETHOD#" + email);
        return decodePaymentMethod(serverMsg);
    }


    /**
     * Update the dog sitter's name.
     * @param name new name inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    public boolean updateName(String name){
        String serverMsg = getReply("DOGSITTER#UPDATENAME#" + email + "#" + name);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's surname.
     * @param surname new surname inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    public boolean updateSurname(String surname){
        String serverMsg = getReply("DOGSITTER#UPDATESURNAME#" + email + "#" + surname);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's password.
     * @param password new password inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    public boolean updatePassword(String password){
        String serverMsg = getReply("DOGSITTER#UPDATEPASSWORD#" + email + "#" + password);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's phone number.
     * @param phoneNumber new phone number inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    public boolean updatePhoneNumber(String phoneNumber){
        String serverMsg = getReply("DOGSITTER#UPDATEPHONENUMBER#" + email + "#" + phoneNumber);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's date of birth.
     * @param dateOfBirth new date of birth inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    public boolean updateDateOfBirth(Date dateOfBirth){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dateFormat.format(dateOfBirth);
        String serverMsg = getReply("DOGSITTER#UPDATEDATEOFBIRTH#" + email + "#" + strDateOfBirth);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's address with the following data inserted by the customer.
     * @param country
     * @param city
     * @param street
     * @param number civic number
     * @param cap
     * @return true if the update is successfully performed.
     */
    public boolean updateAddress(String country, String city, String street, String number, String cap){
        String serverMsg = getReply("DOGSITTER#UPDATEADDRESS#" + email + "#" + country + "#" + city + "#" + street + "#" + number + "#" + cap);
        return serverMsg.equals("true");
    }


    /**
     * Update the dog sitter's payment method with the following data inserted by the customer.
     * @param number card's number
     * @param name owner's name
     * @param surname owner's surname
     * @param expirationDate
     * @param cvv
     * @return true if the update is successfully performed.
     */
    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strExpiration = dateFormat.format(expirationDate);
        String serverMsg = getReply("DOGSITTER#UPDATEPAYMENTMETHOD#" + email + "#" + number + "#" + name + "#" + surname + "#" + strExpiration + "#" + cvv);
        return serverMsg.equals("true");
    }


    /**
     *
     * @return the HashSet of reviews. The Integer value is the code of the Assignment.
     * associated to Review.
     */
    public HashMap<Integer, Review> getReviewList() {
        String serverMsg = getReply("DOGSITTER#GETREVIEWLIST#" + email);
        HashMap<Integer, Review> reviewList = new HashMap<Integer, Review>();
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        while (tokenMsg.hasMoreTokens()){
            int code = Integer.parseInt(tokenMsg.nextToken());
            Review r = getReview(code);
            reviewList.put(code, r);
        }
        return reviewList;
    }


    /**
     * Update the state of assignment indicated with code.
     * @param code of the Assignment.
     * @param state new state of the Assignment.
     * @return true if the update is successfully performed.
     */
    public boolean updateAssignmentState(int code, Boolean state){
        String serverMsg = getReply("DOGSITTER#UPDATEASSIGNMENTSTATE#" + email + "#" + code + "#" + state);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the cashFlag of the dog sitter.
     * @param acceptCash new value of cashFlag.
     * @return true if the update is successfully performed.
     */
    public boolean updateCashFlag(boolean acceptCash){
        String serverMsg = getReply("DOGSITTER#UPDATECASHFLAG#" + email + "#" + acceptCash);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @return the number of dogs accepted simoultaneously by the dog sitter.
     */
    public int getDogsNumber() {
        String serverMsg = getReply("DOGSITTER#GETDOGSNUMBER#" + email);
        return Integer.parseInt(serverMsg);
    }


    /**
     *
     * @return the area of work of dog sitter.
     */
    public Area getArea() {
        String serverMsg = getReply("DOGSITTER#GETAREA#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        Area area = new Area();
        while (tokenMsg.hasMoreTokens()){
            area.addPlace(tokenMsg.nextToken());
        }
        return area;
    }


    /**
     *
     * @return true if dog sitter accepts cash payment.
     */
    public boolean isAcceptingCash() {
        String serverMsg = getReply("DOGSITTER#ISACCEPTINGCASH#" + email);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @return HashSet of DogSize accepted by dog sitter.
     */
    public HashSet<DogSize> getListDogSize(){
        String serverMsg = getReply("DOGSITTER#LISTDOGSIZE#" + email);
        HashSet<DogSize> listDogSize = new HashSet<DogSize>();
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        while (tokenMsg.hasMoreTokens()){
            String strSize = tokenMsg.nextToken();
            DogSize dogSize = DogSize.valueOf(strSize);
            listDogSize.add(dogSize);
        }
        return listDogSize;
    }


    /**
     * Add a new city in which dog sitter works.
     * @param city new city where dog sitter can work.
     * @return true if the update is successfully performed.
     */
    public boolean addNewPlaceArea(String city){
        String serverMsg = getReply("DOGSITTER#ADDNEWPLACEAREA#" + email + "#" + city);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Remove a city in which dog sitter will no longer work.
     * @param city in which dog sitter will non longer work.
     * @return true if city is successfully removed.
     */
    public boolean removePlaceArea(String city){
        String serverMsg = getReply("DOGSITTER#REMOVEPLACEAREA#" + email + "#" + city);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the number of dogs that a dog sitter can accepts simultaneously.
     * @param nDogs new number of dogs that dog sitter can accept simultaneously.
     * @return true if the update is successfully performed.
     */
    public boolean updateDogsNumber(int nDogs){
        String serverMsg = getReply("DOGSITTER#UPDATEDOGSNUMBER#" + email + "#" + nDogs);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @return the dog sitter's availability.
     */
    public Availability getDateTimeAvailability(){
        String serverMsg = getReply("DOGSITTER#GETAVAILABILITY#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        Availability availability = new Availability();
        Time start;
        Time end;
        for (WeekDays wd : WeekDays.values()) {
            String strStart = tokenMsg.nextToken();
            if (!(strStart.equals("null"))){
                start = Time.valueOf(strStart);
            } else {
                start = null;
            }
            String strEnd = tokenMsg.nextToken();
            if (!(strEnd.equals("null"))){
                end = Time.valueOf(strEnd);
            } else {
                end = null;
            }
            WorkingTime workingTime = new WorkingTime(start, end);
            availability.setDayAvailability(workingTime, wd);
        }
        return availability;
    }


    /**
     * Update the dogs' sizes accepted by the dog sitter.
     * @param small
     * @param medium
     * @param big
     * @param giant
     * @return true if the update is successfully performed.
     */
    public boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant){
        String serverMsg = getReply("DOGSITTER#UPDATELISTDOGSIZE#" + email + "#" + small + "#" + medium + "#" + big + "#" + giant);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update dog sitter's availability.
     * @param availability new dog sitter's availability.
     * @return true if the update is successfully performed.
     */
    public boolean updateDateTimeAvailability(Availability availability){
        String clientMsg = "DOGSITTER#UPDATEAVAILABILITY#" + email + "#";
        WorkingTime[] workingTimeArray = availability.getArrayDays();
        for (WeekDays wd : WeekDays.values()) {
            if ((workingTimeArray[wd.ordinal()].getStart().equals(Time.valueOf("00:00:00"))) && (workingTimeArray[wd.ordinal()].getEnd().equals(Time.valueOf("00:00:00")))){
                clientMsg = clientMsg + "null#null#";
            } else {
                clientMsg = clientMsg + workingTimeArray[wd.ordinal()].getStart() + "#" + workingTimeArray[wd.ordinal()].getEnd() +"#";
            }
        }
        String serverMsg = getReply(clientMsg);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    
    /**
     * Get dog sitter's biography.
     * @return the dog sitter's biography.
     */
    public String getBiography(){
        return getReply("DOGSITTER#GETBIOGRAPHY#" + email);
    }

    /**
     * Create a new dog sitter account with the following data.
     * @param email dog sitter's email address.
     * @param name dog sitter's name.
     * @param surname dog sitter's surname.
     * @param password dog sitter's password.
     * @param phoneNumber dog sitter's phone number.
     * @param dateOfBirth dog sitter's date of birth.
     * @param address dog sitter's address.
     * @param paymentMethod dog sitter's payment method.
     * @param area dog sitter's area.
     * @param listDogSize list of dogs' sizes accepted by the dog sitters.
     * @param dogsNumber number of dogs accepted simultaneously by the dog sitters.
     * @param biography a short biography.
     * @param dateTimeAvailability dog sitter's availability.
     * @param acceptCash true if dog sitter accepts cash payment.
     * @return
     */
    public boolean dogSitterSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth,
                                   Address address, PaymentMethod paymentMethod, Area area, HashSet<DogSize> listDogSize, int dogsNumber,
                                   String biography, Availability dateTimeAvailability, boolean acceptCash){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());
        String clientMsg = "DOGSITTER#SIGNUP#" + email + "#" + name + "#" + surname + "#" + password + "#" + phoneNumber
                + "#" + strBirth + "#" + address.getCountry() + "#" + address.getCity() + "#" + address.getStreet() + "#" + address.getNumber()
                + "#" + address.getCap() + "#" + paymentMethod.getNumber() + "#" + paymentMethod.getName() + "#" + paymentMethod.getSurname()
                + "#" + strExpiration + "#" + paymentMethod.getCvv() + "#" + paymentMethod.getAmount() + "#";

        for (String city : area.getPlaces()) {
            clientMsg = clientMsg + city + "*";
        }

        clientMsg = clientMsg + "#";

        for (DogSize size: DogSize.values()) {
            if (listDogSize.contains(size)){
                clientMsg = clientMsg + "true#";
            } else {
                clientMsg = clientMsg + "false#";
            }
        }

        clientMsg = clientMsg + dogsNumber + "#" + biography + "#";
        WorkingTime[] workingTimeArray = dateTimeAvailability.getArrayDays();
        for (WeekDays wd : WeekDays.values()) {
            if ((workingTimeArray[wd.ordinal()].getStart().equals(Time.valueOf("00:00:00"))) && (workingTimeArray[wd.ordinal()].getEnd().equals(Time.valueOf("00:00:00")))){
                clientMsg = clientMsg + "null#null#";
            } else {
                clientMsg = clientMsg + workingTimeArray[wd.ordinal()].getStart() + "#" + workingTimeArray[wd.ordinal()].getEnd() +"#";
            }
        }

        if (acceptCash){
            clientMsg = clientMsg + "true";
        } else {
            clientMsg = clientMsg + "false";
        }

        String serverMsg = getReply(clientMsg);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

}
