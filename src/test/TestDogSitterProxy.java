package test;

import dogSitterClient.DogSitterProxy;
import server.Assignment;
import server.Availability;
import server.DogSize;
import server.Review;
import server.dateTime.WorkingTime;
import server.places.Area;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class TestDogSitterProxy {
    public static void main(String[] args) {
        DogSitterProxy proxy = new DogSitterProxy("MARCO.CARTA@GMAIL.COM");
        DogSitterProxy proxyLogin = new DogSitterProxy();
        System.out.println("test login true: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGETTO123"));
        System.out.println("test login false: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGGGGETTO123"));
        /*HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            System.out.println(a.getCode());
        }*/
        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        Assignment a = listAssignment.get(1);
        System.out.println(a.getDateStart());
        System.out.println(proxy.getCustomerNameOfAssignment(1));
        System.out.println(proxy.getCustomerSurnameOfAssignment(1));
        System.out.println(proxy.getReview(1));
        proxy.getName();
        proxy.getSurname();
        proxy.getPassword();
        proxy.getPhoneNumber();
        proxy.getDateOfBirth();
        proxy.getAddress();
        proxy.getPaymentMethod();

        /*proxy.updateName("PIPINO");
        proxy.updateSurname("IL GRANDE");
        proxy.updatePassword("CAMIBAU");
        proxy.updatePhoneNumber("3345522111");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            proxy.updateDateOfBirth(dateFormat.parse("22/12/2000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updateAddress("ITALY", "BOLOGNA", "VIA DELLA MORTADELLA", "6s", "40121");
        Date expiration = new Date();
        try {
            expiration = dateFormat.parse("31/06/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updatePaymentMethod("9999999999999999", "GIULIO", "ADRIATICO", expiration, 333);*/
        /*HashMap<Integer, Review> reviewList = proxy.getReviewList();
        Review r = reviewList.get(1);
        System.out.println(r.getComment());
        proxy.updateAssignmentState(1, true);
        Assignment one = listAssignment.get(1);
        System.out.println(one.getState());*/
        //proxy.updateCashFlag(true);
        System.out.println(proxy.getDogsNumber());
        Area area = proxy.getArea();
        area.printPlaces();
        System.out.println(proxy.isAcceptingCash());
        HashSet<DogSize> listDogSize = proxy.getListDogSize();
        System.out.println("dog size list:");
        for (DogSize ds : listDogSize) {
            System.out.println(ds);
        }
        //System.out.println(proxy.addNewPlaceArea("MILANO"));
        //System.out.println(proxy.removePlaceArea("MILANO"));
        //proxy.updateDogsNumber(7);
        /*int i;
        Availability availability = proxy.getDateTimeAvailability();
        WorkingTime[] workingTimeArray = availability.getArrayDays();
        for (i = 0; i < 7; i++){
            Time start = workingTimeArray[i].getStart();
            Time end = workingTimeArray[i].getEnd();
            System.out.println(start + ", " + end);
        }*/
        proxy.updateListDogSize(true, true, true, true);
    }
}
