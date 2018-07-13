package test;

import client.gui.GUIAssignmentInformationDogsitter;
import client.proxy.DogSitterProxy;
import server.Assignment;
import server.DogSitter;

public class TestGUIAssignmentInformationDogSitter {
    public static void main(String[] args) {
        DogSitterProxy proxy = new DogSitterProxy("MARCO.CARTA@GMAIL.COM");
        Assignment a = proxy.getAssignmentList().get(1);
        GUIAssignmentInformationDogsitter guiAssignmentInformationDogsitter = new GUIAssignmentInformationDogsitter(a, "MARCO.CARTA@GMAIL.COM");
        guiAssignmentInformationDogsitter.setVisible(true);
    }
}
