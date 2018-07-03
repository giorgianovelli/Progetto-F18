package client.gui;

import javax.swing.*;
import java.awt.*;

public class GUIDogsitterInfo extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelOut = new JPanel();
    private JPanel panelAllInfo = new JPanel();
    private JPanel panelName = new JPanel();
    private JPanel panelSurname = new JPanel();
    private JPanel panelBirth = new JPanel();
    private JPanel panelAverage = new JPanel();
    private JPanel panelBio = new JPanel();

    private JScrollPane scrollPane = new JScrollPane(panelOut);


    private JLabel labelName = new JLabel("Name: ");
    private JLabel labelSurname = new JLabel("Surname: ");
    private JLabel labelBirth = new JLabel("Birth date: ");
    private JLabel labelAverage = new JLabel("Average reviews: ");
    private JLabel labelBio = new JLabel("Biography: ");






    public GUIDogsitterInfo(String mailDogsitter) {
        setTitle("Dogsitter Informations");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());




        initComponents();
    }


    public void initComponents() {


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);



    }






}
