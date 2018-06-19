package enumeration;

import server.Assignment;
import server.Dog;
import server.Review;
import server.Singleton;
import server.places.Address;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;

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
}
