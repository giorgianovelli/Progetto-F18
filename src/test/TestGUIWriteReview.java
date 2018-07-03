package test;

import client.gui.GUIWriteReview;
import server.Assignment;

import java.util.HashMap;

public class TestGUIWriteReview {
    public static void main(String[] args) {

        String email = "RICCARDOGIURA@GMAIL.COM";
        customerClient.CustomerProxy proxy = new customerClient.CustomerProxy(email);
        HashMap<Integer, Assignment> assignmentList =proxy.getAssignmentList();
        Assignment assignmentProva = assignmentList.get(1);
        GUIWriteReview prova = new GUIWriteReview(assignmentProva, email);
        prova.setVisible(true);



    }
}
