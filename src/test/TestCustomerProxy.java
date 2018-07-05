package test;

import client.proxy.CustomerProxy;
import server.Dog;
import server.Singleton;

import java.util.Date;
import java.util.HashSet;

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

        /*SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
        System.out.println("remove a review: " + proxy.removeReview(1));
        HashMap<Integer, Review> reviewList = proxy.getReviewList();
        for (Integer key : reviewList.keySet()) {
            System.out.println(reviewList.get(key).getCode());
        }
        System.out.println("add dog: " + proxy.addDog("RICCARDOGIURA@GMAIL.COM","CANETEST", "AKITA", 1, 20));*/

        /*System.out.println("add a review: " + proxy.addReview(1, "MARCO.CARTA@GMAIL.COM", 4, "prova", "bla, bla, bla,"));
        System.out.println(proxy.getAddress().toString());
        System.out.println(proxy.getDateOfBirth());
        System.out.println(proxy.getPassword());
        System.out.println(proxy.disableDog(3));*/
        /*HashSet<Dog> dogList = proxy.getDogList();
        Singleton singleton = new Singleton();
        Dog hermes = singleton.createDogFromDB(3);
        Dog alfonso = singleton.createDogFromDB(5);
        Dog tony = singleton.createDogFromDB(11);
        for (Dog d : dogList) {
            if (d.getID() == hermes.getID()){
                System.out.println("Hermes is in dogList!");
            }

            if (d.getID() == alfonso.getID()){
                System.out.println("Alfonso is in dogList!");
            }

            if (d.getID() == tony.getID()){
                System.out.println("Tony is in dogList!");
            }
        }*/
        //proxy.isInCashPaymentMethodOfAssignment(5);
        //proxy.updateDogName(3, "TIBERIO");
        //Date date = new Date();
        //proxy.updateDogAge(3, date);
        proxy.updateDogWeight(3, 66.5);
    }
}
