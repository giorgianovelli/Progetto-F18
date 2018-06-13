package customerClient.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class GUIAwards extends JFrame {
    final int WIDTH = 1000;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panel;
    //Icon iconRiccardo = new ImageIcon("");
    //private JLabel riccardo;
    //Icon iconNicolas = new ImageIcon("");
    ImageIcon iconNicolas;
    private JLabel labelN;
    private JLabel nicolas;
    //ImageIcon iconGiorgia;
    //private BufferedImage iconGiorgia;
    ImageIcon iconGiorgia;
    private JLabel labelG;
    private JLabel giorgia;
    //Icon iconCamilla = new ImageIcon("");
    ImageIcon iconCamilla;
    private JLabel labelCami;
    private JLabel camilla;
    //Icon iconCamelia = new ImageIcon("");
    //private JLabel camelia;
    //Icon iconSam = new ImageIcon("");
    //private JLabel sam;


    public GUIAwards(){
        initComponent();
    }

    private void initComponent(){
        setTitle("Awards");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true); //da mettere 'false'

        panel = new JPanel();

        //Icon iconRiccardo = new ImageIcon("");
        //riccardo = new JLabel("Riccardo Giura - riky123456 - (Verifica e convalida, Database Administrator)");
        //Icon iconNicolas = new ImageIcon("");
        iconNicolas = new ImageIcon("images/nicolas.jpeg");
        Image imageTransformN = iconNicolas.getImage();
        Image newImageN = imageTransformN.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconNicolas = new ImageIcon(newImageN);
        labelN = new JLabel(iconNicolas);
        nicolas = new JLabel("Nicolas Carolo (Capo progetto, Qualità del codice)");
        //iconGiorgia = ImageIO.read(getClass().getResource("IMG_-v7xplt.jpg"));
        iconGiorgia = new ImageIcon("images/giorgia.jpg");
        Image imageTransformG = iconGiorgia.getImage();
        Image newImageG = imageTransformG.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        iconGiorgia = new ImageIcon(newImageG);
        labelG = new JLabel(iconGiorgia);
        //Image imageTransform = iconGiorgia.getImage();
        //Image newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        //iconGiorgia = new ImageIcon(newImage);
        giorgia = new JLabel("Giorgia Novelli (Progettazione dell'interfaccia)");
        //Icon iconGiorgia = new ImageIcon("IMG_-v7xplt.jpg");
        //Icon iconCamilla = new ImageIcon("");
        iconCamilla = new ImageIcon("images/camilla.jpeg");
        Image imageTransformCami = iconCamilla.getImage();
        Image newImageCami = imageTransformCami.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        iconCamilla = new ImageIcon(newImageCami);
        labelCami = new JLabel(iconCamilla);
        camilla = new JLabel("Camilla Modica (Documentazione del progetto)");
        //Icon iconCamelia = new ImageIcon("");
        //camelia = new JLabel("Camelia Maidan - cammi001 - (Documentazione del codice)");
        //Icon iconSam = new ImageIcon("");
        //sam = new JLabel("Btissame Akrach - btissame-akrach - (Documentazione dei requisiti)");

        panel.setLayout(new GridLayout(3,2,10,10));
        //panel.add(riccardo); panel.add(nicolas); panel.add(giorgia); panel.add(camilla); panel.add(camelia); panel.add(sam);
        panel.add(labelN); panel.add(nicolas); panel.add(labelG); panel.add(giorgia); panel.add(labelCami); panel.add(camilla);

        add(panel);

    }

}
