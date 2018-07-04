package test;

import client.gui.GUICustomer;
import client.gui.GUISignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;


class TestGUILogin {

    public static void main(String[] args) {

        ImageTest frame = new ImageTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();

    }
}

    class ImageTest extends JFrame {

    private JPanel panelLoginData = new JPanel();
    private JPanel panelBottom = new JPanel();
    private JPanel cont1 = new JPanel();   //pannello contenitore
    private JPanel cont2 = new JPanel();   //pannello contenitore
    private JLabel labelUser = new JLabel("Email", SwingConstants.CENTER);
    private JLabel labelPwd = new JLabel("Password", SwingConstants.CENTER);
    private JTextField textUser = new JTextField();
    private JPasswordField textPwd = new JPasswordField();
    private JButton buttonLogin = new JButton(" Login as Costumer");
    private JButton buttonLoginSitter = new JButton(" Login as Dogsitter");
    private JButton buttonNewAccount = new JButton(" Create a new account ");
    private JLabel status = new JLabel(" ");
    private customerClient.CustomerProxy proxy = new customerClient.CustomerProxy();



    public ImageTest() {


        cont2.setLayout(new GridLayout(1, 1, 20, 20)); // mi sposta il pannello dei dati tranne i bottoni
        cont1.setBorder(BorderFactory.createTitledBorder(" Main Fields: "));
        //cont1.setBorder(BorderFactory.createEmptyBorder(20,100,5,50));
        //cont1.setLayout(new BorderLayout(0 ,0 ));
        //cont2.setLayout(new GridLayout(2, 2, 0, 0));


        panelBottom.setLayout(new GridLayout(2, 1));
        panelBottom.add(buttonLogin, BorderLayout.SOUTH);
        //panelBottom.add(buttonLoginSitter, BorderLayout.SOUTH);
        panelBottom.add(buttonNewAccount, BorderLayout.SOUTH);


        textUser.setText(" RICCARDOGIURA@GMAIL.COM ");
        textPwd.setText(" PROVAPROVA123 ");


        panelLoginData.setLayout(new GridLayout(2, 2));
        panelLoginData.add(labelUser);
        panelLoginData.add(textUser);
        panelLoginData.add(labelPwd);
        panelLoginData.add(textPwd);

        setLayout(new BorderLayout());
        cont1.add(panelLoginData, BorderLayout.CENTER);
        cont2.add(panelBottom, BorderLayout.SOUTH);

        /*JPanel p5 = new JPanel(new BorderLayout());
        p5.add(cont2, BorderLayout.CENTER);
        p5.add(status, BorderLayout.NORTH);
        status.setForeground(Color.RED);
        status.setHorizontalAlignment(SwingConstants.CENTER);
*/

        //acquisisce le dimensioni dello schermo
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //centra il frame nello schermo
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);


        Image img = getToolkit().getImage("new-logo.jpg");
        setIconImage(img);
        setTitle("Login ");


        //aggiungo un pannello al frame
        ImagePanel panel = new ImagePanel();
        panel.setBackground(SystemColor.window);
        Container contentPane = getContentPane();
        contentPane.add(panel);
        panel.add(cont1);
        panel.add(cont2);

        //panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));


        cont1.setOpaque(false);
        cont2.setOpaque(false);

        cont1.setSize(100, 100);
        cont2.setSize(100, 100);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


    }


    class ImagePanel extends JPanel {

        private Image image;


        public ImagePanel() {

            //acquisisco l'immagine
            image = Toolkit.getDefaultToolkit().getImage("images/new-logo.jpg");
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










