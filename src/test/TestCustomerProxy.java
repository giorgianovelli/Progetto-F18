package test;

import customerClient.CustomerProxy;
import server.Dog;
import server.Review;
import server.Singleton;
import server.places.Address;
import server.tools.dateTime.DateTimeDHMS;

import javax.management.DescriptorAccess;
import java.awt.desktop.SystemSleepEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import static server.tools.dateTime.DateTimeTools.dateTimeDiff;

public class TestCustomerProxy {
    public static void main(String[] args) {
        CustomerProxy proxy = new CustomerProxy("RICCARDOGIURA@GMAIL.COM");
        /*Review review = proxy.getReview(2);
        System.out.println(review.getTitle());
        proxy.updateCustomerName("PIPINO");
        proxy.updateCustomerSurname("IL BREVE");
        proxy.updateCustomerPassword("CAMIBAU");
        proxy.updateCustomerPhoneNumber("3333333333");
        proxy.updateCustomerDateOfBirth("22/11/1963");
        proxy.updateCustomerAddress("ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expiration = new Date();
        try {
            expiration = dateFormat.parse("31/01/2010");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updateCustomerPaymentMethod("7372989101832834", "BENEDETTO", "SEDICESIMO", expiration, 555);*/
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date start = new Date();
        Date end = new Date();
        try {
            start = dateFormat2.parse("05/06/2018 11:00");
            end = dateFormat2.parse("05/06/2018 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashSet<Dog> selectedDogs = new HashSet<Dog>(2);
        //creare oggetti Dog
        Singleton singleton = new Singleton();
        Dog d = singleton.createDogFromDB(3);
        selectedDogs.add(d);
        d = singleton.createDogFromDB(4);
        selectedDogs.add(d);

        proxy.search(start, end, new Address("ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121"), selectedDogs, false);
        System.out.println("ext: " + proxy.estimatePriceAssignment(selectedDogs, start, end));
        System.out.println("conf: " + proxy.addAssignment("LUIGIDIMAIO@GMAIL.COM", start, end, selectedDogs, new Address("ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121")));
        System.out.println(proxy.getCustomerPaymentMethod().getNumber());
        System.out.println("remove assignment: " + proxy.removeAssignment(100));
        System.out.println("add a review: " + proxy.addReview(1, "MARCO.CARTA@GMAIL.COM", 4, "prova", "bla, bla, bla,", "recensione veramente utile!"));
    }
}
