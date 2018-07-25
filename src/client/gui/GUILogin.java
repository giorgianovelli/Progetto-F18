package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;

/**
 * This class is the login window for the project.
 */
public class GUILogin extends JFrame {

    /**
     * Panel containing "labelUser","labelPwd","textUser" and "textPdw".
     */
    private JPanel panelLoginData = new JPanel();

    /**
     * Panel containing "buttonLogin","buttonLoginSitter","buttonNewAccount1" and "buttonNewAccount2".
     */
    private JPanel panelBottom = new JPanel();

    /**
     * Label containing email field.
     */
    private JLabel labelUser = new JLabel("Email", SwingConstants.LEFT);

    /**
     * Label containing password field.
     */
    private JLabel labelPwd = new JLabel("Password", SwingConstants.LEFT);

    /**
     * Text area containing user text.
     */
    private JTextField textUser = new JTextField();

    /**
     * Text area containing password text.
     */
    private JPasswordField textPwd = new JPasswordField();

    /**
     * Button with the inscription "Login as Costumer".
     */
    private JButton buttonLogin = new JButton("Login as Costumer");

    /**
     * Button with the inscription "Login as Dogsitter".
     */
    private JButton buttonLoginSitter = new JButton("Login as Dogsitter");

    /**
     * Button with the inscription "SignUp as Costumer".
     */
    private JButton buttonNewAccount1 = new JButton("SignUp as Costumer");

    /**
     * Button with the inscription "SignUp as Dogsitter".
     */
    private JButton buttonNewAccount2 = new JButton("SignUp as Dogsitter");

    /**
     * The external panel.
     */
    private JPanel cont1 = new JPanel();

    /**
     * Grid Bag Layout.
     */
    private GridBagLayout layout = new GridBagLayout();

    /**
     * The costumer proxy.
     */
    private CustomerProxy proxy = new CustomerProxy();

    /**
     * The dogsitter proxy.
     */
    private DogSitterProxy dogSitterProxy = new DogSitterProxy();

    /**
     * This GUI.
     */
    private GUILogin guiLogin;

    /**
     * The class constructor.
     */
    public GUILogin() {

        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        guiLogin = this;
        initComponents();
    }

    /**
     * Initialize the GUI components.
     */
    private void initComponents(){

        cont1.setLayout(new GridLayout(2, 2, 10, 0));
        //cont1.setBorder(BorderFactory.createTitledBorder("Login"));

        //login automatico per velocizzare il debug
        //textUser.setText("MARCO.CARTA@GMAIL.COM");
        textUser.setText("riccardogiura@gmail.com");
        textPwd.setText("PROGETTO123");



        panelLoginData.setLayout(new GridLayout(2,2));
        panelLoginData.add(labelUser);
        panelLoginData.add(textUser);
        panelLoginData.add(labelPwd);
        panelLoginData.add(textPwd);
        add(panelLoginData, BorderLayout.CENTER);

        panelBottom.setLayout(new GridLayout(2,2));
        panelBottom.add(buttonLogin);
        panelBottom.add(buttonLoginSitter);
        panelBottom.add(buttonNewAccount1);
        panelBottom.add(buttonNewAccount2);
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

        cont1.setPreferredSize(new Dimension(500, 130)); //H-150
        cont1.setMinimumSize(new Dimension(500, 130)); //W-420

       /* labelUser.setOpaque(isOpaque());
        labelPwd.setOpaque(isOpaque());*/

        panel.add(cont1);


        cont1.setOpaque(false);


        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Login as Costumer")){
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

                if (ae.getActionCommand().equals("Login as Dogsitter")) {

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

                if (ae.getActionCommand().equals("SignUp as Costumer")){
                    GUISignUp guiSignUp = new GUISignUp(guiLogin);
                    guiSignUp.setVisible(true);
                }

                else if (ae.getActionCommand().equals("SignUp as Dogsitter")){
                    GUIDogSitterSignUp guiDogSitterSignUp = new GUIDogSitterSignUp(guiLogin);
                    guiDogSitterSignUp.setVisible(true);
                }
            }
        };

        buttonLogin.addActionListener(al);
        buttonNewAccount1.addActionListener(al);
        buttonNewAccount2.addActionListener(al);
        buttonLoginSitter.addActionListener(al);

    }

    /**
     * This class allows to have the image in the background.
     */
    private class ImagePanel extends JPanel {

        /**
         * In image type attribute.
         */
        private Image image;

        /**
         * The constructor of the class ImagePanel.
         */
        public ImagePanel() {

            image = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(image, 0);

            try {
                tracker.waitForID(0);
            } catch (InterruptedException exception) {

            }

        }


        /**
         * Method that allows to capture the image and draw it on the screen.
         * @param g
         */
        public void paintComponent(Graphics g) {

            super.paintComponent(g);


            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height / 2;
            int screenWidth = screenSize.width / 2;


            int centroAscissaImage = screenWidth - image.getWidth(null) / 2;
            int centroOrdinataImage = screenHeight - image.getHeight(null) / 2;
            g.drawImage(image, centroAscissaImage, centroOrdinataImage, null);



        }
    }
}