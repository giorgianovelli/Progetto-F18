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

    // String strCurrentPassword = proxy.getPassword();
    //private String currentPassword = new String();
    private String newPassword = new String();
    private String confirmPassword = new String();


    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    //attributi per client-server
    private CustomerProxy proxy;
    private String email;

//______________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
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

        // String strCurrentPassword = proxy.getPassword();
      /*  textCurrentPassword.setText(strCurrentPassword);
        textCurrentPassword.setEditable(true);
        labelCurrentPassword.setLabelFor(textCurrentPassword);

       /* textPasswordConf.setText("");
        textPasswordConf.setEditable(true);
        labelPasswordConf.setLabelFor(textPasswordConf);*/

        //-----------------------------------------------------------------------------------

        /**
         *  TODO  METODO BOTTONI  (CONFERMA MODIFICA PASSWORD da SITEMARE)
         */

        ActionListener changepassword = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent passwordAe) {

                if (passwordAe.getActionCommand().equals("Confirm")) {
                    changePasswordFields();

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
     * metodo per controllare se le nuova password inserita nel campo "NewPassword" corrisponda  al campo "ConfirmPassword"
     * inoltre gestisce l'errore in caso le due password non corrispondano
     */
    //TODO MANCA CONTROLLO SULLA PASSWORD INSERITA DALL'UTENTE

    public boolean changePasswordFields() {
        boolean updatePassword;
        String oldPassword = "";   //acquisisce vecchia password dall'utente
        String strCurrentPassword = proxy.getPassword();
        char[] strNewPassword = textNewPassword.getPassword();
        char[] strConfirmPassword = textPasswordConf.getPassword();


        //  if (oldPassword.equals(strCurrentPassword)) {


        // }
        if(strNewPassword.length == strConfirmPassword.length) {
            for (int i = 0; i < strNewPassword.length; i++) {
                newPassword += strNewPassword[i];
                confirmPassword += strConfirmPassword[i];
            }
        }
            /*else {
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect password!", "Password error", JOptionPane.ERROR_MESSAGE);
            //updatePassword = false;

        }*/

        if (newPassword.equals(confirmPassword)) {
            proxy.updatePassword(newPassword);
            JOptionPane.showMessageDialog(new JFrame(), "you have changed password correctly!", "", JOptionPane.INFORMATION_MESSAGE);
            updatePassword = true;


        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect password!", "Password error", JOptionPane.ERROR_MESSAGE);
            updatePassword = false;
            textNewPassword.setText("");
            textPasswordConf.setText("");
        }




        return updatePassword;

    }

}


