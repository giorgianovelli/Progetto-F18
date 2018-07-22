package client.gui;

import client.proxy.DogSitterProxy;
import server.DogSitter;

import javax.swing.*;

public class GUIDogSitterChangePassword extends GUIChangePassword {

    /**
     * The dogSitter proxy.
     */
    private DogSitterProxy dogSitterProxy;

    /**
     *  Constructor
     * @param email   the user's email
     * @param guiHome interface from where GUIDogSitterChangePassword is invoked
     */
    public GUIDogSitterChangePassword(String email, GUIHome guiHome) {
        super(email, guiHome);
        dogSitterProxy = new DogSitterProxy(email);

    }


    /**
     *  check if the password entered by the user matches the one entered during registration
     * @param currentPwd password entered by the user to be checked
     * @return true if the password entered by the user is correct, false if it is incorrect
     */
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



    /**
     * check if the password entered in the "newPassword" field matches the "confirmPassword" field
     * @param newPassword new password entered by the user
     * @param confirmPassword  check the password entered
     * @return true if the two passwords coincide false otherwise
     */
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



}
