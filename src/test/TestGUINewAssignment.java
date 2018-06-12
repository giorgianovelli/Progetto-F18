package test;
import customerClient.CustomerProxy;
import customerClient.gui.GUINewAssignment;
import customerClient.gui.GUISettings;


import javax.swing.*;

public class TestGUINewAssignment {

    public static void main(String[] args) {

        GUINewAssignment guiNewAssignment = new GUINewAssignment();
        guiNewAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiNewAssignment.setVisible(true);
    }
}
