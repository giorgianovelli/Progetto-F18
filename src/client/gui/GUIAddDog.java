package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * This class allows the Customer to add and choose
 * all the features of dogs.
 */
public class GUIAddDog extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 512;

    /**
     * Frame height.
     */
    final int HEIGHT = 350;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );


    /**
     * The user's email.
     */
    private String email;

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * Out panel, it contains all the other panels.
     */
    private JPanel panelOut = new JPanel();

    /**
     * The panel contains dog's information (name, date of birth, breed and weight).
     */
    private JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to add the new dog and to close the frame.
     */
    private JPanel panelButton = new JPanel();

    /**
     * The panel allows to enter the date of birth.
     */
    private JPanel panelDate = new JPanel();

    /**
     * The label for the dog's name.
     */
    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);

    /**
     * The label for the dog's date of birth.
     */
    private JLabel labelDate = new JLabel("Date of birth: ", SwingConstants.LEFT);

    /**
     * The label of the dog's breed.
     */
    private JLabel labelBreed = new JLabel("Breed:", SwingConstants.LEFT);

    /**
     * The label of the dog's weight.
     */
    private JLabel labelWeight = new JLabel("Weight:", SwingConstants.LEFT);

    /**
     * Text field for the dog's name.
     */
    private JTextField textName = new JTextField();

    /**
     * Text field for the dog's weight.
     */
    private JTextField textWeight = new JTextField();

    /**
     * The combo box shows the list of the possible dog breeds.
     */
    private JComboBox<String> breedList;

    /**
     * Array of the possible dog breeds.
     */
    private String[] breed;

    /**
     * The button allows to add a new dog.
     */
    private JButton buttonAdd = new JButton("Add");

    /**
     * The button allows to close the frame.
     */
    private JButton buttonCancel = new JButton("Cancel");


    /**
     * The combo box allows to select the day of birth.
     */
    private JComboBox<String> dayList;

    /**
     * The combo box allows to select the month of birth.
     */
    private JComboBox<String> monthList;

    /**
     * The combo box allows to select the year of birth.
     */
    private JComboBox<String> yearList;


    /**
     * Array for the day of birth.
     */
    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * Array for the month of birth.
     */
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    /**
     * Array for the year of birth.
     */
    private ArrayList<String> years_tmp = new ArrayList<>();

    /**
     * This GUI.
     */
    private GUIAddDog guiAddDog;

    /**
     * GUI from where GUIAddDog is invoked.
     */
    private GUIDogs guiDogs;


    /**
     * Constructor
     * @param email the user's email.
     * @param guiDogs GUI from where GUIAddDog is invoked.
     */
    public GUIAddDog(String email, GUIDogs guiDogs) {
        this.email = email;
        proxy = new CustomerProxy(email);
        guiAddDog = this;
        this.guiDogs = guiDogs;
        guiDogs.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiDogs.setEnabled(true);
            }
        });
        initComponent();
    }


    /**
     * Initialize the GUI components.
     */
    private void initComponent() {
        setTitle("Dog info");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);


        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Add")) {
                    if (textName.getText().equals("") || textWeight.getText().equals("") ) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    } else{
                        boolean add = addNewDog();

                        if(add){
                            JOptionPane.showMessageDialog(new JFrame(), "Dog added!", "", JOptionPane.INFORMATION_MESSAGE);
                            guiAddDog.dispatchEvent(new WindowEvent(guiAddDog, WindowEvent.WINDOW_CLOSING));
                            guiDogs.dispatchEvent(new WindowEvent(guiDogs, WindowEvent.WINDOW_CLOSING));
                        }
                    }

                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    guiAddDog.dispatchEvent(new WindowEvent(guiAddDog, WindowEvent.WINDOW_CLOSING));
                }

            }
        };


        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        HashSet<String> breedSet = proxy.getDogsBreedsList();
        breed = new String[breedSet.size()];

        int i = 0;
        for (String breedStr : breedSet){
            breed[i] = breedStr;
            i++;
        }

        breedList = new JComboBox<>(breed);

        panelDate.setLayout(new GridLayout(1,3,5,5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);

        panelData.setLayout(new GridLayout(4,2,10,10));
        panelData.setBorder(BorderFactory.createTitledBorder("Dog fields:"));


        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelBreed);
        panelData.add(breedList);
        panelData.add(labelWeight);
        panelData.add(textWeight);
        panelData.add(labelDate);
        panelData.add(panelDate);


        panelButton.setLayout(new GridLayout(1,2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(15, 90, 10, 90));
        buttonAdd.addActionListener(registration);
        buttonCancel.addActionListener(registration);
        panelButton.add(buttonAdd);
        panelButton.add(buttonCancel);
        panelOut.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));

        panelOut.setLayout(new BorderLayout());
        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);

        add(panelOut);

    }

    /**
     * Add the new dog in the database
     * @return returns the value "true" if the procedure was successful
     */
    private boolean addNewDog(){
        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return proxy.addDog(email,textName.getText().toUpperCase(), breedList.getSelectedItem().toString(), dateOfBirth, Double.parseDouble(textWeight.getText()));

    }











}
