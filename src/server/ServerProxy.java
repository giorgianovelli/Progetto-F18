package server;
import database.DBConnector;
import interfaces.InterfaceCustomer;
import server.bank.PaymentMethod;
import server.places.Address;

import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class ServerProxy extends Thread
{
    private ServerSocket Server;

    public static void main(String[] args) {
        try {
            new ServerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerProxy() throws Exception{
        Server = new ServerSocket(4000);
        System.out.println("The Server is waiting on port 4000...");
        this.start();
    }

    public void run(){
        while(true) {
            try{
                System.out.println("Waiting for connection...");
                Socket client = Server.accept();
                System.out.println("Connection accepted by: " +
                        client.getInetAddress());
                Connect c = new Connect(client);
            }
            catch(Exception e) {}
        }
    }
}

class Connect extends Thread {
    private Socket client = null;
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;

    public Connect(Socket clientSocket){
        client = clientSocket;
        try{
            msgIn = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            msgOut = new PrintStream(client.getOutputStream(), true);
        }
        catch(Exception e1){
            try {
                client.close();
            }
            catch(Exception e) { System.out.println(e.getMessage());}
            return;
        }
        this.start();
    }

    public void run(){
        try{
            msgOut.println(executeClientCmd());
            msgOut.flush();
            // chiude gli stream e le connessioni
            msgOut.close();
            msgIn.close();
            client.close();
        }
        catch(Exception e) {

        }
    }

    private String executeClientCmd(){
        String serverMsg = null;
        int code;
        String email;
        SimpleDateFormat dateFormatDays = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatMinutes = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String name;
        String surname;
        Date dateStart;
        Date dateEnd;
        String strDogList;
        try {
            StringTokenizer tokenMsg = null;
            try {
                tokenMsg = new StringTokenizer(msgIn.readLine(), "#");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int action = Integer.parseInt(tokenMsg.nextToken());
            switch (action){
                case 0:
                    String inputUser = tokenMsg.nextToken();
                    String inputPassword = tokenMsg.nextToken();
                    serverMsg = customerAccessDataVerifier(inputUser, inputPassword);
                    break;
                case 1:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerListAssignment(email);
                    break;
                case 2:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getDogSitterNameOfAssignment(code);
                    break;
                case 3:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getDogSitterSurnameOfAssignment(code);
                    break;
                case 4:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getReview(code);
                    break;
                case 5:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerName(email);
                    break;
                case 6:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerSurname(email);
                    break;
                case 7:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerPassword(email);
                    break;
                case 8:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerPhoneNumber(email);
                    break;
                case 9:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerDateOfBirth(email);
                    break;
                case 10:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerAddress(email);
                    break;
                case 11:
                    email = tokenMsg.nextToken();
                    serverMsg = getCustomerPaymentMethod(email);
                    break;
                case 12:
                    email = tokenMsg.nextToken();
                    name = tokenMsg.nextToken();
                    serverMsg = updateCustomerName(email, name);
                    break;
                case 13:
                    email = tokenMsg.nextToken();
                    surname = tokenMsg.nextToken();
                    serverMsg = updateCustomerSurname(email, surname);
                    break;
                case 14:
                    email = tokenMsg.nextToken();
                    String password = tokenMsg.nextToken();
                    serverMsg = updateCustomerPassword(email, password);
                    break;
                case 15:
                    email = tokenMsg.nextToken();
                    String phoneNumber = tokenMsg.nextToken();
                    serverMsg = updateCustomerPhoneNumber(email, phoneNumber);
                    break;
                case 16:
                    email = tokenMsg.nextToken();
                    String strDateOfBirth = tokenMsg.nextToken();
                    Date dateOfBirth = dateFormatDays.parse(strDateOfBirth);
                    serverMsg = updateCustomerDateOfBirth(email, dateOfBirth);
                    break;
                case 17:
                    email = tokenMsg.nextToken();
                    String country = tokenMsg.nextToken();
                    String city = tokenMsg.nextToken();
                    String street = tokenMsg.nextToken();
                    String cnumber = tokenMsg.nextToken();
                    String cap = tokenMsg.nextToken();
                    serverMsg = updateCustomerAddress(email, country, city, street, cnumber, cap);
                    break;
                case 18:
                    email = tokenMsg.nextToken();
                    String number = tokenMsg.nextToken();
                    name = tokenMsg.nextToken();
                    surname = tokenMsg.nextToken();
                    Date expirationDate = dateFormatDays.parse(tokenMsg.nextToken());
                    int cvv = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = updateCustomerPaymentMethod(email, number, name, surname, expirationDate, cvv);
                    break;
                case 19:
                    email = tokenMsg.nextToken();
                    dateStart = dateFormatMinutes.parse(tokenMsg.nextToken());
                    dateEnd = dateFormatMinutes.parse(tokenMsg.nextToken());
                    country = tokenMsg.nextToken();
                    city = tokenMsg.nextToken();
                    street = tokenMsg.nextToken();
                    number = tokenMsg.nextToken();
                    cap = tokenMsg.nextToken();
                    strDogList = tokenMsg.nextToken();
                    String strCash = tokenMsg.nextToken();
                    serverMsg = search(email, dateStart, dateEnd, country, city, street, number, cap, strDogList, strCash);
                    break;
                case 20:
                    email = tokenMsg.nextToken();
                    strDogList = tokenMsg.nextToken();
                    dateStart = dateFormatMinutes.parse(tokenMsg.nextToken());
                    dateEnd = dateFormatMinutes.parse(tokenMsg.nextToken());
                    serverMsg = estimatePriceAssignment(email, strDogList, dateStart, dateEnd);
                    break;
                case 21:
                    email = tokenMsg.nextToken();
                    String emailDogSitter = tokenMsg.nextToken();
                    dateStart = dateFormatMinutes.parse(tokenMsg.nextToken());
                    dateEnd = dateFormatMinutes.parse(tokenMsg.nextToken());
                    strDogList = tokenMsg.nextToken();
                    country = tokenMsg.nextToken();
                    city = tokenMsg.nextToken();
                    street = tokenMsg.nextToken();
                    number = tokenMsg.nextToken();
                    cap = tokenMsg.nextToken();
                    serverMsg = addAssignment(email, emailDogSitter, dateStart, dateEnd, strDogList, country, city, street, number, cap);
                    break;
                default:
            }
        } finally {
            return serverMsg;
        }
    }

    private String customerAccessDataVerifier(String inputUser, String inputPasword){
        Login loginCustomer = new Login();
        try {
            if(loginCustomer.customerAccessDataVerifier(inputUser, inputPasword)){
                return "true";
            } else{
                return "false";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

    private String getCustomerListAssignment(String email){
       //TODO
        Singleton singleton = new Singleton();
        HashMap<Integer, Assignment> customerListAssignment = singleton.getCustomerListAssignmentFromDB(email);
        String msg = "";
        for (Integer key : customerListAssignment.keySet()) {
            Assignment a = customerListAssignment.get(key);
            //TODO aggiungere chiamata per ottenere la lista dei cani e il meeting point di a
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String strDateStart = dateFormat.format(a.getDateStart());
            String strDateEnd = dateFormat.format(a.getDateEnd());
            msg = msg + a.getCode() + "#" + getDogListOfAssignment(a.getCode()) + "#" + strDateStart + "#" + strDateEnd + "#" + a.getState() + "#" + getMeetingPoint(a.getCode()) + "#";
        }
        return msg;
    }

    private String getDogSitterNameOfAssignment(int code){
        DBConnector dbConnector = new DBConnector();
        String emailDogSitter = null;
        DogSitter dogSitter = null;
        try {
            ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
            rs.next();
            emailDogSitter = rs.getString("DOGSITTER");
            Singleton singleton = new Singleton();
            dogSitter = singleton.createDogSitterFromDB(emailDogSitter);
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogSitter.name;
    }

    private String getDogSitterSurnameOfAssignment(int code){
        DBConnector dbConnector = new DBConnector();
        String emailDogSitter = null;
        DogSitter dogSitter = null;
        try {
            ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
            rs.next();
            emailDogSitter = rs.getString("DOGSITTER");
            Singleton singleton = new Singleton();
            dogSitter = singleton.createDogSitterFromDB(emailDogSitter);
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogSitter.surname;
    }

    private String getMeetingPoint(int code){
        Singleton singleton = new Singleton();
        Address meetingPoint = singleton.getMeetingPointFromDB(code);
        return meetingPoint.getCountry() + "*" + meetingPoint.getCity() + "*" + meetingPoint.getStreet() + "*"
                + meetingPoint.getNumber() + "*" + meetingPoint.getCap();
    }

    private String getDogListOfAssignment(int code){
        String msg = "";
        Singleton singleton = new Singleton();
        HashSet<Dog> dogList = singleton.getDogListFromDB(code);
        for (Dog d : dogList) {
            msg = msg + d.getID() + "&" + d.getName() + "&" + d.getBreed() + "&" + d.getSize() + "&" + d.getAge() + "&"
                        + d.getWeight() + "*";
        }
        return msg;
    }

    private String getReview(int code){
        Singleton singleton = new Singleton();
        Review review = singleton.getReview(code);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = dateFormat.format(review.getDate());
        //TODO
        return  date + "#" + review.getRating() + "#" +review.getTitle() + "#" + review.getComment() + "#" + review.getReply();
    }

    private String getCustomerName(String email){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        return customer.getName();
    }

    private String getCustomerSurname(String email){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        return customer.getSurname();
    }

    private String getCustomerPassword(String email){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        return customer.getPassword();
    }

    private String getCustomerPhoneNumber(String email){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        return customer.getPhoneNumber();
    }

    private String getCustomerDateOfBirth(String email){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateOfBirth = dateFormat.format(customer.getDateOfBirth());
        return dateOfBirth;
    }

    private String getCustomerAddress(String email){
        Singleton singleton = new Singleton();
        Address address = singleton.createCustomerFromDB(email).getAddress();
        return address.getCountry() + "*" + address.getCity() + "*" + address.getStreet() + "*"
                + address.getNumber() + "*" + address.getCap();
    }

    private String getCustomerPaymentMethod(String email){
        Singleton singleton = new Singleton();
        PaymentMethod pm = singleton.createCustomerFromDB(email).getPaymentMethod();
        return pm.getNumber() + "*" + pm.getName() + "*" + pm.getSurname() + "*"
                + pm.getExpirationDate() + "*" + pm.getCvv() + "*" + pm.getAmount();
    }

    private String updateCustomerName(String email, String name){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updateName(name)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerSurname(String email, String surname){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updateSurname(surname)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerPassword(String email, String password){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updatePassword(password)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerPhoneNumber(String email, String phoneNumber){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updatePhoneNumber(phoneNumber)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerDateOfBirth(String email, Date dateOfBirth){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updateDateOfBirth(dateOfBirth)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerAddress(String email, String country, String city, String street, String number, String cap){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updateAddress(country, city, street, number, cap)){
            return "true";
        } else {
            return "false";
        }
    }

    private String updateCustomerPaymentMethod(String email, String number, String name, String surname, Date expirationDate, int cvv){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        if (customer.updatePaymentMethod(number, name, surname, expirationDate, cvv)){
            return "true";
        } else {
            return "false";
        }
    }

    private String search(String email, Date dateStart, Date dateEnd, String country, String city, String street, String number, String cap, String strDogList, String strCash){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);
        Address meetingPoint = new Address(country, city, street, number, cap);

        boolean cash;
        if (strCash.equals("true")){
            cash = true;
        } else {
            cash = false;
        }

        StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
        HashSet<Dog> dogList = new HashSet<Dog>();
        while (tokenDogList.hasMoreTokens()){
            Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
            dogList.add(d);
        }

        HashSet<String> resultList = customer.search(dateStart, dateEnd, meetingPoint, dogList, cash);
        String serverMsg = "";
        for (String emailDs : resultList) {
            serverMsg = serverMsg + emailDs + "#";
        }

        return serverMsg;
    }

    private String estimatePriceAssignment(String email, String strDogList, Date dateStart, Date dateEnd){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);

        StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
        HashSet<Dog> dogList = new HashSet<Dog>();
        while (tokenDogList.hasMoreTokens()){
            Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
            dogList.add(d);
        }

        Double price = customer.estimatePriceAssignment(dogList, dateStart, dateEnd);
        return price.toString();
    }

    public String addAssignment(String email, String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, String strDogList, String country, String city, String street, String number, String cap){
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB(email);

        StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
        HashSet<Dog> dogList = new HashSet<Dog>();
        while (tokenDogList.hasMoreTokens()){
            Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
            dogList.add(d);
        }

        Address meetingPoint = new Address(country, city, street, number, cap);

        if (customer.addAssignment(emailDogSitter, dateStartAssignment, dateEndAssignment, dogList, meetingPoint)){
            return "true";
        } else {
            return "false";
        }
    }
}