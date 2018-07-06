package test;

import client.gui.GUIDogSitter;

import java.text.ParseException;

public class TestGUIDogSitter {
    public static void main(String[] args) {
        GUIDogSitter guiDogSitter = null;
        try {
            guiDogSitter = new GUIDogSitter("MARCO.CARTA@GMAIL.COM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        guiDogSitter.setVisible(true);
    }
}
