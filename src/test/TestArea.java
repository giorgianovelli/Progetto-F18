package test;

import engine.Address;
import engine.Area;
import engine.DogSitter;

import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class TestArea {
    public static void main(String[] args) {
        DogSitter ds = createDogSitterFromDB("MARCO.CARTA@GMAIL.COM");
        ds.printArea();
    }
}
