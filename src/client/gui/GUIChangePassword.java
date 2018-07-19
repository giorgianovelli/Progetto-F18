package client.gui;

import client.proxy.CustomerProxy;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIChangePassword extends JFrame {
    final int WIDTH = 400;
    final int HEIGHT = 300;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();

    private JLabel labelCurrentPassword = new JLabel("Current Password:", SwingConstants.LEFT);
    private JLabel labelNewPassword = new JLabel("New Password:", SwingConstants.LEFT);
    private JLabel labelPasswordConf = new JLabel("Confirm New Password:", SwingConstants.LEFT);

    private JPasswordField textCurrentPassword = new JPasswordField(SwingConstants.RIGHT);
    private JPasswordField textNewPassword = new JPasswordField(SwingConstants.RIGHT);
    private JPasswordField textPasswordConf = new JPasswordField(SwingConstants.RIGHT);

    private String newPassword;
    private String confirmPassword;
    private String currentPassword;

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    //attributi per client-server
    private CustomerProxy proxy;
    private String email;


//______________________________________________________________________________________________________________________________________________________________

    /**
     * Costruttore
     *
     * @param email: riferimento all'utente
     */



    public GUIChangePassword(String email) {
        setTitle("Change Password");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.email = email;
        this.proxy = new CustomerProxy(email);


        initComponents();

    }

//______________________________________________________________________________________________________________________________________________________________

    /**
     * inizializza le componenti dell'interfaccia
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


        //-----------------------------------------------------------------------------------


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
                                dispose();

                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), " New Password is invalid!", "Password error", JOptionPane.ERROR_MESSAGE);

                        }

                    }

                }
                if (passwordAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }
            }
        };
        buttonCancel.addActionListener(changepassword);
        buttonConfirm.addActionListener(changepassword);
    }


//______________________________________________________________________________________________________________________________________________________________


    /**
     * controlla se la password inserita nel campo "newPassword" corrisponda al campo "confirmPassword"
     * @param newPassword nuova password inserita dall'utente
     * @param confirmPassword  controlla la password inserita
     * @return true se le due password coincidono false altrimenti
     */

    public boolean changePasswordFields(String newPassword, String confirmPassword) {
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


//______________________________________________________________________________________________________________________________________________________________


    /**
     * verifica se la password inserita dall'utente coincida con quella immessa in fase di registrazione
     * @param currentPwd password inserita dall'utente da controllare
     * @return true se corretta, false se errata
     */

    public boolean checkPassword(String currentPwd) {
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


//______________________________________________________________________________________________________________________________________________________________

    /**
     * legge password inserita dall'utente
     * @param password password inserita dall'utente
     * @return stringa che contiene la password letta
     */
    private String readPassword(char[] password) {
        String pwd = "";
        for (int i = 0; i < password.length; i++) {
            pwd += password[i];
        }
        return pwd;
    }


//______________________________________________________________________________________________________________________________________________________________

    /**
     * controlla se la nuova password inserita dall'utente contenga caratteri speciali
     * @param s password da controllare
     * @return true se nella password ci sono caratteri speciali
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






}