package test;
import client.gui.GUINewAssignment;
import client.gui.GUISettings;


import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class TestGUINewAssignment {

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse("02/07/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        GUINewAssignment guiNewAssignment = new GUINewAssignment(date, "RICCARDOGIURA@GMAIL.COM");
        guiNewAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiNewAssignment.setVisible(true);
        */
    }
}
