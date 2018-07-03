package client.gui;


import javax.swing.*;
import java.awt.*;

public class GUIInfo extends JFrame {
    final int WIDTH = 500;
    final int HEIGHT = 400;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panelIcon;
    private JPanel panelLabel;
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
        setResizable(false);

        panelIcon = new JPanel(new BorderLayout());
        panelLabel = new JPanel();

        splashScreenIcon = new ImageIcon("images/bozza_splashscreen.jpg");
        Image imageTransformIcon = splashScreenIcon.getImage();
        Image newImageIcon = imageTransformIcon.getScaledInstance(500, 210,  java.awt.Image.SCALE_SMOOTH);
        splashScreenIcon = new ImageIcon(newImageIcon);
        labelIcon = new JLabel(splashScreenIcon);

        version = new JLabel("Version: 0.1.0 ", SwingConstants.CENTER);
        date = new JLabel("Publication date: 12/06/2018", SwingConstants.CENTER);
        version.setFont(new Font("Calibri", Font.PLAIN, 24));
        date.setFont(new Font("Calibri", Font.PLAIN, 24));

        panelIcon.add(labelIcon,BorderLayout.NORTH);
        panelLabel.setLayout(new GridLayout(2, 1, 10, -115));
        panelLabel.add(version); panelLabel.add(date);
        panelIcon.add(panelLabel,BorderLayout.CENTER);
        add(panelIcon);

    }

}

