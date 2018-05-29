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
            /*

            Dog dog1 = new Dog("Paperino", "Stronza", DogSize.MEDIUM, 5, 3.5, 2222);
            Dog dog2 = new Dog("Pippo", "Stronza", DogSize.MEDIUM, 5, 3.5, 2222);
            Dog dog3 = new Dog("Topolino", "Stronza", DogSize.MEDIUM, 5, 3.5, 2222);
            Dog dog4 = new Dog("Cane", "Stronza", DogSize.MEDIUM, 5, 3.5, 2222);
            dogList.add(dog1);
            dogList.add(dog2);
            dogList.add(dog3);
            dogList.add(dog4);
            */


        Date startDate = new Date();
        Date endDate = new Date();
        Address address = new Address("Italy", "Pavia", "Via Random", "1", "12000Random");
        Assignment assingment = new Assignment(1, dogList, startDate, endDate, address);


        GUIAssignmentInformationCustomer gui = new GUIAssignmentInformationCustomer(assingment); //passaggio assignent
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }
}

