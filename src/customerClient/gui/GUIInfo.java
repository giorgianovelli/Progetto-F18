package customerClient.gui;


import javax.swing.*;
import java.awt.*;

public class GUIInfo extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panel;
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

        version = new JLabel("Version: 0.1.0 ");
        date = new JLabel("Date of publication: 12/06/2018");

        panel.setLayout(new GridLayout(2,1,10,10));
        panel.add(version);
        panel.add(date);

        add(panel);

    }

}

