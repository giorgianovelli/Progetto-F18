package test;

import client.gui.GUIChooseDogsitter;
import client.gui.GUINewAssignment;
import server.Dog;
import server.Singleton;
import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class TestGUIChooseDogsitter {

    public static void main(String[] args) {

        HashSet<String> test = new HashSet<>();
        test.add("MARCO.CARTA@GMAIL.COM");
        test.add("MATTEOSALVINI@GMAIL.COM");
        test.add("FILIPPO_ALFIERI@GMAIL.COM");
        test.add("ERICA.ROSSI@GMAIL.COM");
        test.add("MARIOBIANCHI@LIBERO.COM");
        test.add("SERGIOMATTARELLA@GMAIL.COM");
        test.add("LUIGIDIMAIO@GMAIL.COM");

        Date startDate;
        Date endDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            startDate = simpleDateFormat.parse("16/07/2018 03:00");
            endDate = simpleDateFormat.parse("16/07/2018 04:00");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Singleton singleton = new Singleton();
        Dog uno = singleton.createDogFromDB(1);
        HashSet<Dog> dogHashSet = new HashSet<>();
        Address address = singleton.getAddressFromDB("RICCARDOGIURA@GMAIL.COM");
        dogHashSet.add(uno);
        boolean booleans = true;


        test.add("");
        test.add("");
        test.add("");
        test.add("");
        test.add("");
        test.add("");
        test.add("");





    }
}
