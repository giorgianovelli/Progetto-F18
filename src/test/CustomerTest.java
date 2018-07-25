package test;

import server.Customer;
import server.Dog;
import server.Singleton;
import server.bank.PaymentMethod;
import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CustomerTest {


    @org.junit.Test
    public void addAssignment() {

        Singleton singleton = new Singleton();
        HashSet<Dog> dogs = singleton.getDogListFromDB(1);
        Date startDate = new Date();
        Date endDate = new Date ();
        Boolean payment = true;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            startDate = simpleDateFormat1.parse("30/07/2018 12:00");
            endDate = simpleDateFormat1.parse("30/07/2018 13:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Address address = singleton.getAddressFromDB("RICCARDOGIURA@GMAIL.COM");
        PaymentMethod paymentMethod = singleton.getPaymentMethodFromDB("5311234567899811");
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.addAssignment("MARCO.CARTA@GMAIL.COM", startDate, endDate, dogs, address, payment);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void removeAssignment() {

        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.removeAssignment(1);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void addReview() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.addReview(1, "MARCO.CARTA@GMAIL.COM", 1, "TEST", "TEST");

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void removeReview() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.removeReview(1);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void addDog() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        Date birthDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            birthDate = simpleDateFormat.parse("01/01/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean result = customer.addDog("RICCARDOGIURA@GMAIL.COM", "TEST", "PITBULL", birthDate, 10);
        assertThat(result, is(true));

    }

    @org.junit.Test
    public void disableDog() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.disableDog(3);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void search() {

        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        HashSet<Dog> dogs = singleton.getDogListFromDB(1);
        Date startDate = new Date();
        Date endDate = new Date ();
        Boolean payment = true;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            startDate = simpleDateFormat1.parse("30/07/2018 12:00");
            endDate = simpleDateFormat1.parse("30/07/2018 13:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Address address = singleton.getAddressFromDB("RICCARDOGIURA@GMAIL.COM");


        HashSet<String> result = customer.search(startDate, endDate, address, dogs, payment);
        HashSet<String>  dogsitters = new HashSet<>();
        dogsitters.add("MARCO.CARTA@GMAIL.COM");
        dogsitters.add("FABIOVERDI@GMAIL.COM");
        assertThat(result, is(dogsitters));


    }

    @org.junit.Test
    public void estimatePriceAssignment() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        HashSet<Dog> dogs = singleton.getDogListFromDB(1);
        Date startDate = new Date();
        Date endDate = new Date ();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            startDate = simpleDateFormat1.parse("30/07/2018 12:00");
            endDate = simpleDateFormat1.parse("30/07/2018 13:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double result = customer.estimatePriceAssignment(dogs, startDate, endDate);

        assertThat(result, is(6.0));
    }

    @org.junit.Test
    public void updateName() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.updateName("FABIO");

        assertThat(result, is(true));
    }

    @org.junit.Test
    public void updateSurname() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.updateSurname("CAROLO");

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void updatePassword() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.updatePassword("PASSWORD");

        assertThat(result, is(true));


    }

    @org.junit.Test
    public void updatePhoneNumber() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.updatePhoneNumber("0123456789");

        assertThat(result, is(true));


    }

    @org.junit.Test
    public void updateDateOfBirth() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        Date birthDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            birthDate = simpleDateFormat.parse("01/01/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean result = customer.updateDateOfBirth(birthDate);

        assertThat(result, is(true));


    }

    @org.junit.Test
    public void updateAddress() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.updateAddress("ITALY", "TORTONA", "VIA EMILIA", "1", "1A");

        assertThat(result, is(true));


    }

    @org.junit.Test
    public void updatePaymentMethod() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        Date expire = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            expire = simpleDateFormat.parse("01/01/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean result = customer.updatePaymentMethod("5311223334450000","FABIO", "TEST",expire, "000");

        assertThat(result, is(true));


    }

    @org.junit.Test
    public void isInCashPaymentMethodOfAssignment() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.isInCashPaymentMethodOfAssignment(1);

        assertThat(result, is(false));
    }

    @org.junit.Test
    public void updateDogName() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        boolean result = customer.updateDogName(3, "TEST");

        assertThat(result, is(true));
    }

    @org.junit.Test
    public void updateDogAge() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");

        Date birthDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            birthDate = simpleDateFormat.parse("01/01/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean result = customer.updateDogAge(3, birthDate);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void updateDogWeight() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.updateDogWeight(3, 2);

        assertThat(result, is(true));

    }

    @org.junit.Test
    public void updateDogBreed() {
        Singleton singleton = new Singleton();
        Customer customer = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        boolean result = customer.updateDogBreed(3, "PITBULL");

        assertThat(result, is(true));

    }
}
