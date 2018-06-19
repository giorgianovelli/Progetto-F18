package test;

import dogSitterClient.DogSitterProxy;
import server.Assignment;
import server.Review;

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
        proxy.updateCashFlag(true);
    }
}
