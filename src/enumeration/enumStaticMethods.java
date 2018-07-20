package enumeration;

import database.DBConnector;
import server.*;
import server.places.Address;
import server.places.Area;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


/**
 * This class contains some static methods that are used by the enumerations of this package.
 */
public class enumStaticMethods {

    /**
     * Return a string with all informations about the dogs of the assignment specified by the code.
     * @param code the assignment's code.
     * @return the string with all informations about the dogs of the assignment specified by the code.
     */
    protected static String getDogListOfAssignment(int code){
        String msg = "";
        Singleton singleton = new Singleton();
        HashSet<Dog> dogList = singleton.getDogListFromDB(code);
        for (Dog d : dogList) {
            msg = msg + d.getID() + "&" + d.getName() + "&" + d.getBreed() + "&" + d.getSize() + "&" + d.getAge() + "&"
                    + d.getWeight() + "&" + d.isEnabled() + "*";
        }
        return msg;
    }


    /**
     * Return a string with all informations about the meeting point of the assignment specified by the code.
     * @param code the assignment's code.
     * @return the string with all informations about the meeting point of the assignment specified by the code.
     */
    protected static String getMeetingPoint(int code){
        Singleton singleton = new Singleton();
        Address meetingPoint = singleton.getMeetingPointFromDB(code);
        return meetingPoint.getCountry() + "*" + meetingPoint.getCity() + "*" + meetingPoint.getStreet() + "*"
                + meetingPoint.getNumber() + "*" + meetingPoint.getCap();
    }


    /**
     * Return a string with all the informations about the review of the assignment specified by the code.
     * @param code the assignment's code.
     * @return
     */
    protected static String getReview(int code){
        Singleton singleton = new Singleton();
        Review review = singleton.getReview(code);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = dateFormat.format(review.getDate());
        return  date + "#" + review.getRating() + "#" +review.getTitle() + "#" + review.getComment() + "#" + review.getReply();
    }


    /**
     * Convert a list of assignments into a string.
     * @param listAssignment the HashMap to be converted.
     * @return the string corresponding to the list of assignment specified as argument.
     */
    protected static String getListAssignment(HashMap<Integer, Assignment> listAssignment){
        String msg = "";
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String strDateStart = dateFormat.format(a.getDateStart());
            String strDateEnd = dateFormat.format(a.getDateEnd());
            msg = msg + a.getCode() + "#" + getDogListOfAssignment(a.getCode()) + "#" + strDateStart + "#" + strDateEnd + "#" + a.getState() + "#" + getMeetingPoint(a.getCode()) + "#";
        }
        return msg;
    }


    /**
     * Convert a string into an Area object.
     * @param strArea the string to be converted.
     * @return the Area corresponding to the string passed as argument.
     */
    protected static Area decodeArea(String strArea){
        StringTokenizer token = new StringTokenizer(strArea, "*");
        Area area = new Area();
        while (token.hasMoreTokens()){
            area.addPlace(token.nextToken());
        }
        return area;
    }


    /**
     * Create the HashSet of dogs accepted by the dog sitter.
     * @param small true if dog sitter accepts small dogs.
     * @param medium true if dog sitter accepts medium dogs.
     * @param big true if dog sitter accepts big dogs.
     * @param giant true if dog sitter accepts giant dogs.
     * @return the HashSet of dogs accepted by the dog sitter.
     */
    protected static HashSet<DogSize> createDogSizeList(boolean small, boolean medium, boolean big, boolean giant){
        HashSet<DogSize> dogSizeList = new HashSet<>();
        if (small){
            dogSizeList.add(DogSize.SMALL);
        }
        if (medium){
            dogSizeList.add(DogSize.MEDIUM);
        }
        if (big){
            dogSizeList.add(DogSize.BIG);
        }
        if (giant){
            dogSizeList.add(DogSize.GIANT);
        }
        return dogSizeList;
    }


    /**
     * Get the dog sitter of assignment specified by the code.
     * @param code the assignment's code.
     * @return the dog sitter of the assignment specified by the code.
     */
    protected static DogSitter getDogSitterOfAssignment(int code) {
        DBConnector dbConnector = new DBConnector();
        String emailDogSitter;
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
        return dogSitter;
    }


    /**
     * Get the customer of the assignment specified by the code.
     * @param code the assignment's code.
     * @return the customer of the assignment specified by the code.
     */
    protected static Customer getCustomerOfAssignment(int code){
        DBConnector dbConnector = new DBConnector();
        String emailCustomer;
        Customer customer = null;
        try {
            ResultSet rs = dbConnector.askDB("SELECT CUSTOMER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
            rs.next();
            emailCustomer = rs.getString("CUSTOMER");
            Singleton singleton = new Singleton();
            customer = singleton.createCustomerFromDB(emailCustomer);
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
