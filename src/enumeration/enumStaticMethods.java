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

public class enumStaticMethods {
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

    protected static String getMeetingPoint(int code){
        Singleton singleton = new Singleton();
        Address meetingPoint = singleton.getMeetingPointFromDB(code);
        return meetingPoint.getCountry() + "*" + meetingPoint.getCity() + "*" + meetingPoint.getStreet() + "*"
                + meetingPoint.getNumber() + "*" + meetingPoint.getCap();
    }

    protected static String getReview(int code){
        Singleton singleton = new Singleton();
        Review review = singleton.getReview(code);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = dateFormat.format(review.getDate());
        return  date + "#" + review.getRating() + "#" +review.getTitle() + "#" + review.getComment() + "#" + review.getReply();
    }

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

    protected static Area decodeArea(String strArea){
        StringTokenizer token = new StringTokenizer(strArea, "*");
        Area area = new Area();
        while (token.hasMoreTokens()){
            area.addPlace(token.nextToken());
        }
        return area;
    }

    protected static HashSet<DogSize> createDogSizeList(boolean small, boolean medium, boolean big, boolean giant){
        HashSet<DogSize> dogSizeList = new HashSet<DogSize>();
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
