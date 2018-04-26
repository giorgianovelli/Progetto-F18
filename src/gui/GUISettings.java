package gui;

import javax.swing.*;
import java.awt.*;

public class GUISettings extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelAvailability = new JPanel();
    private JPanel panelButton = new JPanel();

    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelSurname = new JLabel("Surname:");
    private JLabel labelDate = new JLabel("Date of birth:");
    private JLabel labelPhoneNumber = new JLabel("Phone number:");
    private JLabel labelAddress = new JLabel("engine.Address:");
    private JLabel labelPassword = new JLabel("Password:");
    private JLabel labelPasswordConf = new JLabel("Confirm password:");
    private JLabel labelEmail = new JLabel();
    private JLabel labelPaymentMethod = new JLabel();
    //Label solo per dogSitter
    private JLabel labelArea = new JLabel();
    private JLabel labelDogsBreed = new JLabel("");
    private JLabel labelDogsNumber = new JLabel("Number of dogs:");
    private JLabel labelAvailability = new JLabel("engine.Availability:");
    private JLabel labelBiography = new JLabel("Biography:");

    private JTextField textName = new JTextField();


    public GUISettings() {
        setTitle("Account settings");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());



        initComponents();
    }

    private void initComponents(){
        panelData.setLayout(new GridLayout(1,2,10,10));
        panelOut.add(panelData);
        panelOut.add(panelAvailability);

        panelData.add(labelName);
        panelData.add(textName);
        add(panelOut);
    }
}
