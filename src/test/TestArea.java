package test;

import server.DogSitter;
import server.Singleton;

//import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestArea {
    public static void main(String[] args) {
        Singleton singleton = new Singleton();
        DogSitter ds = singleton.createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
        ds.printArea();
    }
}
