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
        String clientMsg = "0#" + inputUser + "#" + inputPasword;
        if (getReply(clientMsg).equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<Integer, Assignment> getCustomerListAssignment() {
        String serverMsg = getReply("1#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        HashMap<Integer, Assignment> customerListAssignment = new HashMap<Integer, Assignment>();
        while (tokenMsg.hasMoreTokens()) {
            int code = Integer.parseInt(tokenMsg.nextToken());
            HashSet<Dog> dogList = decodeDogList(tokenMsg.nextToken());    //TODO
            //tokenMsg.nextToken();           //...
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            Date dateStart = new Date();
            Date dateEnd = new Date();
            try {
                dateStart = dateFormat.parse(tokenMsg.nextToken());
                dateEnd = dateFormat.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean state;
            if (tokenMsg.nextToken().equals("true")) {
                state = true;
            } else {
                state = false;
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
        //System.out.println("TM: " + tokenMsg.nextToken());
        HashSet<Dog> dogList = new HashSet<Dog>();
        while (tokenMsg.hasMoreTokens()) {
            StringTokenizer tokenDog = new StringTokenizer(tokenMsg.nextToken(), "&");
            //System.out.println("TD: " + tokenDog.nextToken());
            int ID = Integer.parseInt(tokenDog.nextToken());
            String name = tokenDog.nextToken();
            String breed = tokenDog.nextToken();
            DogSize size = DogSize.valueOf(tokenDog.nextToken());
            int age = Integer.parseInt(tokenDog.nextToken());
            double weight = Double.parseDouble(tokenDog.nextToken());
            Dog d = new Dog(name, breed, size, age, weight, ID);
            dogList.add(d);
        }
        return dogList;
    }

    public String getDogSitterNameOfAssignment(int code) {
        return getReply("2#" + code);
    }

    public String getDogSitterSurnameOfAssignment(int code) {
        return getReply("3#" + code);
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

    public String getCustomerName(){
        return getReply("5#" + email);
    }

    public String getCustomerSurname(){
        return getReply("6#" + email);
    }

    public String getCustomerPassword(){
        return getReply("7#" + email);
    }

    public String getCustomerPhoneNumber(){
        return getReply("8#" + email);
    }

    public Date getCustomerDateOfBirth(){
        String serverMsg = getReply("9#" + email);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        try {
            return dateFormat.parse(serverMsg);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Address getCustomerAddress(){
        String serverMsg = getReply("10#" + email);
        return decodeAddress(serverMsg);
    }

    public PaymentMethod getCustomerPaymentMethod(){
        String serverMsg = getReply("11#" + email);
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
        StringTokenizer tokenMsg = new StringTokenizer(msg, "#");
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

    public boolean updateCustomerName(String name){
        String serverMsg = getReply("12#" + email + "#" + name);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerSurname(String surname){
        String serverMsg = getReply("13#" + email + "#" + surname);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerPassword(String password){
        String serverMsg = getReply("14#" + email + "#" + password);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerPhoneNumber(String phoneNumber){
        String serverMsg = getReply("15#" + email + "#" + phoneNumber);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerDateOfBirth(String strDateOfBirth){
        String serverMsg = getReply("16#" + email + "#" + strDateOfBirth);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerAddress(String country, String city, String street, String number, String cap){
        String serverMsg = getReply("17#" + email + "#" + country + "#" + city + "#" + street + "#" + number + "#" + cap);
        if (serverMsg.equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomerPaymentMethod(String number, String name, String surname, Date expirationDate, int cvv){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strExpiration = dateFormat.format(expirationDate);
        String serverMsg = getReply("18#" + email + "#" + number + "#" + name + "#" + surname + "#" + strExpiration + "#" + cvv);
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
        String clientMsg = "19#" + email + "#" + strStart + "#" + strEnd + "#" + meetingPoint.getCountry() + "#" + meetingPoint.getCity() + "#" +
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
        String clientMsg = "20#" + email + "#";
        for (Dog d : dogList) {
            clientMsg = clientMsg + d.getID() + "*";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        clientMsg = clientMsg + "#" + dateFormat.format(dateStart) + "#" + dateFormat.format(dateEnd);
        System.out.println("test: " + clientMsg);
        String serverMsg = getReply(clientMsg);
        System.out.println("server msg: " + serverMsg);
        //return Double.parseDouble(serverMsg);
        return 0.0;
    }

}
