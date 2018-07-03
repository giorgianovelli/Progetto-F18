package test;

import client.gui.GUIHome;

import java.text.ParseException;

public class TestGUIHome {
    public static void main(String[] args) {
        try {
            GUIHome guiHome = new GUIHome("RICCARDOGIURA@GMAIL.COM");
            guiHome.setVisible(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
