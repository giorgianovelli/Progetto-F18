package client.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *  This class shows general information about the project.
 */
public class GUIInfo extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 500;

    /**
     * Frame height.
     */
    final int HEIGHT = 400;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * The panel containing the project icon.
     */
    private JPanel panelIcon;

    /**
     * The panel containing other panels.
     */
    private JPanel panelLabel;

    /**
     * The type parameter Image icon that acquires the icon.
     */
    ImageIcon splashScreenIcon;

    /**
     * The label containing the project icon.
     */
    private JLabel labelIcon;

    /**
     * The label containing version of the project.
     */
    private JLabel version;

    /**
     * The label containing the publication  data of the project.
     */
    private JLabel date;


    /**
     * Constuctor of the class
     */
    public GUIInfo(GUIHome guiCustomer) {
        guiCustomer.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiCustomer.setEnabled(true);
            }
        });
        initComponent();
    }


    /**
     * initialize the interface components
     */
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

        version = new JLabel("Version: 1.0.0 Presentation preview", SwingConstants.CENTER);
        date = new JLabel("Publication date: 26/07/2018", SwingConstants.CENTER);
        version.setFont(new Font("Calibri", Font.PLAIN, 24));
        date.setFont(new Font("Calibri", Font.PLAIN, 24));

        panelIcon.add(labelIcon,BorderLayout.NORTH);
        panelLabel.setLayout(new GridLayout(2, 1, 10, -115));
        panelLabel.add(version); panelLabel.add(date);
        panelIcon.add(panelLabel,BorderLayout.CENTER);
        add(panelIcon);

    }

}

