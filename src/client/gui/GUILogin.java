package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;


public class GUILogin extends JFrame {

    private JPanel panelLoginData = new JPanel();
    private JPanel panelBottom = new JPanel();
    private JLabel labelUser = new JLabel("Email", SwingConstants.LEFT);
    private JLabel labelPwd = new JLabel("Password", SwingConstants.LEFT);
    private JTextField textUser = new JTextField();
    private JPasswordField textPwd = new JPasswordField();
    private JButton buttonLogin = new JButton("Login as Costumer");
    private JButton buttonLoginSitter = new JButton("Login as Dogsitter");
    private JButton buttonNewAccount = new JButton("Create a new account");
    private JPanel cont1 = new JPanel();   //pannello contenitore
    private GridBagLayout layout = new GridBagLayout();
    private GridBagConstraints lim = new GridBagConstraints();

    private CustomerProxy proxy = new CustomerProxy();
    private DogSitterProxy dogSitterProxy = new DogSitterProxy();

    public GUILogin() {

        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
    }

    private void initComponents(){

        cont1.setLayout(new GridLayout(2, 1, 10, 0)); // mi sposta il pannello dei dati tranne i bottoni
        cont1.setBorder(BorderFactory.createTitledBorder("Login"));

        //login automatico per velocizzare il debug
        textUser.setText("MARCO.CARTA@GMAIL.COM");
        textPwd.setText("PROGETTO123");



        panelLoginData.setLayout(new GridLayout(2,2));
        panelLoginData.add(labelUser);
        panelLoginData.add(textUser);
        panelLoginData.add(labelPwd);
        panelLoginData.add(textPwd);
        add(panelLoginData, BorderLayout.CENTER);

        panelBottom.setLayout(new GridLayout(3,1));
        panelBottom.add(buttonLogin);
        panelBottom.add(buttonLoginSitter);
        panelBottom.add(buttonNewAccount);
        add(panelBottom, BorderLayout.SOUTH);

        cont1.add(panelLoginData);
        cont1.add(panelBottom );

        panelLoginData.setBackground(new Color(176, 144, 97));
        panelBottom.setBackground(new Color(176, 144, 97));
        textPwd.setBackground(new Color(241, 230, 218));
        textUser.setBackground(new Color(241, 230, 218));



        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //centra il frame nello schermo
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);


        Image img = getToolkit().getImage("logo.jpg");
        setIconImage(img);

        ImagePanel panel = new ImagePanel();
        panel.setBackground(SystemColor.window);
        Container contentPane = getContentPane();
        contentPane.add(panel);
        panel.setLayout(layout);


        // le dimensioni del pannello in larghezza e altezza

        cont1.setPreferredSize(new Dimension(400, 165)); //H-150
        cont1.setMinimumSize(new Dimension(400, 165)); //W-420

       /* labelUser.setOpaque(isOpaque());
        labelPwd.setOpaque(isOpaque());*/

        panel.add(cont1);


        cont1.setOpaque(false);


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

                if (ae.getActionCommand().equals("Login as Costumer") && !(textUser.equals("RICCARDOGIURA@GMAIL.COM")) && !(textPwd.equals("PROVAPROVA123"))){
                    //String clientMsg = textUser.getText() + new String(textPwd.getPassword());
                    //proxy.getReply(clientMsg);
                    if (proxy.customerAccessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                        GUICustomer guiCustomer = null;
                        try {
                            //guiCustomer = new GUICustomer(textUser.getText());
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

                if (ae.getActionCommand().equals("Login as Dogsitter") && !(textUser.equals("MARCO.CARTA@GMAIL.COM")) && !(textPwd.equals("PROGETTO123"))) {

                    if (dogSitterProxy.dogSitterAccessDataVerifier(textUser.getText(), new String(textPwd.getPassword()))){
                        GUIDogSitter guiDogSitter = null;

                        try {
                            guiDogSitter = new GUIDogSitter(textUser.getText());

                        } catch (ParseException e) {

                            System.out.println("Error in parsing data");
                        }

                        guiDogSitter.setVisible(true);

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
        buttonLoginSitter.addActionListener(al);

    }


    private class ImagePanel extends JPanel {

            private Image image;


            public ImagePanel() {

                //acquisisco l'immagine
                image = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(image, 0);

                try {
                    tracker.waitForID(0);
                } catch (InterruptedException exception) {

                }

            }


            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                //acquisisco le dimensioni dello schermo
                Toolkit kit = Toolkit.getDefaultToolkit();
                Dimension screenSize = kit.getScreenSize();
                int screenHeight = screenSize.height / 2;
                int screenWidth = screenSize.width / 2;

                //disegna l'immagine
                int centroAscissaImage = screenWidth - image.getWidth(null) / 2;
                int centroOrdinataImage = screenHeight - image.getHeight(null) / 2;
                g.drawImage(image, centroAscissaImage, centroOrdinataImage, null);



            }
        }
    }
