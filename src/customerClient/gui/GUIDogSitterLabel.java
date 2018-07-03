package customerClient.gui;

import javax.swing.*;
import java.awt.*;

public class GUIDogSitterLabel extends JFrame {
    final int WIDTH = 500;
    final int HEIGHT = 500;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelArea = new JPanel();

    private JLabel labelArea = new JLabel("Area:", SwingConstants.LEFT);
    private JLabel labelDogBreed = new JLabel("Dog Breed:", SwingConstants.LEFT);
    private JLabel labelDogsNumber = new JLabel("Number of dogs:", SwingConstants.LEFT);
    private JLabel labelAvailability = new JLabel("Availability:", SwingConstants.LEFT);
    private JLabel labelBiography = new JLabel("Biography:", SwingConstants.LEFT);

    private JTextField textArea = new JTextField();
    private JTextField textDogBreed = new JTextField();
    private JTextField textDogsNumber = new JTextField();//todo da cambiare in menu a tendina con cui scegliere numero cani??
    private JTextField textAvailability = new JTextField();
    private JTextField textBiography = new JTextField("immetti la tua biografia"); //todo da cambiare con area di testo

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");


    public GUIDogSitterLabel() {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents();
    }


    private void initComponents()  {

        panelData.setLayout(new GridLayout(5, 1, 70, 30));
        panelData.setBorder(BorderFactory.createTitledBorder("Second Step:(DogSitter Fields) "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);

        panelData.add(labelArea);
        panelData.add(textArea);
        panelData.add(labelDogBreed);
        panelData.add(textDogBreed);
        panelData.add(labelDogsNumber);
        panelData.add(textDogsNumber);
        panelData.add(labelAvailability);
        panelData.add(textAvailability);
        panelData.add(labelBiography);
        panelData.add(textBiography);

      /*  panelArea.add(labelBiography);
        panelArea.add(textBiography);
        panelData.add(panelArea);*/
        add(panelOut);


        textArea.setText("");
        textArea.setEditable(true);
        labelArea.setLabelFor(textArea);

        textDogBreed.setText("");
        textDogBreed.setEditable(true);
        labelDogBreed.setLabelFor(textDogBreed);

        textDogsNumber.setText("");
        textDogsNumber.setEditable(true);
        labelDogsNumber.setLabelFor(textDogsNumber);

        textAvailability.setText("");
        textAvailability.setEditable(true);
        labelAvailability.setLabelFor(textAvailability);

        textBiography.setText("Immettere la propria biografia");
        textBiography.setEditable(true);
        labelBiography.setLabelFor(textBiography);





    }







}


