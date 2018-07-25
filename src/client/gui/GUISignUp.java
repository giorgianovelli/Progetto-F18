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

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUISignUp extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 650;
    final double MAX_AMOUNT = 500.0;
    final double MIN_AMOUNT = 50.0;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUISignUp guiSignUp;
    private GUILogin guiLogin;

    protected JPanel panelOut = new JPanel();
    protected JPanel panelData = new JPanel();
    protected JPanel panelButton = new JPanel();
    protected JPanel panelDate = new JPanel();
    protected JPanel panelAddress = new JPanel();
    protected JPanel panelExpiration = new JPanel();
    protected JPanel panelPayment = new JPanel();

    protected JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    protected JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);
    protected JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);
    protected JLabel labelEmail = new JLabel("Email:", SwingConstants.LEFT);
    protected JLabel labelPassword = new JLabel("Password:", SwingConstants.LEFT);
    protected JLabel labelConfirmPassword = new JLabel("Confirm Password:", SwingConstants.LEFT);
    protected JLabel labelAddress = new JLabel("Address:", SwingConstants.LEFT);
    protected JLabel labelCountry = new JLabel("Country:", SwingConstants.LEFT);
    protected JLabel labelCity = new JLabel("City:", SwingConstants.LEFT);
    protected JLabel labelCap = new JLabel("Cap:", SwingConstants.LEFT);
    protected JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);

    protected JTextField textName = new JTextField();
    protected JTextField textSurname = new JTextField();
    protected JTextField textEmail = new JTextField();
    protected JPasswordField textPassword = new JPasswordField();
    protected JPasswordField textConfirmPassword = new JPasswordField();
    protected JTextField textStreet = new JTextField();
    protected JTextField textStreetNumber = new JTextField();
    protected JTextField textCity = new JTextField();
    protected JTextField textCountry = new JTextField();
    protected JTextField textCap = new JTextField();
    protected JTextField textPhoneNumber = new JTextField();

    protected String Password;
    protected String confirmPassword;

    protected JButton buttonCustomerConfirm = new JButton("Next");
    protected JButton buttonCancel = new JButton("Cancel");


    protected JComboBox<String> dayList;
    protected JComboBox<String> monthList;
    protected JComboBox<String> yearList;

    protected String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    protected String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    protected ArrayList<String> years_tmp = new ArrayList<>();

    // valori per payment method

    protected JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);
    protected JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);
    protected JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT);
    protected JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);
    protected JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);

    protected JTextField textCreditCardOwneSurname = new JTextField();
    protected JTextField textCreditCardOwnerName = new JTextField();
    protected JTextField textCreditCardNumber = new JTextField();

    protected JComboBox<String> expirationMonth;
    protected JComboBox<String> expirationYear;
    protected JTextField textSecurityCode = new JTextField();

    protected String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    protected String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    protected JTextField textExpirationDays = new JTextField();


    // attributo per client-server
    private CustomerProxy proxy;




    /**
     * Constructor
     */

    public GUISignUp(GUILogin guiLogin) {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.guiLogin = guiLogin;
        guiLogin.setEnabled(false);
        guiSignUp = this;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiLogin.setEnabled(true);
            }
        });



        initComponents();
    }



    /**
     * inizializza le componenti dell'interfaccia
     */

    protected void initComponents() {

        proxy = new CustomerProxy(textEmail.getText());
        /**
         * Panels
         */
        panelOut.setLayout(new BorderLayout());
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



        /**
         * JCOMBOBOX di DATE OF BIRTH
         */

        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());



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
        panelAddress.add(textStreetNumber);
        panelData.add(panelAddress);

        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);


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


        /**
         * PANEL DI EXPIRATION DATE per sistemare le jcombobox
         *
         */

        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));
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



        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Next")) {

                    Password = "";
                    confirmPassword = "";
                    Password = readPassword(textPassword.getPassword());
                    confirmPassword = readPassword(textConfirmPassword.getPassword());


                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textStreetNumber.getText().equals("") || textPhoneNumber.getText().equals("") ||
                            textEmail.getText().equals("") || Password == "" || confirmPassword == "" || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);


                    }else if (checkEmail(textEmail.getText()) ) {
                        checkAddCustomerValues();
                    }
                }

            }
        };
        buttonCancel.addActionListener(e -> guiSignUp.dispatchEvent(new WindowEvent(guiSignUp, WindowEvent.WINDOW_CLOSING)));
        buttonCustomerConfirm.addActionListener(registration);

    }

    /**
     * metodo per inserire le tuple nel database
     */

    protected boolean addCustomerValues() {
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

        Date inputDate  = getNewExpirationDate();// aggiorna la data di scadenza
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        //prendo solo l'anno dalle comboBox per calcolare l'ultimo giorno del mese
        try {
            ex_Year = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PaymentMethod paymentMethod = new PaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText().toUpperCase(), textCreditCardOwneSurname.getText().toUpperCase(), inputDate, textSecurityCode.getText(), amount);

        /**
         * stampa il giorno sulla label nell'interfaccia
         */
        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), ex_Year));
        textExpirationDays.setText(expiration_Days);


        String strPassword = readPassword(textPassword.getPassword());
        Address address = new Address(textCountry.getText().toUpperCase(), textCity.getText().toUpperCase(), textStreet.getText().toUpperCase(), textStreetNumber.getText().toUpperCase(), textCap.getText().toUpperCase());


        return proxy.customerSignUp(textEmail.getText().toUpperCase(), textName.getText().toUpperCase(), textSurname.getText().toUpperCase(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod);


    }


    protected void checkAddCustomerValues() {

        boolean inputPassword = changePasswordFields(Password, confirmPassword);
        boolean inputCap = checkCapNumber(textCap.getText());
        boolean inputAddressNumber = checkAddressNumber(textStreetNumber.getText());
        boolean inputPhoneNumber = checkPhoneNumber(textPhoneNumber.getText());

        if(checkDateOfBirth(dayList.getSelectedItem().toString(), monthList.getSelectedItem().toString(), yearList.getSelectedItem().toString()) && inputPassword && inputCap && inputAddressNumber && inputPhoneNumber){

            boolean inputCrediCardNumber = checkCreditCardNumber(textCreditCardNumber.getText());
            Date inputDate = getNewExpirationDate();
            boolean inputCvv = checkCvvNumber(textSecurityCode.getText());

            if(inputCrediCardNumber && !(dateBeforeToday(inputDate)) && inputCvv){
                if(addCustomerValues()){
                    JOptionPane.showMessageDialog(new JFrame(), "Account creation was successful!", "", JOptionPane.INFORMATION_MESSAGE);
                    GUICustomerLabel guiCustomerLabel = new GUICustomerLabel(textEmail.getText().toUpperCase(), guiSignUp);
                    guiCustomerLabel.setVisible(true);
                    guiSignUp.setVisible(false);

                }

                else {
                    JOptionPane.showMessageDialog(new JFrame(), "ERROR! Email address already used!", "", JOptionPane.ERROR_MESSAGE);


                }


            }
        }



    }



    /**
     * Controlla se l'email inserita corrisponda al formato "email@gmail.com"
     * @param email inserita dall'utente
     * @return true se corretta false altrimenti
     */

    protected boolean checkEmail (String email) {

        String expression = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";

        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(email);

        boolean matchFound = m.matches();

        if (matchFound) {
            JOptionPane.showMessageDialog(new JFrame(), "Syntax of the email is correct", "", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Email", "", JOptionPane.ERROR_MESSAGE);
            textEmail.setText("");
            return false;
        }

    }



    /**
     * legge password inserita dall'utente
     * @param password password inserita dall'utente
     * @return stringa che contiene la password letta
     */

    protected String readPassword(char[] password) {
        String pwd = "";
        for (int i = 0; i < password.length; i++) {
            pwd += password[i];
        }
        return pwd;
    }


    /**
     * controlla se la password inserita nel campo "Password" corrisponda al campo "confirmPassword"
     * @param Password nuova password inserita dall'utente
     * @param confirmPassword  controlla la password inserita
     * @return true se le due password coincidono false altrimenti
     */

    protected boolean changePasswordFields(String Password, String confirmPassword) {
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


    /**
     * controlla se il dato inserito è un numero
     * @param number
     * @return
     */

    protected boolean checkNumber (String number){
        long n=0;

        try{
            n=Long.parseLong(number);

        }catch(NumberFormatException e){}

        if(n==0) {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid number", "", JOptionPane.ERROR_MESSAGE);
            return false;

        }else{
            return true;
        }

    }


    /**
     * controlla se il CAP sia composto  da cifre o da lettere e che ne abbia 5 "es. E2"
     * @param capNumber
     * @return
     */


    protected boolean checkCapNumber(String capNumber) {
        int digits = capNumber.length();

        if ((digits <= 5)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Cap ", "", JOptionPane.ERROR_MESSAGE);
            textCap.setText("");
            return false;
        }

    }


    /**
     * controllo numero dell'indirizzo può contenere oltre alle cifre anche le lettere  es. 28A
     * @param addressNumber
     * @return
     */
    protected boolean checkAddressNumber(String addressNumber) {
        int digits = addressNumber.length();

        if ((digits < 5) ) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Street number ", "", JOptionPane.ERROR_MESSAGE);
            textStreetNumber.setText("");
            return false;
        }

    }


    /**
     * CONTROLLA IL NUMERO DI TELEFONO inserito deve contenere solo cifre ed averne 10
     * @param phoneNumber
     * @return
     */
    protected boolean checkPhoneNumber(String phoneNumber) {
        boolean number = checkNumber(phoneNumber);

        int digits = phoneNumber.length();

        if ((digits == 10) && number) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid phone number", "", JOptionPane.ERROR_MESSAGE);
            textPhoneNumber.setText("");
            return false;
        }

    }


    /**
     * controlla numero carta di credito solo cifre e 16
     * @param crediCardNumber
     * @return
     */

    protected boolean checkCreditCardNumber(String crediCardNumber) {
        boolean number = checkNumber(crediCardNumber);

        int digits = crediCardNumber.length();

        if ((digits == 16) && number) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Credit card number ", "", JOptionPane.ERROR_MESSAGE);
            textCreditCardNumber.setText("");
            return false;
        }

    }


    /**
     *costruisce una data secondo il formato "dd/MM/yyyy"
     * @param day stringa giorno
     * @param month stringa mese
     * @param year stringa anno
     * @return data nel formato predefinito
     */


    protected Date buildDate(String day, String month, String year) {

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

    /**
     * implementa la data di scadenza della carta di credito
     * @return data di scadenza
     */
    protected Date getNewExpirationDate() {
        Date exYear = new Date();
        Date inputDate;
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        //prendo solo l'anno dalle comboBox per calcolare l'ultimo giorno del mese
        try {
            exYear = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //costruisco la data completa della nuova data di scadenza per la carta
        inputDate = buildDate(Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), exYear)), expirationMonth.getSelectedItem().toString(), expirationYear.getSelectedItem().toString());

        return inputDate;

    }



    /**
     * controlla se la data di scadenza delle carte di credito inserita dall'utente nelle jcombobox è inferiore a quella odierna
     *
     * @param date data che viene controllata
     * @return ritorna true se la data da controllare è precedente rispetto a quella odierna
     */
    protected boolean dateBeforeToday(Date date) {
        Date todayDate = new Date(System.currentTimeMillis());

        if (date.before(todayDate)) {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Expiry Date ", "", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        return false;
    }




    /**
     * controlla numero di CVV
     * @param cvvNumber
     * @return
     */

    protected boolean checkCvvNumber(String cvvNumber) {
        boolean number = checkNumber(cvvNumber);

        int digits = cvvNumber.length();

        if ((digits <= 3) && number) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid CVV ", "", JOptionPane.ERROR_MESSAGE);
            textSecurityCode.setText("");
            return false;
        }



    }


    protected boolean checkDateOfBirth(String day, String month, String year){

        boolean check;


        switch (month) {

            case ("02"): {
                if (Integer.parseInt(year) % 4 != 0) {
                    if (day.equals("29") || day.equals("30") || day.equals("31")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                JOptionPane.ERROR_MESSAGE);
                        check = false;
                        break;


                    }
                }

                if (Integer.parseInt(year) % 4 == 0) {
                    if (day.equals("30") || day.equals("31")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                JOptionPane.ERROR_MESSAGE);
                        check = false;
                        break;
                    }
                }
            }

            case ("04"): {
                if (day.equals("31")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);
                    check = false;
                    break;
                }
            }

            case ("06"): {
                if (day.equals("31")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);
                    check = false;
                    break;
                }
            }

            case ("09"): {
                if (day.equals("31")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);
                    check = false;
                    break;
                }
            }

            case ("11"): {
                if (day.equals("31")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);
                    check = false;
                    break;
                }
            }
            default:{
                check =true;
                break;
            }


        }

        return check;
    }














    public GUILogin getGuiLogin() {
        return guiLogin;
    }










}