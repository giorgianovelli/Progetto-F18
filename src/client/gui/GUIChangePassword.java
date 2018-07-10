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
    private JPasswordField textOldPassword = new JPasswordField();

    private String newPassword;
    private String confirmPassword;
    private String pass2; //= new String();
    private String currentPassword; // = new String();

    private String inputPass = new String();

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    //attributi per client-server
    private CustomerProxy proxy;
    private String email;


//______________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
     *
     * @param email: reference to the user
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


    private void initComponents() {

        /**
         * JPANEL
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

        /**
         *  TODO  METODO BOTTONI  (CONFERMA MODIFICA PASSWORD da SITEMARE)
         */

        ActionListener changepassword = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent passwordAe) {

                if (passwordAe.getActionCommand().equals("Confirm")) {
                    //se inserisco password corretta e lascio gli altri campi vuoti si impalla DA SISTEMARE

                    boolean matchPass = controlloPassword(inputPass);
                    if ((matchPass)) {
                        changePasswordFields();
                        // dispose();
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
     * controllo se le nuova password inserita nel campo "NewPassword" corrisponda  al campo "ConfirmPassword"
     */

    public boolean changePasswordFields() {
        boolean updatePassword;
        char[] strNewPassword = textNewPassword.getPassword();
        char[] strConfirmPassword = textPasswordConf.getPassword();

        if (strNewPassword.length == strConfirmPassword.length) {
            for (int i = 0; i < strNewPassword.length; i++) {
                newPassword += strNewPassword[i];
                confirmPassword += strConfirmPassword[i];
            }
        }

        String newPassword = new String(textNewPassword.getPassword());
        String confirmPassword = new String(textPasswordConf.getPassword());

        if (newPassword.equals(confirmPassword)) {
            proxy.updatePassword(newPassword);
            JOptionPane.showMessageDialog(new JFrame(), "you have changed password correctly!", "", JOptionPane.INFORMATION_MESSAGE);

           /* System.out.println("la nuova password inserita è: " + newPassword);
            System.out.println("la conferma password corrente è:" + confirmPassword);*/
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
     * verifica la validità della password immessa
     * @param inputPass2  password da verificare
     * @return true se corretta, false se errata
     */

    public boolean controlloPassword(String inputPass2) {
        boolean matchPassword;

        char[] pass = textOldPassword.getPassword();
        char[] strCurrentPassword = textCurrentPassword.getPassword();


        if (pass.length == strCurrentPassword.length) {
            for (int i = 0; i < pass.length; i++) {
                pass2 += pass[i];
                currentPassword += strCurrentPassword[i];
            }
        }


        String currentPassword = proxy.getPassword();
        String pass2 = new String(textCurrentPassword.getPassword());

        if (pass2.equals(currentPassword)) {

            JOptionPane.showMessageDialog(new JFrame(), "old password match currentpassword", "", JOptionPane.INFORMATION_MESSAGE);


            matchPassword = true;

        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Current Password is not correct!", "Password error", JOptionPane.ERROR_MESSAGE);
            matchPassword = false;
        }


        return matchPassword;

    }

}