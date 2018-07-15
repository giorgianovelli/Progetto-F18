package test;



import client.gui.GUIAssignmentInformationDogsitter;
import client.gui.GUIDogSitter;
import server.Assignment;
import server.Dog;
import server.Singleton;
import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import javax.swing.*;

public class TestGUIAssignmentInformationCustomer {
    public static void main(String[] args) {

        HashSet<Dog> dogList = new HashSet<>();

        Singleton singleton = new Singleton();
        Dog d = singleton.createDogFromDB(3);
        dogList.add(d);
        d = singleton.createDogFromDB(4);
        dogList.add(d);

        GUIDogSitter guiDogSitter = null;

        /*
        for (int i = 0; i < 30; i++) {
            Dog dog = new Dog("Pluto", "Random", DogSize.MEDIUM, 5, 3.5, 2222);
            dogList.add(dog);
        }

       */

        try {
             guiDogSitter = new GUIDogSitter("MARCO.CARTA@FGMAIL.COM");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = simpleDateFormat.parse("04/06/2018 10:00");
            endDate = simpleDateFormat.parse("04/06/2018 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Address address1 = singleton.getAddressFromDB("RICCARDOGIURA@GMAIL.COM");
        Assignment assingment = new Assignment(1, dogList, startDate, endDate, address1);


        GUIAssignmentInformationDogsitter gui = new GUIAssignmentInformationDogsitter(assingment, "RICCARDOGIURA@GMAIL.COM", guiDogSitter); //passaggio assignment
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);

    }
}

