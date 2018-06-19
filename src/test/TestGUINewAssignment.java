package test;
import customerClient.CustomerProxy;
import customerClient.gui.GUINewAssignment;
import customerClient.gui.GUISettings;


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
            date = simpleDateFormat.parse("14/06/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String test = simpleDateFormat.format(date);

        Date dateEnd = new Date();
        String day = "01";
        String month = "01";
        String year = "2018";
        String toHour = "11";
        String toMinute = "00";

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        dateEnd = new Date();
        try {
            dateEnd = simpleDateFormat1.parse(day + "/" + month + "/" + year + " " + toHour + ":" + toMinute);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        System.out.println(dateEnd.toString());


        GUINewAssignment guiNewAssignment = new GUINewAssignment(date, "RICCARDOGIURA@GMAIL.COM");
        guiNewAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiNewAssignment.setVisible(true);
    }
}
