package test;

//import javafx.scene.layout.BorderImage;   //TODO DA ERRORI
//import javafx.scene.layout.BorderPane;    //TODO DA ERRORI

import javax.swing.*;
import javax.swing.text.BoxView;
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
        private JPanel cont1 = new JPanel();   //pannello contenitore
        private JPanel cont2 = new JPanel();   //pannello contenitore
        private JLabel labelUser = new JLabel("Email",SwingConstants.CENTER);
        private JLabel labelPwd = new JLabel("Password",SwingConstants.CENTER);
        private JTextField textUser = new JTextField();
        private JPasswordField textPwd = new JPasswordField();
        private JButton buttonLogin = new JButton(" Login ");
        private JButton buttonNewAccount = new JButton(" Create a new account ");



        public ImageTest() {
            //cont2.setLayout(new GridLayout(2, 2, 0, 0));
            cont1.setBorder(BorderFactory.createTitledBorder(" Main Fields: "));
            cont2.setLayout(new BorderLayout(getWidth() / 2 ,getHeight() / 2 ));


            panelBottom.setLayout(new GridLayout(2, 2));
            panelBottom.add(buttonLogin,BorderLayout.SOUTH);
            panelBottom.add(buttonNewAccount,BorderLayout.SOUTH);


            textUser.setText(" RICCARDOGIURA@GMAIL.COM ");
            textPwd.setText(" PROVAPROVA123 ");


            panelLoginData.setLayout(new GridLayout(2, 2));
            panelLoginData.add(labelUser);
            panelLoginData.add(textUser);
            panelLoginData.add(labelPwd);
            panelLoginData.add(textPwd);


            cont1.add(panelLoginData,BorderLayout.CENTER);
            cont2.add(panelBottom,BorderLayout.SOUTH);




            //acquisisce le dimensioni dello schermo
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();

            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;

            //centra il frame nello schermo
            setSize(screenWidth / 2, screenHeight / 2);
            setLocation(screenWidth / 4, screenHeight / 4);


            Image img = getToolkit().getImage("bozza_splashscreeen.jpg");
            setIconImage(img);
            setTitle("Login ");


            //aggiungo un pannello al frame
            ImagePanel panel = new ImagePanel();
            panel.setBackground(SystemColor.window);
            Container contentPane = getContentPane();
            contentPane.add(panel);
            panel.add(cont1);
            panel.add(cont2);
           // panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));



            cont1.setOpaque(false);
            cont2.setOpaque(false);

            cont1.setSize(100,100);
            cont2.setSize(100,100);
            setExtendedState( JFrame.MAXIMIZED_BOTH);


        }


        class ImagePanel extends JPanel {

            private Image image;


            public ImagePanel() {

                //acquisisco l'immagine
                image = Toolkit.getDefaultToolkit().getImage("images/bozza_splashscreen.jpg");
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










