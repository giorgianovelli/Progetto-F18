package test;

import engine.Customer;
import engine.Dog;
import engine.DogSitter;

import static engine.ObjectCreator.createCustomerFromDB;
import static engine.ObjectCreator.createDogFromDB;
import static engine.ObjectCreator.createDogSitterFromDB;

public class TestObjectCreator {
    public static void main(String[] args) {
        Dog d = createDogFromDB(1);
        Customer c = createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        DogSitter ds = createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
    }
}
