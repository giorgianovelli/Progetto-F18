package customerClient.gui;

        import javax.swing.*;
        import java.awt.*;


public class GUIAwards extends JFrame {
    final int WIDTH = 850;
    final int HEIGHT = 690;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panel;
    ImageIcon iconRiccardo;
    private JLabel labelR;
    private JLabel riccardo;
    ImageIcon iconNicolas;
    private JLabel labelN;
    private JLabel nicolas;
    ImageIcon iconGiorgia;
    private JLabel labelG;
    private JLabel giorgia;
    ImageIcon iconCamilla;
    private JLabel labelC;
    private JLabel camilla;
    ImageIcon iconCamelia;
    private JLabel labelCami;
    private JLabel camelia;
    ImageIcon iconSam;
    private JLabel labelS;
    private JLabel sam;


    public GUIAwards(){
        initComponent();
    }

    private void initComponent(){
        setTitle("Credits");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 110));

        iconRiccardo = new ImageIcon("images/riccardo.jpeg");
        Image imageTransformR = iconRiccardo.getImage();
        Image newImageR = imageTransformR.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconRiccardo = new ImageIcon(newImageR);
        labelR = new JLabel(iconRiccardo);
        riccardo = new JLabel("Riccardo Giura (Verification and validation, Database Administrator)");
        iconNicolas = new ImageIcon("images/nicolas.jpeg");
        Image imageTransformN = iconNicolas.getImage();
        Image newImageN = imageTransformN.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconNicolas = new ImageIcon(newImageN);
        labelN = new JLabel(iconNicolas);
        nicolas = new JLabel("Nicolas Carolo (Project leader, Code quality)");
        iconGiorgia = new ImageIcon("images/giorgia.jpg");
        Image imageTransformG = iconGiorgia.getImage();
        Image newImageG = imageTransformG.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconGiorgia = new ImageIcon(newImageG);
        labelG = new JLabel(iconGiorgia);
        giorgia = new JLabel("Giorgia Novelli (Interface design)");
        iconCamilla = new ImageIcon("images/camilla.jpeg");
        Image imageTransformC = iconCamilla.getImage();
        Image newImageC = imageTransformC.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconCamilla = new ImageIcon(newImageC);
        labelC = new JLabel(iconCamilla);
        camilla = new JLabel("Camilla Modica (Project documentation)");
        iconCamelia = new ImageIcon("images/camelia.jpg");
        Image imageTransformCami = iconCamelia.getImage();
        Image newImageCami = imageTransformCami.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconCamelia = new ImageIcon(newImageCami);
        labelCami = new JLabel(iconCamelia);
        camelia = new JLabel("Camelia Maidan (Code documentation)");
        iconSam = new ImageIcon("images/sam.jpg");
        Image imageTransformS = iconSam.getImage();
        Image newImageS = imageTransformS.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
        iconSam = new ImageIcon(newImageS);
        labelS = new JLabel(iconSam);
        sam = new JLabel("Btissame Akrach (Requirements documentation)");

        panel.setLayout(new GridLayout(6,2,-200,10));
        panel.add(labelR); panel.add(riccardo); panel.add(labelN); panel.add(nicolas); panel.add(labelG); panel.add(giorgia);
        panel.add(labelC); panel.add(camilla); panel.add(labelCami); panel.add(camelia); panel.add(labelS); panel.add(sam);

        add(panel);

    }

}