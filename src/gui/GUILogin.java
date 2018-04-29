package gui;

import gui.GUICustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import enumeration.WeekDays;
import engine.Login;
import enumeration.TypeUser;

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
    private JPanel panelButtonLogin = new JPanel();
    private JButton buttonLoginAsCustomer = new JButton("Login as customer");
    private JButton buttonLoginAsDogSitter = new JButton("Login as dog sitter");
    private JButton buttonNewAccount = new JButton("Create a new account");

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
        panelButtonLogin.setLayout(new GridLayout(1,2,5,5));
        panelButtonLogin.add(buttonLoginAsCustomer);
        panelButtonLogin.add(buttonLoginAsDogSitter);
        panelBottom.add(panelButtonLogin);
        panelBottom.add(buttonNewAccount);
        add(panelBottom, BorderLayout.SOUTH);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Login as customer") && !(textUser.equals("")) && !(textPwd.equals(""))){
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
                }

                if (ae.getActionCommand().equals("Login as dog sitter") && !(textUser.equals("")) && !(textPwd.equals(""))){
                    Login login = new Login();
                    try {
                        if(login.dogSitterAccessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                            //open GUIDogSitter
                            GUIDogSitter guiDogSitter = new GUIDogSitter();
                            guiDogSitter.setVisible(true);
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
                }

                if (ae.getActionCommand().equals("Create a new account")){
                    GUISignUp guiSignUp = new GUISignUp();
                    guiSignUp.setVisible(true);
                    setVisible(false);
                }
            }
        };
        buttonLoginAsCustomer.addActionListener(al);
        buttonLoginAsDogSitter.addActionListener(al);
        buttonNewAccount.addActionListener(al);
    }

}
