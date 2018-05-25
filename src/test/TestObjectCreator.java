package test;

import server.Customer;
import server.Dog;
import server.DogSitter;
import server.Singleton;

//import static staticClasses.ObjectCreator.createCustomerFromDB;
//import static staticClasses.ObjectCreator.createDogFromDB;
//import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestObjectCreator {
    public static void main(String[] args) {
        Singleton singleton = new Singleton();
        Dog d = singleton.createDogFromDB(3);
        Customer c = singleton.createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        DogSitter ds = singleton.createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
    }
}
