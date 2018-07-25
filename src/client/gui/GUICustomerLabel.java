package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Dog;
//import sun.misc.FloatingDecimal;

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
 * This class allows the creation of a new customer through the parameters.
 */
public class GUICustomerLabel extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 650;

    /**
     * Frame height.
     */
    final int HEIGHT = 400;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * This GUI.
     */
    private GUICustomerLabel guiCustomerLabel;

    /**
     * Out panel, it contains all the other panels.
     */
    private JPanel panelOut = new JPanel();

    /**
     * The panel contains dog's information (name, breed, weight and date of birth ).
     */
    private JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to add the new dog and to close the frame.
     */
    private JPanel panelButton = new JPanel();
    /**
     * The panel that allows to enter the date of birth.
     */
    private JPanel panelDate = new JPanel();

    /**
     * The label for the dog's name.
     */
    private JLabel labelDogsName = new JLabel("Name of the dog:", SwingConstants.LEFT);

    /**
     * The label of the dog's breed.
     */
    private JLabel labelDogBreed = new JLabel("Dog breed:", SwingConstants.LEFT);

    /**
     * The label of the dog's weight.
     */
    private JLabel labelDogsWeight = new JLabel("Dog's weight:", SwingConstants.LEFT);

    /**
     * The label for the dog's date of birth.
     */
    private JLabel labelDogsAge = new JLabel("Age of the dog:", SwingConstants.LEFT);

    /**
     * Text field for the dog's name.
     */
    private JTextField textDogsName = new JTextField();

    /**
     * Text field for the dog's weight.
     */
    private JTextField textDogsWeight = new JTextField();

    /**
     * Text field for the user email
     */
    private JTextField textEmail = new JTextField();

    /**
     * The combo box shows the list of the possible dog breeds.
     */
    private JComboBox<String> breedList;

    /**
     * Array of the possible dog breeds.
     */
    private String[] breed;

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
    private ArrayList<String> years_tmp = new ArrayList<String>();

    /**
     * The button that allows to add a new dog and confirm registration
     */
    private JButton buttonConfirm = new JButton("Confirm");

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The GUISignUp
     */
    private GUISignUp guiSignUp;



    /**
     * Constructor
     * @param email the user's email
     * @param guiSignUp GUI from where GUICustomerLabel is invoked
     */

    public GUICustomerLabel(String email, GUISignUp guiSignUp) {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.email = email;
        proxy = new CustomerProxy(email);
        this.guiSignUp = guiSignUp;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiSignUp.getGuiLogin().setEnabled(true);
            }
        });

        guiCustomerLabel = this;


        initComponents();
    }




    /**
     * Initialize the GUI components.
     */

    private void initComponents()  {


        panelData.setLayout(new GridLayout(4, 1, 70, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("SECOND STEP_Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);



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


        panelData.add(labelDogsName);
        panelData.add(textDogsName);
        panelData.add(labelDogBreed);
        panelData.add(breedList);
        panelData.add(labelDogsWeight);
        panelData.add(textDogsWeight);
        panelData.add(labelDogsAge);

        panelDate.setLayout(new GridLayout(1, 3, 5, 5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);
        panelData.add(panelDate);

        add(panelOut);

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    if (textDogsName.getText().equals("") || textDogsWeight.getText().equals("") ) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    } else if (checkNumber(textDogsWeight.getText())) {

                        boolean add = newDog();
                        if(add){
                            JOptionPane.showMessageDialog(new JFrame(), "Thanks for your registration!", "", JOptionPane.INFORMATION_MESSAGE);
                            guiCustomerLabel.dispatchEvent(new WindowEvent(guiCustomerLabel, WindowEvent.WINDOW_CLOSING));
                            guiSignUp.dispatchEvent(new WindowEvent(guiSignUp, WindowEvent.WINDOW_CLOSING));

                        }
                    }

                }
            }
        };
        buttonConfirm.addActionListener(registration);

    }

    /**
     * Add a new dog in the database
     * @return returns the value "true" if the procedure was successful
     */
    private boolean newDog(){
        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return proxy.addDog(textEmail.getText(),textDogsName.getText().toUpperCase(), breedList.getSelectedItem().toString(), dateOfBirth, Double.parseDouble(textDogsWeight.getText()));

    }


    /**
     * check if the weight of the inserted dog is a number
     * @param number number to check
     * @return false if entered data is not a number
     */

    private boolean checkNumber (String number){
        double n=0;

        try{
            n = Double.valueOf(number).doubleValue();

        }catch(NumberFormatException e){}

        if(n==0) {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid dog's weight", "", JOptionPane.ERROR_MESSAGE);
            textDogsWeight.setText("");
            return false;

        }else{
            return true;
        }

    }




}
