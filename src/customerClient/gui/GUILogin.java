package customerClient.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import customerClient.CustomerProxy;
import server.Login;

public class GUILogin extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 150;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JPanel panelLoginData = new JPanel();
    private JPanel panelBottom = new JPanel();
    private JLabel labelUser = new JLabel("Email", SwingConstants.LEFT);
    private JLabel labelPwd = new JLabel("Password", SwingConstants.LEFT);
    private JTextField textUser = new JTextField();
    private JPasswordField textPwd = new JPasswordField();
    private JButton buttonLogin = new JButton("Login");
    private JButton buttonNewAccount = new JButton("Create a new account");

    private CustomerProxy proxy = new CustomerProxy();

    public GUILogin() {
        setTitle("Login");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        //ImageIcon img = new ImageIcon("/Users/nicolas/Desktop/logo.png");
        //setIconImage(img.getImage());

        initComponents();
    }

    private void initComponents(){

        //login automatico per velocizzare il debug
        textUser.setText("RICCARDOGIURA@GMAIL.COM");
        textPwd.setText("PROVAPROVA123");

        panelLoginData.setLayout(new GridLayout(2,2));
        panelLoginData.add(labelUser);
        panelLoginData.add(textUser);
        panelLoginData.add(labelPwd);
        panelLoginData.add(textPwd);
        add(panelLoginData, BorderLayout.CENTER);
        panelBottom.setLayout(new GridLayout(2,1));
        panelBottom.add(buttonLogin);
        panelBottom.add(buttonNewAccount);
        add(panelBottom, BorderLayout.SOUTH);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                /*if (ae.getActionCommand().equals("Login") && !(textUser.equals("")) && !(textPwd.equals(""))){
                    Login login = new Login();
                    try {
                        if(login.customerAccessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                            //open GUICustomer
                            GUICustomer guiCustomer = null;
                            try {
                                guiCustomer = new GUICustomer(textUser.getText());
                            } catch (ParseException e) {
                                //e.printStackTrace();
                                System.out.println("Error in parsing data");
                            }
                            guiCustomer.setVisible(true);
                            setVisible(false);
                        } else{
                            //show error message
                            JOptionPane.showMessageDialog(new JFrame(), "Incorrect user or password!", "Login error",
                                    JOptionPane.ERROR_MESSAGE);
                            textUser.setText("");
                            textPwd.setText("");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }*/

                if (ae.getActionCommand().equals("Login") && !(textUser.equals("")) && !(textPwd.equals(""))){
                    //String clientMsg = textUser.getText() + new String(textPwd.getPassword());
                    //proxy.getReply(clientMsg);
                    if (proxy.customerAccessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                        GUICustomer guiCustomer = null;
                        try {
                            guiCustomer = new GUICustomer(textUser.getText());
                        } catch (ParseException e) {
                            //e.printStackTrace();
                            System.out.println("Error in parsing data");
                        }
                        guiCustomer.setVisible(true);
                        setVisible(false);
                    } else {
                        //show error message
                        JOptionPane.showMessageDialog(new JFrame(), "Incorrect user or password!", "Login error",
                                JOptionPane.ERROR_MESSAGE);
                        textUser.setText("");
                        textPwd.setText("");
                    }
                }

                if (ae.getActionCommand().equals("Create a new account")){
                    GUISignUp guiSignUp = new GUISignUp();
                    guiSignUp.setVisible(true);
                    setVisible(false);
                }
            }
        };

        buttonLogin.addActionListener(al);
        buttonNewAccount.addActionListener(al);
    }

}
