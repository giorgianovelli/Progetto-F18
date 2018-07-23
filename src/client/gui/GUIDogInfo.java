package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Dog;

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
 * This class show to the Customer all the
 * information about a dog .
 */
public class GUIDogInfo extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 600;

    /**
     * Frame height.
     */
    final int HEIGHT = 300;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * Reference to the object Dog, which contains the information to show.
     */
    private Dog dog;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * Out panel.
     */
    private JPanel panelOut = new JPanel();

    /**
     * The panel contains dog's information (name, date of birth, breed and weight).
     */
    private JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to save the dog's information and to close the frame.
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
    private JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);

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
     * The button allows to save the changes for the dog's information.
     */
    private JButton buttonConfirm = new JButton("Confirm");

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
    private GUIDogInfo guiDogInfo;

    /**
     * GUI from which this class is created.
     */
    private GUIDogs guiDogs;


    /**
     * Constructor
     * @param dog Reference to the object Dog, which contains the information to show.
     * @param email The user's email.
     * @param guiDogs This GUI.
     */
    public GUIDogInfo(Dog dog, String email, GUIDogs guiDogs){
        this.dog = dog;
        this.email = email;
        proxy = new CustomerProxy(this.email);
        this.guiDogs = guiDogs;
        guiDogs.setEnabled(false);
        guiDogInfo = this;

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

        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        Date strDate = proxy.getDogDateOfBirth(dog.getID());
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    if (textName.getText().equals("") ||  textWeight.getText().equals("") ) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    }
                    else
                    {
                        if(setNewValues()){
                            JOptionPane.showMessageDialog(new JFrame(), "The data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                            guiDogInfo.dispatchEvent(new WindowEvent(guiDogInfo, WindowEvent.WINDOW_CLOSING));
                            guiDogs.dispatchEvent(new WindowEvent(guiDogs, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                }
            }
        };

        HashSet<String> breedSet = proxy.getDogsBreedsList();
        breed = new String[breedSet.size()];

        int i = 0;
        for (String breedStr : breedSet){
            breed[i] = breedStr;
            i++;
        }

        breedList = new JComboBox<>(breed);
        breedList.setSelectedItem(dog.getBreed());


        panelData.setLayout(new GridLayout(4,2, 10,10));
        panelData.setBorder(BorderFactory.createTitledBorder("Dog fields:"));

        panelData.add(labelName);
        textName.setText(dog.getName());
        panelData.add(textName);

        panelData.add(labelDate);
        panelDate.setLayout(new GridLayout(1,2,5,5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);

        panelData.add(panelDate);

        panelData.add(labelBreed);
        panelData.add(breedList);

        panelData.add(labelWeight);
        textWeight.setText(Double.toString(dog.getWeight()));
        panelData.add(textWeight);

        panelButton.setLayout(new GridLayout(1,2, 5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(15, 90, 10, 90));
        buttonConfirm.addActionListener(registration);
        buttonCancel.addActionListener(e -> guiDogInfo.dispatchEvent(new WindowEvent(guiDogInfo, WindowEvent.WINDOW_CLOSING)));
        panelButton.add(buttonConfirm);
        panelButton.add(buttonCancel);

        panelOut.setLayout(new BorderLayout());
        panelOut.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);

        add(panelOut);

    }


    /**
     * Read the new values of the dog's information and update the database
     * @return returns the value "true" if the procedure was successful
     */
    private boolean setNewValues(){
        boolean update = false;
        int dogID = dog.getID();
        boolean updateName = proxy.updateDogName(dogID,textName.getText().toUpperCase());
        boolean updateBreed = proxy.updateDogBreed(dogID, breedList.getSelectedItem().toString());
        boolean updateWeight = false;

        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean updateDate = proxy.updateDogAge(dogID, dateOfBirth );

        if(checkNumber(textWeight.getText())){
            updateWeight = proxy.updateDogWeight(dogID, Double.parseDouble(textWeight.getText()));
        }


        if(updateName && updateBreed && updateWeight && updateDate){
            update = true;
        }


        return update;


    }

    private boolean checkNumber (String number){
        double n=0;

        try{
            n = Double.valueOf(number).doubleValue();

        }catch(NumberFormatException e){}

        if(n==0) {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid dog's weight", "", JOptionPane.ERROR_MESSAGE);
            return false;

        }else{
            return true;
        }

    }
}
