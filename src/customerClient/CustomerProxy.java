package customerClient;

import interfaces.InterfaceCustomer;
import server.*;
import server.bank.PaymentMethod;
import server.places.Address;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerProxy implements InterfaceCustomer {
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;
    private Socket socket = null;
    private String serverReply;
    private String email;

    public CustomerProxy(String email) {
        this.email = email;
    }

    public CustomerProxy() {
        this.email = null;
    }

    private String getReply(String clientMsg) {
        try {
            // open a socket connection
            socket = new Socket("127.0.0.1", 4000); //4000 customer e 4001 dog sitter
            // Apre i canali I/O
            msgIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            msgOut = new PrintStream(socket.getOutputStream(), true);
            msgOut.println(clientMsg);
            msgOut.flush();
            // Legge dal server
            serverReply = msgIn.readLine();
            System.out.println("Received message from server: " + serverReply);
            msgOut.close();
            msgIn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return serverReply;
        }
    }

    public boolean customerAccessDataVerifier(String inputUser, String inputPasword) {
        String clientMsg = "CUSTOMER#ACCESSVERIFIER#" + inputUser + "#" + inputPasword;
        if (getReply(clientMsg).equals("true")) {
            return true;
        } else {
            return false;
        }
    }

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

    private Address decodeMeetingPoint(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }

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

    public String getDogSitterNameOfAssignment(int code) {
        return getReply("CUSTOMER#GETDOGSITTERNAMEOFASSIGNMENT#" + code);
    }

    public String getDogSitterSurnameOfAssignment(int code) {
        return getReply("CUSTOMER#GETDOGSITTERSURNAMEOFASSIGNMENT#" + code);
    }

    public Review getReview(int code){
        String serverMsg = getReply("4#" + code);
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

    public String getName(){
        return getReply("CUSTOMER#GETNAME#" + email);
    }

    public String getSurname(){
        return getReply("CUSTOMER#GETSURNAME#" + email);
    }

    public String getPassword(){
        return getReply("CUSTOMER#GETPASSWORD#" + email);
    }

    public String getPhoneNumber(){
        return getReply("CUSTOMER#GETPHONENUMBER#" + email);
    }

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

    public Address getAddress(){
        String serverMsg = getReply("CUSTOMER#GETADDRESS#" + email);
        return decodeAddress(serverMsg);
    }

    public PaymentMethod getPaymentMethod(){
        String serverMsg = getReply("CUSTOMER#GETPAYMENTMETHOD#" + email);
        return decodePaymentMethod(serverMsg);
    }

    private Address decodeAddress(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "#");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }

    private PaymentMethod decodePaymentMethod(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String number = tokenMsg.nextToken();
        String name = tokenMsg.nextToken();
        String surname = tokenMsg.nextToken();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expirationDate = new Date();
        try {
            expirationDate = dateFormat.parse(tokenMsg.nextToken());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int cvv = Integer.parseInt(tokenMsg.nextToken());
        double amount = Double.parseDouble(tokenMsg.nextToken());
        return new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
    }

    public boolean updateName(String name){
        String serverMsg = getReply("CUSTOMER#UPDATENAME#" + email + "#" + name);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSurname(String surname){
        String serverMsg = getReply("CUSTOMER#UPDATESURNAME#" + email + "#" + surname);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePassword(String password){
        String serverMsg = getReply("CUSTOMER#UPDATEPASSWORD#" + email + "#" + password);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePhoneNumber(String phoneNumber){
        String serverMsg = getReply("CUSTOMER#UPDATEPHONENUMBER#" + email + "#" + phoneNumber);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

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

    public boolean updateAddress(String country, String city, String street, String number, String cap){
        String serverMsg = getReply("CUSTOMER#UPDATEADDRESS#" + email + "#" + country + "#" + city + "#" + street + "#" + number + "#" + cap);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strExpiration = dateFormat.format(expirationDate);
        String serverMsg = getReply("CUSTOMER#UPDATEPAYMENTMETHOD#" + email + "#" + number + "#" + name + "#" + surname + "#" + strExpiration + "#" + cvv);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

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

    public boolean addAssignment(String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint){
        String clientMsg = "CUSTOMER#ADDASSIGNMENT#" + email + "#" + emailDogSitter + "#";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strStart = dateFormat.format(dateStartAssignment);
        String strEnd = dateFormat.format(dateEndAssignment);
        clientMsg = clientMsg + strStart + "#" + strEnd + "#";
        for (Dog d : selectedDogs) {
            clientMsg = clientMsg + d.getID() + "*";
        }
        clientMsg = clientMsg + "#" + meetingPoint.getCountry() + "#" + meetingPoint.getCity() + "#" + meetingPoint.getStreet() + "#" + meetingPoint.getNumber() + "#" + meetingPoint.getCap();
        if (getReply(clientMsg).equals("true")){
            return true;
        } else {
            return false;
        }

    }

    public boolean removeAssignment(int code) {
        String serverMsg = getReply("CUSTOMER#REMOVEASSIGNMENT#" + email + "#" + code);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean addReview(int codeAssignment, String  emailDogSitter, int rating, String title, String comment) {
        String serverMsg = getReply("CUSTOMER#ADDREVIEW#" + email + "#" + codeAssignment + "#" + emailDogSitter + "#" + rating + "#" + title + "#" + comment);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean removeReview(Integer code) {
        String serverMsg = getReply("CUSTOMER#REMOVEREVIEW#" + email + "#" + code);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

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

    public boolean addDog(String customerEmail, String name, String breed, Date dateOfBirth, double weight) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dateFormat.format(dateOfBirth);
        String clientMsg = "CUSTOMER#ADDDOG#" + "#" + email + "#" + name + "#" + breed + "#" + strDateOfBirth + "#" + weight;
        String serverMsg = getReply(clientMsg);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean disableDog(int ID){
        String serverMsg = getReply("CUSTOMER#DISABLEDOG#" + email + "#" + ID);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public HashSet<Dog> getDogList(){
        return decodeDogList(getReply("CUSTOMER#GETDOGLIST#" + email));
    }

}
