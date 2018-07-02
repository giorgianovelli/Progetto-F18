package test;

import server.Assignment;
import server.DogSitter;
import server.Singleton;

import java.util.HashMap;

public class TestDogSitter {
    public static void main(String[] args) {
        Singleton singleton = new Singleton();
        DogSitter dogSitter = singleton.createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
        HashMap<Integer, Assignment> listAssignment = dogSitter.getAssignmentList();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            System.out.println("\tTEST ASSIGNMENT: " + a.getCode());
        }
    }

}
