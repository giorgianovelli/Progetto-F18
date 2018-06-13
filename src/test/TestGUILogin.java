package test;

import javax.swing.*;
import java.awt.*;


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
        private JLabel labelUser = new JLabel("Email", SwingConstants.CENTER);
        private JLabel labelPwd = new JLabel("Password", SwingConstants.CENTER);
        private JTextField textUser = new JTextField();
        private JPasswordField textPwd = new JPasswordField();
        private JButton buttonLogin = new JButton("Login");
        private JButton buttonNewAccount = new JButton("Create a new account");



        public ImageTest() {


            panelBottom.setLayout(new GridLayout(2, 1));
            panelBottom.add(buttonLogin);
            panelBottom.add(buttonNewAccount);

            textUser.setText("RICCARDOGIURA@GMAIL.COM");
            textPwd.setText("PROVAPROVA123");

            panelLoginData.setLayout(new GridLayout(2, 2));
            panelLoginData.add(labelUser);
            panelLoginData.add(textUser);
            panelLoginData.add(labelPwd);
            panelLoginData.add(textPwd);


            //acquisisce le dimensioni dello schermo
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;

            //centra il frame nello schermo
            setSize(screenWidth / 2, screenHeight / 2);
            setLocation(screenWidth / 4, screenHeight / 4);


            Image img = getToolkit().getImage("bozza.jpg");
            setIconImage(img);
            setTitle("Login Costumer");

            //aggiungo un pannello al frame
            ImagePanel panel = new ImagePanel();
            panel.setBackground(SystemColor.window);
            Container contentPane = getContentPane();
            contentPane.add(panel);
            panel.add(panelLoginData);
            panel.add(panelBottom);
            panel.setOpaque(true);
            //panel.setSize(80,20);
            //panel.setBounds(20, 10, 80, 25);
            setExtendedState( JFrame.MAXIMIZED_BOTH);


        }

        class ImagePanel extends JPanel {
            private Image image;

            public ImagePanel() {

                //acquisisco l'immagine
                image = Toolkit.getDefaultToolkit().getImage("C:/Users/Camelia/IdeaProjects/Progetto-F18Nuovo/images/bozza.jpg");
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(image, 0);
                try {
                    tracker.waitForID(0);
                }
                catch (InterruptedException exception) {
                }
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                //acquisisco le dimensioni dello schermo
                Toolkit kit = Toolkit.getDefaultToolkit();
                Dimension screenSize = kit.getScreenSize();
                int screenHeight = screenSize.height /2 ;
                int screenWidth = screenSize.width /2 ;

                //disegna l'immagine
                int centroAscissaImage = screenWidth - image.getWidth(null) / 2;
                int centroOrdinataImage = screenHeight - image.getHeight(null) / 2;
                g.drawImage(image, centroAscissaImage, centroOrdinataImage, null);

            }
        }
    }










