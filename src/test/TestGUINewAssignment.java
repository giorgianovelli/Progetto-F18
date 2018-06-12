package test;
import customerClient.CustomerProxy;
import customerClient.gui.GUINewAssignment;
import customerClient.gui.GUISettings;


import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestGUINewAssignment {

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        GUINewAssignment guiNewAssignment = new GUINewAssignment(date, "RICCARDOGIURA@GMAIL.COM");
        guiNewAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiNewAssignment.setVisible(true);
    }
}
