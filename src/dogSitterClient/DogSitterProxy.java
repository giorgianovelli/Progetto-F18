package dogSitterClient;

import interfaces.InterfaceDogSitter;
import server.*;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DogSitterProxy implements InterfaceDogSitter {
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;
    private Socket socket = null;
    private String serverReply;
    private String email;

    public DogSitterProxy(String email) {
        this.email = email;
    }

    public DogSitterProxy() {
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

    public boolean dogSitterAccessDataVerifier(String inputUser, String inputPasword) {
        String clientMsg = "DOGSITTER#ACCESSVERIFIER#" + inputUser + "#" + inputPasword;
        return getReply(clientMsg).equals("true");
    }

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

    private Address decodeMeetingPoint(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }

    public String getCustomerNameOfAssignment(int code) {
        return getReply("DOGSITTER#GETCUSTOMERNAMEOFASSIGNMENT#" + code);
    }

    public String getCustomerSurnameOfAssignment(int code) {
        return getReply("DOGSITTER#GETCUSTOMERSURNAMEOFASSIGNMENT#" + code);
    }

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

    public String getName(){
        return getReply("DOGSITTER#GETNAME#" + email);
    }

    public String getSurname(){
        return getReply("DOGSITTER#GETSURNAME#" + email);
    }

    public String getPassword(){
        return getReply("DOGSITTER#GETPASSWORD#" + email);
    }

    public String getPhoneNumber(){
        return getReply("DOGSITTER#GETPHONENUMBER#" + email);
    }

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

    public Address getAddress(){
        String serverMsg = getReply("DOGSITTER#GETADDRESS#" + email);
        return decodeAddress(serverMsg);
    }

    public PaymentMethod getPaymentMethod(){
        String serverMsg = getReply("DOGSITTER#GETPAYMENTMETHOD#" + email);
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
        String serverMsg = getReply("DOGSITTER#UPDATENAME#" + email + "#" + name);
        return serverMsg.equals("true");
    }

    public boolean updateSurname(String surname){
        String serverMsg = getReply("DOGSITTER#UPDATESURNAME#" + email + "#" + surname);
        return serverMsg.equals("true");
    }

    public boolean updatePassword(String password){
        String serverMsg = getReply("DOGSITTER#UPDATEPASSWORD#" + email + "#" + password);
        return serverMsg.equals("true");
    }

    public boolean updatePhoneNumber(String phoneNumber){
        String serverMsg = getReply("DOGSITTER#UPDATEPHONENUMBER#" + email + "#" + phoneNumber);
        return serverMsg.equals("true");
    }

    public boolean updateDateOfBirth(Date dateOfBirth){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dateFormat.format(dateOfBirth);
        String serverMsg = getReply("DOGSITTER#UPDATEDATEOFBIRTH#" + email + "#" + strDateOfBirth);
        return serverMsg.equals("true");
    }

    public boolean updateAddress(String country, String city, String street, String number, String cap){
        String serverMsg = getReply("DOGSITTER#UPDATEADDRESS#" + email + "#" + country + "#" + city + "#" + street + "#" + number + "#" + cap);
        return serverMsg.equals("true");
    }

    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strExpiration = dateFormat.format(expirationDate);
        String serverMsg = getReply("DOGSITTER#UPDATEPAYMENTMETHOD#" + email + "#" + number + "#" + name + "#" + surname + "#" + strExpiration + "#" + cvv);
        return serverMsg.equals("true");
    }

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

    public boolean updateAssignmentState(int code, Boolean state){
        String serverMsg = getReply("DOGSITTER#UPDATEASSIGNMENTSTATE#" + email + "#" + code + "#" + state);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCashFlag(boolean acceptCash){
        String serverMsg = getReply("DOGSITTER#UPDATECASHFLAG#" + email + "#" + acceptCash);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public int getDogsNumber() {
        String serverMsg = getReply("DOGSITTER#GETDOGSNUMBER#" + email);
        return Integer.parseInt(serverMsg);
    }

    public Area getArea() {
        String serverMsg = getReply("DOGSITTER#GETAREA#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        Area area = new Area();
        while (tokenMsg.hasMoreTokens()){
            area.addPlace(tokenMsg.nextToken());
        }
        return area;
    }

    public boolean isAcceptingCash() {
        String serverMsg = getReply("DOGSITTER#ISACCEPTINGCASH#" + email);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

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

    public boolean addNewPlaceArea(String city){
        String serverMsg = getReply("DOGSITTER#ADDNEWPLACEAREA#" + email + "#" + city);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removePlaceArea(String city){
        String serverMsg = getReply("DOGSITTER#REMOVEPLACEAREA#" + email + "#" + city);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateDogsNumber(int nDogs){
        String serverMsg = getReply("DOGSITTER#UPDATEDOGSNUMBER#" + email + "#" + nDogs);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

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

    public boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant){
        String serverMsg = getReply("DOGSITTER#UPDATELISTDOGSIZE#" + email + "#" + small + "#" + medium + "#" + big + "#" + giant);
        if (serverMsg.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

}
