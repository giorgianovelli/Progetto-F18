package test;

import server.Customer;
import server.DogSitter;
import engine.PlatformEngine;
import server.Singleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

//import static staticClasses.ObjectCreator.createCustomerFromDB;
//import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestSearchEngine {
    public static void main(String[] args) {
        PlatformEngine se = new PlatformEngine();
        Singleton singleton = new Singleton();
        Customer c = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        DogSitter ds = singleton.createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = date.parse("14/05/2018 00:00");
            endDate = date.parse("14/05/2018 23:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashSet<DogSitter> dogSitterList = se.search(startDate,endDate, ds.getAddress(), c.getDogList(), true);
    }
}
