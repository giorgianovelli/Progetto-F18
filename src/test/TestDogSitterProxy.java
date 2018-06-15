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
        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            System.out.println(a.getCode());
        }
    }
}
