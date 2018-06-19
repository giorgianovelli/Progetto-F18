package test;

import dogSitterClient.DogSitterProxy;
import server.Assignment;

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
    }
}
