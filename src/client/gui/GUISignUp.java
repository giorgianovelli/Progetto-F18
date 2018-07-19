package client.gui;


import client.Calendar;
import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import server.Availability;
import server.DogSize;
import server.User;
import server.bank.PaymentMethod;
import server.places.Address;
import server.places.Area;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class GUISignUp extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 650;
    final double MAX_AMOUNT = 500.0;
    final double MIN_AMOUNT = 50.0;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUISignUp guiSignUp;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelExpiration = new JPanel();
    private JPanel panelPayment = new JPanel();

    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);
    private JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);
    private JLabel labelEmail = new JLabel("Email:", SwingConstants.LEFT);
    private JLabel labelPassword = new JLabel("Password:", SwingConstants.LEFT);
    private JLabel labelConfirmPassword = new JLabel("Confirm Password:", SwingConstants.LEFT);
    private JLabel labelAddress = new JLabel("Address:", SwingConstants.LEFT);
    private JLabel labelCountry = new JLabel("Country:", SwingConstants.LEFT);
    private JLabel labelCity = new JLabel("City:", SwingConstants.LEFT);
    private JLabel labelCap = new JLabel("Cap:", SwingConstants.LEFT);
    private JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);

    private JTextField textName = new JTextField();
    private JTextField textSurname = new JTextField();
    private JTextField textEmail = new JTextField();
    private JPasswordField textPassword = new JPasswordField();
    private JPasswordField textConfirmPassword = new JPasswordField();
    private JTextField textStreet = new JTextField();
    private JTextField textNumber = new JTextField();
    private JTextField textCity = new JTextField();
    private JTextField textCountry = new JTextField();
    private JTextField textCap = new JTextField();
    private JTextField textPhoneNumber = new JTextField();

    private String Password;
    private String confirmPassword;

    private JButton buttonCustomerConfirm = new JButton("Next");
    private JButton buttonCancel = new JButton("Cancel");


    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<>();

    // valori per payment method

    private JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);
    private JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);
    private JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT);
    private JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);
    private JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);

    private JTextField textCreditCardOwneSurname = new JTextField();
    private JTextField textCreditCardOwnerName = new JTextField();
    private JTextField textCreditCardNumber = new JTextField();

    private JComboBox<String> expirationMonth;
    private JComboBox<String> expirationYear;
    private JTextField textSecurityCode = new JTextField();

    private String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    private JTextField textExpirationDays = new JTextField();


    // attributo per client-server
    private CustomerProxy proxy;


//______________________________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
     */

    public GUISignUp() {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        proxy = new CustomerProxy(textEmail.getText());

        guiSignUp = this;


        initComponents();
    }

//______________________________________________________________________________________________________________________________________________________________________________

    /**
     * inizializza le componenti dell'interfaccia
     */

    private void initComponents() {

        /**
         * Panels
         */

        panelData.setLayout(new GridLayout(11, 1, 40, 5));
        panelData.setBorder(BorderFactory.createTitledBorder("FIRST STEP_Customer Fields: "));
        panelPayment.setBorder(BorderFactory.createTitledBorder("Credit Card information: "));
        panelPayment.setLayout(new GridLayout(5, 1, 40, 5));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelPayment, BorderLayout.CENTER);
        panelOut.add(panelButton, BorderLayout.SOUTH);


        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelSurname);
        panelData.add(textSurname);
        panelData.add(labelDate);

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
         * others panels
         */

        panelDate.setLayout(new GridLayout(1, 3, 5, 5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);
        panelData.add(panelDate);

        panelData.add(labelEmail);
        panelData.add(textEmail);
        panelData.add(labelPassword);
        panelData.add(textPassword);
        panelData.add(labelConfirmPassword);
        panelData.add(textConfirmPassword);
        panelData.add(labelCountry);
        panelData.add(textCountry);
        panelData.add(labelCity);
        panelData.add(textCity);
        panelData.add(labelCap);
        panelData.add(textCap);

        panelData.add(labelAddress);
        panelAddress.setLayout(new BoxLayout(panelAddress, BoxLayout.X_AXIS));
        panelAddress.add(textStreet);
        panelAddress.add(textNumber);
        panelData.add(panelAddress);

        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);

        //-----------------------------------------------------------------------------------
        /**
         *   PAYMENT panels
         */
        panelPayment.add(labelCreditCardOwnerName);
        panelPayment.add(textCreditCardOwnerName);
        panelPayment.add(labelCrediCardOwnerSurname);
        panelPayment.add(textCreditCardOwneSurname);
        panelPayment.add(labelCreditCardNumber);
        panelPayment.add(textCreditCardNumber);
        panelPayment.add(labelExpirationDate);


        /**
         * JCOMBOBOX di EXPIRATION DATE
         */

        expirationMonth = new JComboBox<>(expirationMonths);
        expirationYear = new JComboBox<>(expirationYears);

        textExpirationDays.setEditable(false);
        labelExpirationDate.setLabelFor(textExpirationDays);

        //-----------------------------------------------------------------------------------


        /**
         * PANEL DI EXPIRATION DATE per sistemare le jcombobox
         *
         */

        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));
        panelExpiration.add(textExpirationDays);
        panelExpiration.add(expirationMonth);
        panelExpiration.add(expirationYear);
        panelPayment.add(panelExpiration);
        panelPayment.add(labelSecurityCode);
        panelPayment.add(textSecurityCode);
        add(panelOut);

        /**
         * Button panels
         */
        panelButton.setLayout(new GridLayout(1, 2, 5, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonCustomerConfirm, BorderLayout.SOUTH);


        //-----------------------------------------------------------------------------------

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Next")) {

                    Password = "";
                    confirmPassword = "";

                    Password = readPassword(textPassword.getPassword());
                    confirmPassword = readPassword(textConfirmPassword.getPassword());


                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textNumber.getText().equals("") || textPhoneNumber.getText().equals("") ||
                            Password == "" || confirmPassword == "" || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    } else {
                        boolean inputPassword = changePasswordFields(Password, confirmPassword);
                        boolean add = addCustomerValues();

                        if (add && inputPassword) {
                            JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                            GUICustomerLabel guiCustomerLabel = new GUICustomerLabel(textEmail.getText(), guiSignUp);
                            guiCustomerLabel.setVisible(true);
                            dispose();

                        }

                    }

                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    System.exit(0);
                }

            }


        };
        buttonCancel.addActionListener(registration);
        buttonCustomerConfirm.addActionListener(registration);

    }

