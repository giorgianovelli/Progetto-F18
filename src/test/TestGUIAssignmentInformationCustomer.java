package test;


import customerClient.gui.GUIAssignmentInformationCustomer;
import server.Assignment;
import server.Dog;
import server.DogSize;
import server.places.Address;
import java.util.Date;
import java.util.HashSet;
import javax.swing.*;

public class TestGUIAssignmentInformationCustomer {
    public static void main(String[] args) {

        HashSet<Dog> dogList = new HashSet<>();

        for (int i = 0; i < 30; i++) {
            Dog dog = new Dog("Pluto", "Random", DogSize.MEDIUM, 5, 3.5, 2222);
            dogList.add(dog);
        }


        Date startDate = new Date();
        Date endDate = new Date();
        Address address = new Address("Italy", "Pavia", "Strada statale per Voghera", "1", "12000");
        Assignment assingment = new Assignment(1, dogList, startDate, endDate, address);


        GUIAssignmentInformationCustomer gui = new GUIAssignmentInformationCustomer(assingment, "RICCARDOGIURA@GMAIL.COM"); //passaggio assignment
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }
}

