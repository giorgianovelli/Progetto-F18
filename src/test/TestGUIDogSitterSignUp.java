package test;

import client.gui.GUIDOGSITTERSIGNUPPROVAMIA;
import client.gui.GUIDogSitterSignUp;
import client.gui.GUILogin;

public class TestGUIDogSitterSignUp {

    public static void main(String[] args) throws Exception {

        GUILogin guiLogin = new GUILogin();
        GUIDogSitterSignUp guiDogsitterSignUp = new GUIDogSitterSignUp(guiLogin);
        guiDogsitterSignUp.setVisible(true);


    }

}
