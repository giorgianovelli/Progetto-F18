package test;

import customerClient.gui.GUIChooseDogsitter;

import java.util.HashSet;

public class TestGUIChooseDogsitter {

    public static void main(String[] args) {

        HashSet<String> test = new HashSet<>();

        GUIChooseDogsitter guiChooseDogsitter = new GUIChooseDogsitter(test);
        guiChooseDogsitter.setVisible(true);
    }
}
