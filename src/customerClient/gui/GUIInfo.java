package customerClient.gui;


import javax.swing.*;
import java.awt.*;

public class GUIInfo extends JFrame {
    final int WIDTH = 800;
    final int HEIGHT = 1000;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panel;
    ImageIcon splashScreenIcon;
    private JLabel labelIcon;
    private JLabel version;
    private JLabel date;


    public GUIInfo(){
        initComponent();
    }

    private void initComponent(){
        setTitle("Info");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true); //da mettere 'false'

        panel = new JPanel();

        splashScreenIcon = new ImageIcon("images/bozza_splashscreen.jpg");
        Image imageTransformIcon = splashScreenIcon.getImage();
        Image newImageIcon = imageTransformIcon.getScaledInstance(800, 210,  java.awt.Image.SCALE_SMOOTH);
        splashScreenIcon = new ImageIcon(newImageIcon);
        labelIcon = new JLabel(splashScreenIcon);

        version = new JLabel("Version: 0.1.0 ", SwingConstants.CENTER);
        date = new JLabel("Publication date: 12/06/2018", SwingConstants.CENTER);

        panel.setLayout(new GridLayout(3,1,10,10));
        panel.add(labelIcon); panel.add(version); panel.add(date);
        add(panel);

    }

}

