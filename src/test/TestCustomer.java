package test;

import engine.*;
import enumeration.DogSize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import static staticClasses.ObjectCreator.createCustomerFromDB;
import static staticClasses.ObjectCreator.createDogFromDB;
import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestCustomer {
    public static void main(String[] args) throws ParseException {
        //test addAssignment(...)
        HashSet<Dog> selectedDogs = new HashSet<Dog>(2);
        //creare oggetti Dog
        Dog d = createDogFromDB(3);
        selectedDogs.add(d);
        //selectedDogs.add(scooby);
        DogSitter ds = createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
        Customer c = createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        String strEnd = "07/05/2018 14:00";
        Date end = date.parse(strEnd);
        //c.addAssignment(ds, new Date(), end, selectedDogs, ds.getAddress());
        c.removeAssignment(3);

        /*String strBirthDS = "03/04/1980 00:00";
        Date birthDS = date.parse(strBirthC);
        String strDateStartAssignment = "30/04/2018 11:00";
        Date dateStartAssignment = date.parse(strDateStartAssignment);
        String strDateEndAssignment = "30/05/2018 18:00";
        Date dateEndAssignment = date.parse(strDateEndAssignment);
        Address customerAddress = new Address("Italy", "Pavia", "Via Ferrata", "1", "27100");
        PaymentMethod customerPaymentMethod = new PaymentMethod("Pippo", "Baudo","1234567890123456", dateEndAssignment, 123, 1000);
        Customer c = new Customer("pippo@email.it", "Pippo", "Baudo", "password", "1234567890", birthC, customerAddress, customerPaymentMethod);
        Address dogSitterAddress = new Address("Italy", "Pavia", "Via Mascheroni", "12", "27100");
        PaymentMethod dogSitterPaymentMethod = new PaymentMethod("Paperino", "Paperini","1234567935123456", dateEndAssignment, 456, 15345);
        DogSitter ds = new DogSitter("paperino@email.it", "Paperino", "Paperini", "pass", "0987654321", birthDS, dogSitterAddress, dogSitterPaymentMethod);
        c.addAssignment(ds, dateStartAssignment, dateEndAssignment, selectedDogs, customerAddress);

        //test removeAssignment(...)
        c.removeAssignment("30/04/2018 11:00_paperino@email.it_pippo@email.it");
        //c.removeAssignment("30/04/2018 11:00_paperino@email.it_pippo@email.it");

        //test addReview(...)
        String strDateReview = "10/05/2018 06:32";
        Date dateReview = date.parse(strDateReview);
        int rating = 5;
        String title = "Very good!";
        String comment = "This dog sitter is very good";
        c.addReview(ds, dateReview, rating, title, comment);

        //test removeReview
        c.removeReview("10/05/2018 06:32_paperino@email.it_pippo@email.it");
        //c.removeReview("10/05/2018 06:32_paperino@email.it_pippo@email.it");

        //test listAssignment(...)
        c.listAssignment();

        //test listReview(...)
        c.listReview();*/
    }
}
