package test;
import customerClient.CustomerProxy;
import customerClient.gui.GUINewAssignment;
import customerClient.gui.GUISettings;


import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestGUINewAssignment {

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse("14/06/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String test = simpleDateFormat.format(date);


        GUINewAssignment guiNewAssignment = new GUINewAssignment(date, "RICCARDOGIURA@GMAIL.COM");
        guiNewAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiNewAssignment.setVisible(true);
    }
}
