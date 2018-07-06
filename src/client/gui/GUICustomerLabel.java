package client.gui;

import javax.swing.*;
import java.awt.*;

public class GUICustomerLabel extends JFrame {
    final int WIDTH = 500;
    final int HEIGHT = 500;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();

    private JLabel labelIDDogs = new JLabel("ID of the dog:", SwingConstants.LEFT);
    private JLabel labelDogsName = new JLabel("Name of the dog:", SwingConstants.LEFT);
    private JLabel labelDogBreed = new JLabel("Dog breed:", SwingConstants.LEFT);
    private JLabel labelDogsWeight = new JLabel("Dog's weight:", SwingConstants.LEFT);
    private JLabel labelDogsAge = new JLabel("Age of the dog:", SwingConstants.LEFT);

    private JTextField textIDDogs = new JTextField();
    private JTextField textDogsName = new JTextField();
    private JTextField textDogBreed = new JTextField();
    private JTextField textDogsWeight = new JTextField();
    private JTextField textDogsAge = new JTextField();


    private JButton buttonContinue = new JButton("Next >>");
    private JButton buttonBack = new JButton("<< Back");

//______________________________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
     */

    public GUICustomerLabel() {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents();
    }
//______________________________________________________________________________________________________________________________________________________________________________


    private void initComponents()  {

        /**
         * Panels da sistemare
         */

        panelData.setLayout(new GridLayout(5, 1, 70, 30));
        panelData.setBorder(BorderFactory.createTitledBorder("SECOND STEP_Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonBack, BorderLayout.SOUTH);
        panelButton.add(buttonContinue, BorderLayout.SOUTH);


        panelData.add(labelIDDogs);
        panelData.add(textIDDogs);
        panelData.add(labelDogsName);
        panelData.add(textDogsName);
        panelData.add(labelDogBreed);
        panelData.add(textDogBreed);
        panelData.add(labelDogsWeight);
        panelData.add(textDogsWeight);
        panelData.add(labelDogsAge);
        panelData.add(textDogsAge);


        add(panelOut);

        /*textIDDogs.setText("");
        textIDDogs.setEditable(true);
        labelIDDogs.setLabelFor(textIDDogs);*/


    }








}
