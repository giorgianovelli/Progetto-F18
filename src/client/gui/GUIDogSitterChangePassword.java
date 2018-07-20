package client.gui;

import client.proxy.DogSitterProxy;
import server.DogSitter;

import javax.swing.*;

public class GUIDogSitterChangePassword extends GUIChangePassword {


    private DogSitterProxy dogSitterProxy;
    /**
     * Constructor
     *
     * @param email : reference to the user
     */
    public GUIDogSitterChangePassword(String email, GUIHome guiHome) {
        super(email, guiHome);
        dogSitterProxy = new DogSitterProxy(email);

    }

   protected boolean changePasswordFields(String newPassword, String confirmPassword) {
        boolean updatePassword;

        if (newPassword.equals(confirmPassword)) {
            dogSitterProxy.updatePassword(newPassword);
            updatePassword = true;


        } else {
            JOptionPane.showMessageDialog(new JFrame(), "New Password and Confirm Password do not match!", "Password error", JOptionPane.ERROR_MESSAGE);
            textNewPassword.setText("");
            textPasswordConf.setText("");
            updatePassword = false;
        }


        return updatePassword;


    }

    protected boolean checkPassword(String currentPwd) {
        boolean matchPassword;

        String currentPwdProxy = dogSitterProxy.getPassword();

        if (currentPwd.equals(currentPwdProxy)) {
            JOptionPane.showMessageDialog(new JFrame(), "the current password is correct", "", JOptionPane.INFORMATION_MESSAGE);
            matchPassword = true;

        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Current Password is not correct!", "Password error", JOptionPane.ERROR_MESSAGE);
            textCurrentPassword.setText("");
            matchPassword = false;
        }


        return matchPassword;

    }



}
