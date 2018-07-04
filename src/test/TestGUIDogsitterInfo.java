package test;

import client.gui.GUIDogsitterInfo;
import client.proxy.DogSitterProxy;

public class TestGUIDogsitterInfo {


    public static void main(String[] args) {


        String email = "MARCO.CARTA@GMAIL.COM";


        GUIDogsitterInfo guiDogsitterInfo = new GUIDogsitterInfo(email);
        guiDogsitterInfo.setVisible(true);


    }
}
