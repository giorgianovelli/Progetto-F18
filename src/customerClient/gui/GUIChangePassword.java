package customerClient.gui;

import customerClient.CustomerProxy;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIChangePassword extends JFrame {
    final int WIDTH = 400;
    final int HEIGHT = 400;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private User user;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JButton panelButton = new JButton();

    private JLabel labelCurrentPassword = new JLabel("Current Password:", SwingConstants.LEFT);
    private JLabel labelPasswordConf = new JLabel("Confirm New Password:", SwingConstants.LEFT);
    private JLabel labelNewPassword = new JLabel("New Password:", SwingConstants.LEFT);

    //todo cambiato in jtextfield per vedere il contenuto errore :null
    private JPasswordField textCurrentPassword = new JPasswordField(SwingConstants.RIGHT);
    private JPasswordField textPasswordConf = new JPasswordField(SwingConstants.RIGHT);
    private  JPasswordField textNewPassword = new JPasswordField(SwingConstants.RIGHT);

    private  String newPassword = new String();
    private  String confirmPassword = new String();


    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    //TODO attributi per client-server
    private CustomerProxy proxy;
    private String email;

    public GUIChangePassword(String email) {
        setTitle("Change Password");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        // this.user = user;
        this.email = email;
        this.proxy = new CustomerProxy(email);

        initComponents();

    }

    private void initComponents() {

        panelData.setLayout(new GridLayout(3,1,20, 10));
        panelData.add(labelCurrentPassword);
        panelData.add(textCurrentPassword);
        panelData.add(labelNewPassword);
        panelData.add(textNewPassword);
        panelData.add(labelPasswordConf);
        panelData.add(textPasswordConf);
        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        add(panelOut);
        panelButton.setLayout(new GridLayout(1, 2));
        panelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);

        String strCurrentPassword = proxy.getPassword();
        textCurrentPassword.setText(strCurrentPassword);
        textCurrentPassword.setEditable(true);
        labelCurrentPassword.setLabelFor(textCurrentPassword);

        textPasswordConf.setText("");
        textPasswordConf.setEditable(true);
        labelPasswordConf.setLabelFor(textPasswordConf);

        //METODO CONFERMA MODIFICA PASSWORD da SITEMARE
        ActionListener changepassword = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent passwordAe) {

                if (passwordAe.getActionCommand().equals("Confirm")) {
                    wrongPassword(newPassword, confirmPassword);
                    //da sistemare
                }
                if (passwordAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }


            }
        };
        buttonCancel.addActionListener(changepassword);
        buttonConfirm.addActionListener(changepassword);




    }

    //metodo per controllare se le nuova password inserita nel campo "NewPassword" corrisponda  al campo "ConfirmPassword"
    public boolean wrongPassword(String inputNewPassword, String inputConfirmPassword){
        inputNewPassword = "New Password";
        inputConfirmPassword = "Confirm New Password";

        if (inputNewPassword.equals(inputConfirmPassword)){
            return true;
        }else  {
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect password!", "Password error", JOptionPane.ERROR_MESSAGE);
            textNewPassword.setText("");
            textPasswordConf.setText("");
            return  false;
        }
    }




}

