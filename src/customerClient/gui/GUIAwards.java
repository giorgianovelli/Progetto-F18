package customerClient.gui;

import javax.swing.*;
import java.awt.*;

public class GUIAwards extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panel;
    //Icon iconRiccardo = new ImageIcon("");
    private JLabel riccardo;
    //Icon iconNicolas = new ImageIcon("");
    private JLabel nicolas;
    private JLabel giorgia;
    //Icon iconGiorgia = new ImageIcon("IMG_-v7xplt.jpg");
    //Icon iconCamilla = new ImageIcon("");
    private JLabel camilla;
    //Icon iconCamelia = new ImageIcon("");
    private JLabel camelia;
    //Icon iconSam = new ImageIcon("");
    private JLabel sam;


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
        riccardo = new JLabel("Riccardo Giura - riky123456 - (Verifica e convalida, Database Administrator)");
        //Icon iconNicolas = new ImageIcon("");
        nicolas = new JLabel("Nicolas Carolo - nicolas-carolo - (Capo progetto, Qualità del codice)");
        giorgia = new JLabel("Giorgia Novelli - giorgianovelli - (Progettazione dell'interfaccia)");
        //Icon iconGiorgia = new ImageIcon("IMG_-v7xplt.jpg");
        //Icon iconCamilla = new ImageIcon("");
        camilla = new JLabel("Camilla Modica - therealzuc - (Documentazione del progetto)");
        //Icon iconCamelia = new ImageIcon("");
        camelia = new JLabel("Camelia Maidan - cammi001 - (Documentazione del codice)");
        //Icon iconSam = new ImageIcon("");
        sam = new JLabel("Btissame Akrach - btissame-akrach - (Documentazione dei requisiti)");

        panel.setLayout(new GridLayout(6,1,10,10));
        panel.add(riccardo); panel.add(nicolas); panel.add(giorgia); panel.add(camilla); panel.add(camelia); panel.add(sam);

        add(panel);

    }

}
