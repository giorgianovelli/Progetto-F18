package test;

import client.gui.GUIDogSitter;
import client.gui.GUIHome;
import client.gui.GUIShowDogsitterAssignment;
import client.proxy.CustomerProxy;
import client.gui.GUIWriteReview;
import client.proxy.DogSitterProxy;
import server.Assignment;

import java.text.ParseException;
import java.util.HashMap;

import static enumeration.CalendarState.NORMAL;

public class TestGUIShowDogsitterAssignment {
    public static void main(String[] args) {

        String email1 = "MARCO.CARTA@GMAIL.COM";
        String email2 = "RICCARDOGIURA@GMAIL.COM";

        DogSitterProxy proxy = new DogSitterProxy(email1);

        /*

        HashMap<Integer, Assignment> list = proxy.getAssignmentList();

        System.out.println(list.size());
        */

        /*
        CustomerProxy proxy1 = new CustomerProxy(email2);
        HashMap<Integer, Assignment> list2 = proxy1.getAssignmentList();
        System.out.println((list2.size()));*/


        GUIDogSitter guiDogSitter = null;
        try {
            guiDogSitter = new GUIDogSitter("MARCO.CARTA@GMAIL.COM");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Errore");
        }


        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(NORMAL, proxy.getAssignmentList(), email2, guiDogSitter );
        guiShowDogsitterAssignment.setVisible(true);

        System.out.println(proxy.getCustomerNameOfAssignment(1));






    }
}
