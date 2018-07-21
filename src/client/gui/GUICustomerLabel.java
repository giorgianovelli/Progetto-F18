package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Dog;
import sun.misc.FloatingDecimal;

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

public class GUICustomerLabel extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 400;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private GUICustomerLabel guiCustomerLabel;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelDate = new JPanel();

    private JLabel labelDogsName = new JLabel("Name of the dog:", SwingConstants.LEFT);
    private JLabel labelDogBreed = new JLabel("Dog breed:", SwingConstants.LEFT);
    private JLabel labelDogsWeight = new JLabel("Dog's weight:", SwingConstants.LEFT);
    private JLabel labelDogsAge = new JLabel("Age of the dog:", SwingConstants.LEFT);

    private JTextField textDogsName = new JTextField();
    private JTextField textDogsWeight = new JTextField();
    private JTextField textEmail = new JTextField();

    private JComboBox<String> breedList;
    private String[] breed;

    //jcombobox per data di nascita
    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<String>();


    private JButton buttonConfirm = new JButton("Confirm");


    // attributi per client-server
    private CustomerProxy proxy;
    private String email;



    /**
     * Costruttore
     * @param email identifica il proprietario del cane
     * @param guiSignUp interfaccia da cui è stata richiamata
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

        guiSignUp.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiSignUp.setEnabled(true);
            }
        });

        guiCustomerLabel = this;


        initComponents();
    }




    /**
     * inizializza le componenti dell'interfaccia
     */

    private void initComponents()  {

        /**
         * Panels
         */

        panelData.setLayout(new GridLayout(4, 1, 70, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("SECOND STEP_Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);

        //-----------------------------------------------------------------------------------

        /**
         * JCOMBOBOX di DATE OF BIRTH
         */
        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        //-----------------------------------------------------------------------------------
        /**
         * JCOMBOBOX per la razza dei cani
         */

        HashSet<String> breedSet = proxy.getDogsBreedsList();
        breed = new String[breedSet.size()];

        int i = 0;
        for (String breedStr : breedSet){
            breed[i] = breedStr;
            i++;
        }

        breedList = new JComboBox<>(breed);

       //-----------------------------------------------------------------------------------

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

        //-----------------------------------------------------------------------------------

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
                             dispose();
                             GUILogin guiLogin = new GUILogin();
                             guiLogin.setVisible(true);
                        }
                    }

                }
            }
        };
        buttonConfirm.addActionListener(registration);



    }



    /**
     * aggiunge un nuovo cane al database, in base ai parametri inseriti dall'utente
     * @return restituisce true se la procedura è avvenuta correttamente
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
     * controlla se il peso del cane inserito è un numero
     * @param number
     * @return
     */

    public static boolean checkNumber (String number){
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
