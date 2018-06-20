package test;

import customerClient.CustomerProxy;
import server.Assignment;
import server.Customer;
import server.Dog;
import server.Singleton;
import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class TestCustomerExecEnum {
    public static void main(String[] args) {
        CustomerProxy proxyLogin = new CustomerProxy();
        if (proxyLogin.customerAccessDataVerifier("RICCARDOGIURA@GMAIL.COM", "PROVAPROVA123")){
            System.out.println("Allowed!");
        } else {
            System.out.println("Denied!");
        }

        CustomerProxy proxy = new CustomerProxy("RICCARDOGIURA@GMAIL.COM");
        HashMap<Integer, Assignment> assignmentList = proxy.getAssignmentList();
        /*proxy.getDogSitterNameOfAssignment(2);
        proxy.getDogSitterSurnameOfAssignment(2);
        proxy.getName();
        proxy.getSurname();
        proxy.getPassword();
        proxy.getPhoneNumber();
        proxy.getDateOfBirth();
        proxy.getAddress();
        proxy.getPaymentMethod();
        proxy.updateName("RICK");
        proxy.updateSurname("GIU");
        proxy.updatePassword("PROVA");
        proxy.updatePhoneNumber("1111111111");*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdate = new Date();
        try {
            birthdate = dateFormat.parse("23/06/1996");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //proxy.updateDateOfBirth(birthdate);
        proxy.updateAddress("ITALY", "PAVIA", "VIA FERRATA", "1", "27100");
        //System.out.println(proxy.updatePaymentMethod("2222221112222222", "RICK", "GIU", new Date(), 555));
        HashSet<Dog> selectedDogs = new HashSet<Dog>(2);
        //creare oggetti Dog
        Singleton singleton = new Singleton();
        Dog d = singleton.createDogFromDB(3);
        selectedDogs.add(d);
        d = singleton.createDogFromDB(4);
        selectedDogs.add(d);
        Date start = new Date();
        Date end = new Date();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            start = dateFormat2.parse("24/07/2018 11:00");
            end = dateFormat2.parse("24/07/2018 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("search:");
        proxy.search(start, end, new Address("ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121"), selectedDogs, false);

        proxy.estimatePriceAssignment(selectedDogs, start, end);
        System.out.println("addAssignment:");
        proxy.addAssignment("MARCO.CARTA@GMAIL.COM", start, end, selectedDogs, new Address("ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121"), false);
        /*proxy.removeAssignment(5);
        proxy.addReview(1, "MARCO.CARTA@GMAIL.COM", 4, "prova", "funziona! Molto utile!");
        proxy.removeReview(1);
        proxy.getAssignmentList();
        System.out.println("__________________");*/
        //proxy.addDog("RICCARDOGIURA@GMAIL.COM", "TIBERIO", "BULLDOG", start, 16);
        //proxy.disableDog(3);
        //proxy.getDogList();
        //System.out.println(proxy.getReview(1).getComment());
    }
}
