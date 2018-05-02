package test;

import engine.Customer;
import engine.Dog;
import engine.DogSitter;

import static staticClasses.ObjectCreator.createCustomerFromDB;
import static staticClasses.ObjectCreator.createDogFromDB;
import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestObjectCreator {
    public static void main(String[] args) {
        Dog d = createDogFromDB(1);
        Customer c = createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        DogSitter ds = createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
    }
}
