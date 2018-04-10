import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class GUILogin extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 150;
    Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    JPanel panelLoginData = new JPanel();
    JPanel panelBottom = new JPanel();
    JLabel labelUser = new JLabel("Email", SwingConstants.LEFT);
    JLabel labelPwd = new JLabel("Password", SwingConstants.LEFT);
    JTextField textUser = new JTextField();
    JPasswordField textPwd = new JPasswordField();
    JButton buttonLogin = new JButton("Login");
    JButton buttonNewAccount = new JButton("Create a new account");

    public GUILogin() {
        setTitle("Login");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents(){
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
                if (ae.getActionCommand().equals("Login") && !(textUser.equals("")) && !(textPwd.equals(""))){
                    Login login = new Login();
                    if(login.accessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                        if (login.getTypeUser().equals(TypeUser.CUSTOMER)){
                            //open GUIDogSitter
                            GUICustomer guiCustomer = new GUICustomer();
                            guiCustomer.setVisible(true);
                            setVisible(false);
                        } else{
                            //open GUIDogSitter
                            GUIDogSitter guiDogSitter = new GUIDogSitter();
                            guiDogSitter.setVisible(true);
                            setVisible(false);
                        }

                    } else{
                        //show error message
                        JOptionPane.showMessageDialog(new JFrame(), "Incorrect user or password!", "Login error",
                                JOptionPane.ERROR_MESSAGE);
                        textUser.setText("");
                        textPwd.setText("");
                    }
                }

                if (ae.getActionCommand().equals("Create a new account")){
                    GUISignIn guiSignIn = new GUISignIn();
                    guiSignIn.setVisible(true);
                    setVisible(false);
                }
            }
        };
        buttonLogin.addActionListener(al);
        buttonNewAccount.addActionListener(al);
    }

}