//______________________________________________________________________________________________________________________________________________________________________________

    /**
     * metodo per inserire le tuple nel database
     */

    private boolean addCustomerValues() {
        /**
         *  data di nascita
         */
        Date dateOfBirth2 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();

        try {
            dateOfBirth2 = dateFormat.parse(strDateOfBirth);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * generazione casuale di amount
         */
        Double max = MAX_AMOUNT;
        Double min = MIN_AMOUNT;
        Random rand = new Random();
        Double amount = Math.round((min + (max - min) * rand.nextDouble()) * 100d) / 100d;

        /**
         *  data di scadenza
         */

        Date ex_Year = new Date();

        Date inputDate;
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        //prendo solo l'anno dalle comboBox per calcolare l'ultimo giorno del mese
        try {
            ex_Year = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //costruisco la data completa della nuova data di scadenza per la carta
        inputDate = buildDate(Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), ex_Year)), expirationMonth.getSelectedItem().toString(), expirationYear.getSelectedItem().toString());

        System.out.println(inputDate);

        PaymentMethod paymentMethod = new PaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText(), textCreditCardOwneSurname.getText(), inputDate, textSecurityCode.getText(), amount);

        /**
         * stampa il giorno sulla label nell'interfaccia
         */
        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), ex_Year));
        textExpirationDays.setText(expiration_Days);


        String strPassword = readPassword(textPassword.getPassword());
        Address address = new Address(textCountry.getText(), textCity.getText(), textStreet.getText(), textNumber.getText(), textCap.getText());


        return proxy.customerSignUp(textEmail.getText().toUpperCase(), textName.getText(), textSurname.getText(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod);


    }


//______________________________________________________________________________________________________________________________________________________________

    /**
     * legge password inserita dall'utente
     * @param password password inserita dall'utente
     * @return stringa che contiene la password letta
     */

    private String readPassword(char[] password) {
        String pwd = "";
        for (int i = 0; i < password.length; i++) {
            pwd += password[i];
        }
        return pwd;
    }


//______________________________________________________________________________________________________________________________________________________________


    /**
     * controlla se la password inserita nel campo "Password" corrisponda al campo "confirmPassword"
     * @param Password nuova password inserita dall'utente
     * @param confirmPassword  controlla la password inserita
     * @return true se le due password coincidono false altrimenti
     */

    public boolean changePasswordFields(String Password, String confirmPassword) {
        boolean updatePassword;

        if (Password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(new JFrame(), "you entered password correctly!", "", JOptionPane.INFORMATION_MESSAGE);
            updatePassword = true;


        } else {
            JOptionPane.showMessageDialog(new JFrame(), "New Password and Confirm Password do not match!", "Password error", JOptionPane.ERROR_MESSAGE);
            textPassword.setText("");
            textConfirmPassword.setText("");
            updatePassword = false;
        }


        return updatePassword;


    }


//______________________________________________________________________________________________________________________________________________________________

    /**
     *costruisce una data secondo il formato "dd/MM/yyyy"
     * @param day stringa giorno
     * @param month stringa mese
     * @param year stringa anno
     * @return data nel formato predefinito
     */


    private Date buildDate(String day, String month, String year) {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDateOfBirth = day + "/" + month + "/" + year;
        try {
            date = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


}