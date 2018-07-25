package client.gui;

import client.proxy.CustomerProxy;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class allows to change the password of the user account.
 */
public class GUIChangePassword extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 400;

    /**
     * Frame height.
     */
    final int HEIGHT = 300;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Out panel, it contains all the other panels.
     */
    private JPanel panelOut = new JPanel();

    /**
     * The panel that contains password fields (Current Password, New Password, Confim New Passord).
     */
    private JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to confirm  the change of password or cancel it
     */
    private JPanel panelButton = new JPanel();

    /**
     * The label for the Current Password
     */
    private JLabel labelCurrentPassword = new JLabel("Current Password:", SwingConstants.LEFT);

    /**
     * The label for the New Password
     */
    private JLabel labelNewPassword = new JLabel("New Password:", SwingConstants.LEFT);
    /**
     * The label for Confirm New Password
     */
    private JLabel labelPasswordConf = new JLabel("Confirm New Password:", SwingConstants.LEFT);

    /**
     * The Password field of Current Password
     */
    protected JPasswordField textCurrentPassword = new JPasswordField(SwingConstants.RIGHT);

    /**
     * The Password field of New Password
     */
    protected JPasswordField textNewPassword = new JPasswordField(SwingConstants.RIGHT);

    /**
     * The Password field of Confirm New Password
     */
    protected JPasswordField textPasswordConf = new JPasswordField(SwingConstants.RIGHT);

    /**
     *  The Current Password
     */
    private String currentPassword;

    /**
     * The New Password
     */
    private String newPassword;
    /**
     * The Confirm New Password
     */
    private String confirmPassword;

    /**
     * The button that allows to confirm changes
     */
    private JButton buttonConfirm = new JButton("Confirm");
    /**
     * The button that allows to cancel changes and close the frame
     */
    private JButton buttonCancel = new JButton("Cancel");

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The user's email.
     */
    private String email;

    /**
     * This GUI.
     */
    private GUIChangePassword guiChangePassword;


    /**
     * Constructor
     * @param email   the user's email.
     * @param guiHome interface from where GUIChangePassword is invoked
     */

    public GUIChangePassword(String email, GUIHome guiHome) {
        setTitle("Change Password");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.email = email;
        this.proxy = new CustomerProxy(email);
        guiChangePassword = this;
        guiHome.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiHome.setEnabled(true);
            }
        });


        initComponents();

    }


    /**
     * initialize the GUI components
     */
    private void initComponents() {

        /**
         * Panels
         */

        panelData.setLayout(new GridLayout(3, 1, 20, 30));
        panelData.setBorder(BorderFactory.createTitledBorder("Password Fields: "));
        panelData.add(labelCurrentPassword);
        panelData.add(textCurrentPassword);
        panelData.add(labelNewPassword);
        panelData.add(textNewPassword);
        panelData.add(labelPasswordConf);
        panelData.add(textPasswordConf);
        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        add(panelOut);
        panelButton.setLayout(new GridLayout(1, 2, 10, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);


        ActionListener changepassword = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent passwordAe) {

                if (passwordAe.getActionCommand().equals("Confirm")) {
                    currentPassword = "";
                    newPassword = "";
                    confirmPassword = "";

                    currentPassword = readPassword(textCurrentPassword.getPassword());
                    newPassword = readPassword(textNewPassword.getPassword());
                    confirmPassword = readPassword(textPasswordConf.getPassword());


                    if (currentPassword == "" || newPassword == "" || confirmPassword == "") {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);
                    }

                    else if (checkPassword(currentPassword)) {
                        if(!(getSpecialCharacterCount(newPassword))){
                            if (changePasswordFields(newPassword, confirmPassword)) {
                                JOptionPane.showMessageDialog(new JFrame(), "you have changed password correctly!", "", JOptionPane.INFORMATION_MESSAGE);
                                guiChangePassword.dispatchEvent(new WindowEvent(guiChangePassword, WindowEvent.WINDOW_CLOSING));
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), " New Password is invalid!", "Password error", JOptionPane.ERROR_MESSAGE);

                        }

                    }

                }
            }
        };
        buttonCancel.addActionListener(e -> guiChangePassword.dispatchEvent(new WindowEvent(guiChangePassword, WindowEvent.WINDOW_CLOSING)));
        buttonConfirm.addActionListener(changepassword);
    }


    /**
     * reads the password entered by the user
     * @param password password entered by the user
     * @return string that contains the read password
     */
    private String readPassword(char[] password) {
        String pwd = "";
        for (int i = 0; i < password.length; i++) {
            pwd += password[i];
        }
        return pwd;
    }


    /**
     * check if the password entered by the user matches the one entered during registration
     * @param currentPwd password entered by the user to be checked
     * @return true if the password entered by the user is correct, false if it is incorrect
     */

    protected boolean checkPassword(String currentPwd) {
        boolean matchPassword;

        String currentPwdProxy = proxy.getPassword();

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
     * check if the new password entered by the user contains special characters
     * @param s password to check
     * @return true if there are special characters in the password
     */
    public boolean getSpecialCharacterCount(String s) {

        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        if(s!=null || !(s.trim().isEmpty())) {
            for (int i = 0; i < s.length(); i++) {

                if (specialChars.contains(s.substring(i, i + 1))) {
                    return true;

                }

            }
        }

        return false;


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
            proxy.updatePassword(newPassword);
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