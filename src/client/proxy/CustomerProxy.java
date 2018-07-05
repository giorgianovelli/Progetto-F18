/**
 * This class extends the functionalities of Proxy class
 * with some methods useful for comunitaction between a customer client
 * and the server.
 */

package client.proxy;

import interfaces.InterfaceCustomer;
import server.*;
import server.bank.PaymentMethod;
import server.places.Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerProxy extends Proxy implements InterfaceCustomer {

    /**
     * The customer's email address.
     */
    private String email;


    /**
     * Create a new CustomerProxy using the email address of a customer.
     * @param email the customer's email address.
     */
    public CustomerProxy(String email) {
        this.email = email;
    }


    /**
     * Create a new CustomerProxy object.
     * WARNING: use this constructor only for calling method.
     * customerAccessDataVerifier(String inputUser, String inputPasword).
     */
    public CustomerProxy() {
        this.email = null;
    }


    /**
     * Perform the login of a customer.
     * @param inputUser the email address inserted by the user.
     * @param inputPasword the password inserted by the user.
     * @return true if login is successfully performed.
     */
    public boolean customerAccessDataVerifier(String inputUser, String inputPasword) {
        String clientMsg = "CUSTOMER#ACCESSVERIFIER#" + inputUser + "#" + inputPasword;
        if (getReply(clientMsg).equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @return the HashMap of customer's assignments. The Integer key is the code of the Assignment.
     */
    public HashMap<Integer, Assignment> getAssignmentList() {
        String serverMsg = getReply("CUSTOMER#GETLISTASSIGNMENT#" + email);
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
     * Decode the list of dogs.
     * @param msg the fragment of the message received from the server containing dogs' ID.
     * @return the HashSet of Dogs.
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
            if (tokenDog.nextToken().equals("true")){
                isEnabled = true;
            } else {
                isEnabled = false;
            }
            Dog d = new Dog(name, breed, size, age, weight, ID, isEnabled);
            dogList.add(d);
        }
        return dogList;
    }


    /**
     * Get the dog sitter's name of the assignment.
     * @param code the code of the Assignment.
     * @return the dog sitter's name of the Assignment related to code.
     */
    public String getDogSitterNameOfAssignment(int code) {
        return getReply("CUSTOMER#GETDOGSITTERNAMEOFASSIGNMENT#" + code);
    }


    /**
     * Get the dog sitter's surname of the assignment.
     * @param code the code of the Assignment.
     * @return the dog sitter's surname of the Assignment related to code.
     */
    public String getDogSitterSurnameOfAssignment(int code) {
        return getReply("CUSTOMER#GETDOGSITTERSURNAMEOFASSIGNMENT#" + code);
    }


    /**
     * Get the review indicated with code.
     * @param code the code of the Assignment.
     * @return the Review associated to Assignment related to code.
     */
    public Review getReview(int code){
        String serverMsg = getReply("CUSTOMER#GETREVIEW#" + code);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        //non deve ricevere l'attributo codice dal server
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
     * Get the customer's name.
     * @return the customer's name.
     */
    public String getName(){
        return getReply("CUSTOMER#GETNAME#" + email);
    }


    /**
     * Get the customer's surname.
     * @return the customer's surname.
     */
    public String getSurname(){
        return getReply("CUSTOMER#GETSURNAME#" + email);
    }


    /**
     * Get the customer's password.
     * @return the customer's password.
     */
    public String getPassword(){
        return getReply("CUSTOMER#GETPASSWORD#" + email);
    }


    /**
     * Get the customer's phone number.
     * @return the customer's phone number.
     */
    public String getPhoneNumber(){
        return getReply("CUSTOMER#GETPHONENUMBER#" + email);
    }


    /**
     * Get the customer's date of birth.
     * @return the customer's date of birth.
     */
    public Date getDateOfBirth(){
        String serverMsg = getReply("CUSTOMER#GETDATEOFBIRTH#" + email);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(serverMsg);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the customer's address.
     * @return the customer's Address.
     */
    public Address getAddress(){
        String serverMsg = getReply("CUSTOMER#GETADDRESS#" + email);
        return decodeAddress(serverMsg);
    }


    /**
     * Get the customer's payment method.
     * @return the customer's PaymentMethod.
     */
    public PaymentMethod getPaymentMethod(){
        String serverMsg = getReply("CUSTOMER#GETPAYMENTMETHOD#" + email);
        return decodePaymentMethod(serverMsg);
    }


    /**
     * Update the customer's name.
     * @param name new name inserted by the customer.
     * @return true if the update is successfully performed.
     */
    public boolean updateName(String name){
        String serverMsg = getReply("CUSTOMER#UPDATENAME#" + email + "#" + name);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's surname.
     * @param surname new surname inserted by the customer.
     * @return true if the update is successfully performed.
     */
    public boolean updateSurname(String surname){
        String serverMsg = getReply("CUSTOMER#UPDATESURNAME#" + email + "#" + surname);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's password.
     * @param password new password inserted by the customer.
     * @return true if the update is successfully performed.
     */
    public boolean updatePassword(String password){
        String serverMsg = getReply("CUSTOMER#UPDATEPASSWORD#" + email + "#" + password);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's phone number.
     * @param phoneNumber new phone number inserted by the customer.
     * @return true if the update is successfully performed.
     */
    public boolean updatePhoneNumber(String phoneNumber){
        String serverMsg = getReply("CUSTOMER#UPDATEPHONENUMBER#" + email + "#" + phoneNumber);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's date of birth.
     * @param dateOfBirth new date of birth inserted by the customer.
     * @return true if the update is successfully performed.
     */
    public boolean updateDateOfBirth(Date dateOfBirth){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dateFormat.format(dateOfBirth);
        String serverMsg = getReply("CUSTOMER#UPDATEDATEOFBIRTH#" + email + "#" + strDateOfBirth);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's address with the following data inserted by the customer.
     * @param country the country in which the customer lives.
     * @param city the city where the customer lives.
     * @param street the street in which customer lives.
     * @param number civic number.
     * @param cap the cap of the city where the customer lives.
     * @return true if the update is successfully performed.
     */
    public boolean updateAddress(String country, String city, String street, String number, String cap){
        String serverMsg = getReply("CUSTOMER#UPDATEADDRESS#" + email + "#" + country + "#" + city + "#" + street + "#" + number + "#" + cap);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Update the customer's payment method with the following data inserted by the customer.
     * @param number card's number.
     * @param name owner's name.
     * @param surname owner's surname.
     * @param expirationDate the card's expiration date.
     * @param cvv the secure code of the card.
     * @return true if the update is successfully performed.
     */
    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strExpiration = dateFormat.format(expirationDate);
        String serverMsg = getReply("CUSTOMER#UPDATEPAYMENTMETHOD#" + email + "#" + number + "#" + name + "#" + surname + "#" + strExpiration + "#" + cvv);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Perform a search of dog sitters available based on the following parameters.
     * @param dateStart start of the assignment.
     * @param dateEnd end of assignment.
     * @param meetingPoint place (Address type) in which customer and dog sitter will meet.
     * @param dogList list of dogs to assign to dog sitter.
     * @param cash true if customer will pay dog sitter in cash.
     * @return the HashSet containing email address of available dog sitters.
     */
    public HashSet<String> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strStart = dateFormat.format(dateStart);
        String strEnd = dateFormat.format(dateEnd);
        String clientMsg = "CUSTOMER#SEARCH#" + email + "#" + strStart + "#" + strEnd + "#" + meetingPoint.getCountry() + "#" + meetingPoint.getCity() + "#" +
                meetingPoint.getStreet() + "#" + meetingPoint.getNumber() + "#" + meetingPoint.getCap() + "#";

        for (Dog d : dogList) {
            clientMsg = clientMsg + d.getID() + "*";
        }

        clientMsg = clientMsg + "#" + cash;
        String serverMsg = getReply(clientMsg);

        StringTokenizer tokenServer = new StringTokenizer(serverMsg, "#");
        HashSet<String> dogSitterMailList = new HashSet<String>();
        while (tokenServer.hasMoreTokens()){
            dogSitterMailList.add(tokenServer.nextToken());
        }

        return dogSitterMailList;
    }


    /**
     * Estimate the assignment's price based on following parameters.
     * @param dogList list of dogs to assign to dog sitter.
     * @param dateStart start of assignment.
     * @param dateEnd end of assignment.
     * @return the assignment's price.
     */
    public double estimatePriceAssignment(HashSet<Dog> dogList, Date dateStart, Date dateEnd){
        String clientMsg = "CUSTOMER#ESTIMATEPRICEASSIGNMENT#" + email + "#";
        for (Dog d : dogList) {
            clientMsg = clientMsg + d.getID() + "*";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        clientMsg = clientMsg + "#" + dateFormat.format(dateStart) + "#" + dateFormat.format(dateEnd);
        String serverMsg = getReply(clientMsg);
        return Double.parseDouble(serverMsg);
    }


    /**
     * Confirm the assignment for the following parameters.
     * @param emailDogSitter dog sitter's email address.
     * @param dateStartAssignment start of the assignment.
     * @param dateEndAssignment end of the assignment.
     * @param selectedDogs the HashSet of dogs to assign to the dog sitter.
     * @param meetingPoint place (Address type) in which customer and dog sitter will meet.
     * @param paymentInCash true if customer will pay dog sitter in cash.
     * @return true if assignment is successfully performed.
     */
    public boolean addAssignment(String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash){
        String clientMsg = "CUSTOMER#ADDASSIGNMENT#" + email + "#" + emailDogSitter + "#";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strStart = dateFormat.format(dateStartAssignment);
        String strEnd = dateFormat.format(dateEndAssignment);
        clientMsg = clientMsg + strStart + "#" + strEnd + "#";
        for (Dog d : selectedDogs) {
            clientMsg = clientMsg + d.getID() + "*";
        }
        clientMsg = clientMsg + "#" + meetingPoint.getCountry() + "#" + meetingPoint.getCity() + "#" + meetingPoint.getStreet() + "#" + meetingPoint.getNumber() + "#" + meetingPoint.getCap() + "#" + paymentInCash;
        if (getReply(clientMsg).equals("true")){
            return true;
        } else {
            return false;
        }

    }


    /**
     * Remove the assignment associated to code.
     * @param code of the assignment to remove.
     * @return true if the assignment is successfully removed.
     */
    public boolean removeAssignment(int code) {
        String serverMsg = getReply("CUSTOMER#REMOVEASSIGNMENT#" + email + "#" + code);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Add a review.
     * @param codeAssignment the code of the assignment that customer want to review.
     * @param emailDogSitter dog sitter's email address.
     * @param rating int value from 1 to 5.
     * @param title the title of the review.
     * @param comment the description inserted by the customer.
     * @return true if review is successfully created.
     */
    public boolean addReview(int codeAssignment, String  emailDogSitter, int rating, String title, String comment) {
        String serverMsg = getReply("CUSTOMER#ADDREVIEW#" + email + "#" + codeAssignment + "#" + emailDogSitter + "#" + rating + "#" + title + "#" + comment);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Remove the review related to code.
     * @param code the code of the review that the customer want to remove.
     * @return true if the review is successfully removed.
     */
    public boolean removeReview(Integer code) {
        String serverMsg = getReply("CUSTOMER#REMOVEREVIEW#" + email + "#" + code);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get the list of the reviews written by the customer.
     * @return the HashMap of the Reviews written by the customer. The Integer key is the
     * assignment's code related to review.
     */
    public HashMap<Integer, Review> getReviewList() {
        String serverMsg = getReply("CUSTOMER#GETREVIEWLIST#" + email);
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
     * Add a new dog with the following data to the dogs' list
     * @param customerEmail email address of the owner.
     * @param name dog's name.
     * @param breed dog's breed.
     * @param dateOfBirth dog's date of birth.
     * @param weight dog's weight.
     * @return true if the new dog is successfully addedd.
     */
    public boolean addDog(String customerEmail, String name, String breed, Date dateOfBirth, double weight) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dateFormat.format(dateOfBirth);
        String clientMsg = "CUSTOMER#ADDDOG#" + email + "#" + name + "#" + breed + "#" + strDateOfBirth + "#" + weight;
        String serverMsg = getReply(clientMsg);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Disable a dog.
     * @param ID the ID of the dog that customer want to disable.
     * @return true if the dog is successfully disabled.
     */
    public boolean disableDog(int ID){
        String serverMsg = getReply("CUSTOMER#DISABLEDOG#" + email + "#" + ID);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get the list of dogs of the customer.
     * @return the HashSet containing the dogs of the customer.
     */
    public HashSet<Dog> getDogList(){
        return decodeDogList(getReply("CUSTOMER#GETDOGLIST#" + email));
    }


    /**
     * Create a new customer account based on following data.
     * @param email user's email address.
     * @param name user's name.
     * @param surname user's surname.
     * @param password user's password.
     * @param phoneNumber user's phone number.
     * @param dateOfBirth user's date of birth.
     * @param address user's address.
     * @param paymentMethod user's payment method.
     * @return true if the new customer account is successfully created.
     */
    public boolean customerSignUp(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strBirth = dateFormat.format(dateOfBirth);
        String strExpiration = dateFormat.format(paymentMethod.getExpirationDate());
        String clientMsg = "CUSTOMER#SIGNUP#" + email + "#" + name + "#" + surname + "#" + password + "#" + phoneNumber
                + "#" + strBirth + "#" + address.getCountry() + "#" + address.getCity() + "#" + address.getStreet() + "#" + address.getNumber()
                + "#" + address.getCap() + "#" + paymentMethod.getNumber() + "#" + paymentMethod.getName() + "#" + paymentMethod.getSurname()
                + "#" + strExpiration + "#" + paymentMethod.getCvv() + "#" + paymentMethod.getAmount();
        String serverMsg = getReply(clientMsg);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the payment method of the assignment is in cash.
     * @param code the code of the assignment.
     * @return true if the customer pays the dog sitter in cash.
     */
    public Boolean isInCashPaymentMethodOfAssignment(int code){
        String serverMsg = getReply("CUSTOMER#ISINCASHASSIGNMENT#" + email + "#" + code);
        if (serverMsg.equals("true")){
            return true;
        } else if (serverMsg.equals("false")){
            return false;
        } else {
            return null;
        }
    }

    public boolean updateDogName(int ID, String name){
        String serverMsg = getReply("CUSTOMER#UPDATEDOGNAME#" + email + "#" + ID + "#" + name);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

}
