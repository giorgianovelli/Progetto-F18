package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import com.mysql.fabric.xmlrpc.base.Data;
import server.User;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import server.bank.PaymentMethod;


public class GUISettings extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    protected JPanel panelOut = new JPanel();
    protected JPanel panelData = new JPanel();
    protected JPanel panelButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelPayment = new JPanel();
    private JPanel panelExpiration = new JPanel();

    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);
    private JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);
    private JLabel labelAddress = new JLabel("Address:", SwingConstants.LEFT);
    private JLabel labelCountry = new JLabel("Country:", SwingConstants.LEFT);
    private JLabel labelCity = new JLabel("City:", SwingConstants.LEFT);
    private JLabel labelStreet = new JLabel("Street:", SwingConstants.LEFT);
    private JLabel labelNumber = new JLabel();
    private JLabel labelCap = new JLabel("Cap:", SwingConstants.LEFT);
    private JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);

    // label per titolare carta di credito
    private JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);
    private JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);
    private JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT); //data di scadenza
    private JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);
    private JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);

    protected JTextField textName = new JTextField();
    protected JTextField textSurname = new JTextField();
    protected JTextField textStreet = new JTextField();
    protected JTextField textStreetNumber = new JTextField();
    protected JTextField textCity = new JTextField();
    protected JTextField textCountry = new JTextField();
    protected JTextField textCap = new JTextField();
    protected JTextField textPhoneNumber = new JTextField();

    // textfield per titolare carta di credito
    protected JTextField textCreditCardOwnerName = new JTextField();
    protected JTextField textCreditCardNumber = new JTextField();
    protected JTextField textSecurityCode = new JTextField();
    protected JTextField textCreditCardOwneSurname = new JTextField();


    protected JButton buttonConfirm = new JButton("Confirm");
    protected JButton buttonCancel = new JButton("Cancel");

    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private JComboBox<String> expirationMonth;
    private JComboBox<String> expirationYear;

    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<>();

    private String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    private JTextField textExpirationDays = new JTextField();

    // attributi per client-server
    private CustomerProxy proxy;
    private String email;
    private GUISettings guiSettings;



    /**
     * Costruttore
     *
     * @param email: riferimento all'utente
     * @param guiHome interface from where GUISettings is invoked
     */


    public GUISettings(String email, GUIHome guiHome) {
        setTitle("Account settings");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.email = email;
        this.proxy = new CustomerProxy(email);
        guiSettings = this;
        guiHome.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiHome.setEnabled(true);
            }
        });

        setValues();

        initComponents();
    }


    /**
     * inizializza le componenti dell'interfaccia
     */

    private void initComponents() {
        /**
         * Panel
         */
       
        panelData.setLayout(new GridLayout(8, 1, 40, 10));
        panelData.setBorder(BorderFactory.createTitledBorder("Customer Fields: "));
        panelPayment.setBorder(BorderFactory.createTitledBorder("Credit Card information: "));
        panelPayment.setLayout(new GridLayout(5, 1, 40, 10));

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
         *  Serve per fare in modo che le jcombobox di "Date of Birth" siano corrette
         */

        Date strDate = proxy.getDateOfBirth();
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);


        /**
         * others panel
         */

        panelDate.setLayout(new GridLayout(1, 3, 5, 5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);
        panelData.add(panelDate);
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

        /**
         *  Serve per fare in modo che le jcombobox di "ExpirationDate" siano corrette
         */
        PaymentMethod strExpirationDate = proxy.getPaymentMethod();
        SimpleDateFormat expirationDateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        String expiration_Months = expirationDateFormatmm.format(strExpirationDate.getExpirationDate());
        String expiration_Years = expirationDateFormatyyyy.format(strExpirationDate.getExpirationDate());

        expirationMonth.setSelectedItem(expiration_Months);
        expirationYear.setSelectedItem(expiration_Years);

        /**
         * inserimento ultimo giorno del mese in modo automatico
         */

        Date exYear = new Date();

        try {
            exYear = expirationDateFormatyyyy.parse(expirationMonth.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), exYear));

        /**
         * stampa il giorno sulla label nell'interfaccia
         */

        textExpirationDays.setText(expiration_Days);
        textExpirationDays.setEditable(false);
        labelExpirationDate.setLabelFor(textExpirationDays);


        /**
         * PANEL DI EXPIRATION DATE per sistemare le jcombobox
         * e PANEL dei BOTTONI
         */

        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));

        panelExpiration.add(textExpirationDays);
        panelExpiration.add(expirationMonth);
        panelExpiration.add(expirationYear);
        panelPayment.add(panelExpiration);

        panelPayment.add(labelSecurityCode);
        panelPayment.add(textSecurityCode);

        add(panelOut);
        panelButton.setLayout(new GridLayout(1, 2, 5, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);


        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {

                    boolean inputCap = checkCapNumber(textCap.getText());
                    boolean inputAddressNumber = checkAddressNumber(textStreetNumber.getText());
                    boolean inputPhoneNumber = checkPhoneNumber(textPhoneNumber.getText());

                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textStreetNumber.getText().equals("") || textPhoneNumber.getText().equals("") || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);


                    } else if (inputCap && inputAddressNumber && inputPhoneNumber) {

                        boolean inputCrediCardNumber = checkCreditCardNumber(textCreditCardNumber.getText());
                        Date inputDate = getNewExpirationDate();
                        boolean inputCvv = checkCvvNumber(textSecurityCode.getText());

                        if (inputCrediCardNumber && !(dateBeforeToday(inputDate)) && inputCvv) {

                            setNewValues();
                            JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                            guiSettings.dispatchEvent(new WindowEvent(guiSettings, WindowEvent.WINDOW_CLOSING));
                        }

                    }
                }
            }
        };
        buttonCancel.addActionListener(e -> guiSettings.dispatchEvent(new WindowEvent(guiSettings, WindowEvent.WINDOW_CLOSING)));
        buttonConfirm.addActionListener(registration);
    }

    /**
     * METODO PER INSERIRE I VALORI CONTENUTI NEL DATABASE
     */

    protected void setValues() {

        String strName = proxy.getName();
        textName.setText(strName);
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        String strSurname = proxy.getSurname();
        textSurname.setText(strSurname);
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        Address customerAddress = proxy.getAddress();
        textStreet.setText(customerAddress.getStreet());
        textStreet.setEditable(true);
        labelStreet.setLabelFor(textStreet);

        textStreetNumber.setText(customerAddress.getNumber());
        textStreetNumber.setEditable(true);
        labelStreet.setLabelFor(textStreetNumber);

        textCountry.setText(customerAddress.getCountry());
        textCountry.setEditable(true);
        labelCountry.setLabelFor(textCountry);

        textCity.setText(customerAddress.getCity());
        textCity.setEditable(true);
        labelCity.setLabelFor(textCity);

        textCap.setText(customerAddress.getCap());
        textCap.setEditable(true);
        labelCap.setLabelFor(textCap);

        String strPhoneNumber = proxy.getPhoneNumber();
        textPhoneNumber.setText(strPhoneNumber);
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);

        PaymentMethod strPaymentmethod = proxy.getPaymentMethod();
        textCreditCardOwnerName.setText(strPaymentmethod.getName());
        textCreditCardOwnerName.setEditable(true);
        labelCreditCardOwnerName.setLabelFor(textCreditCardOwnerName);


        textCreditCardOwneSurname.setText(strPaymentmethod.getSurname());
        textCreditCardOwneSurname.setEditable(true);
        labelCrediCardOwnerSurname.setLabelFor(textCreditCardOwneSurname);

        textCreditCardNumber.setText(strPaymentmethod.getNumber());
        textCreditCardNumber.setEditable(true);
        labelCreditCardNumber.setLabelFor(textCreditCardNumber);


        textSecurityCode.setText(strPaymentmethod.getCvv());
        textSecurityCode.setEditable(true);
        labelSecurityCode.setLabelFor(textSecurityCode);


    }


    /**
     * METODO PER  AGGIORNARE IL DATABASE
     */

    protected void setNewValues() {
        proxy.updateName(textName.getText().toUpperCase());
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        proxy.updateSurname(textSurname.getText().toUpperCase());
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        /**
         * aggiorna la data di nascita
         */

        Date dateOfBirth = buildDate(dayList.getSelectedItem().toString(), monthList.getSelectedItem().toString(), yearList.getSelectedItem().toString());
        System.out.println(dateOfBirth);
        boolean updateDate = proxy.updateDateOfBirth(dateOfBirth);

        if (!(updateDate)) {
            System.out.println("Error in updating the date of birth");
        }

        proxy.updateAddress(textCountry.getText().toUpperCase(), textCity.getText().toUpperCase(), textStreet.getText().toUpperCase(), textStreetNumber.getText().toUpperCase(), textCap.getText().toUpperCase());
        textCountry.setEditable(true);
        labelCountry.setLabelFor(textCountry);
        textCity.setEditable(true);
        labelCity.setLabelFor(textCity);
        textStreet.setEditable(true);
        labelStreet.setLabelFor(textStreet);
        textStreetNumber.setEditable(true);
        labelNumber.setLabelFor(textStreetNumber);
        textCap.setEditable(true);
        labelCap.setLabelFor(textCap);

        proxy.updatePhoneNumber(textPhoneNumber.getText());
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);

        Date inputDate = getNewExpirationDate();// aggiorna la data di scadenza


        boolean upPaymentMethod = proxy.updatePaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText().toUpperCase(), textCreditCardOwneSurname.getText().toUpperCase(), inputDate, textSecurityCode.getText());
        System.out.println("upPaymentMethod " + upPaymentMethod);

        textCreditCardNumber.setEditable(true);
        labelCreditCardNumber.setLabelFor(textCreditCardNumber);
        textCreditCardOwnerName.setEditable(true);
        labelCreditCardOwnerName.setLabelFor(textCreditCardOwnerName);
        textCreditCardOwneSurname.setEditable(true);
        labelCrediCardOwnerSurname.setLabelFor(textCreditCardOwneSurname);
        textExpirationDays.setEditable(true);
        expirationMonth.setEnabled(true);
        expirationYear.setEnabled(true);
        textSecurityCode.setEditable(true);
        labelSecurityCode.setLabelFor(textSecurityCode);


        if (!(upPaymentMethod)) {
            System.out.println("Error in updating PaymentMethod");
        }


    }

    /**
     * controlla se il dato inserito è un numero
     * @param number
     * @return
     */

    private boolean checkNumber (String number){
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


    private boolean checkCapNumber(String capNumber) {
        int digits = capNumber.length();

        if ((digits <= 5)) {
            // JOptionPane.showMessageDialog(new JFrame(), "syntax of Cap is correct", "", JOptionPane.INFORMATION_MESSAGE);
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
    private boolean checkAddressNumber(String addressNumber) {
        int digits = addressNumber.length();

        if ((digits < 5) ) {
            // JOptionPane.showMessageDialog(new JFrame(), "syntax of Street number is correct", "", JOptionPane.INFORMATION_MESSAGE);
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
    private boolean checkPhoneNumber(String phoneNumber) {
        boolean number = checkNumber(phoneNumber);

        int digits = phoneNumber.length();

        if ((digits == 10) && number) {
            // JOptionPane.showMessageDialog(new JFrame(), "syntax of phone number is correct", "", JOptionPane.INFORMATION_MESSAGE);
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

    private boolean checkCreditCardNumber(String crediCardNumber) {
        boolean number = checkNumber(crediCardNumber);

        int digits = crediCardNumber.length();

        if ((digits == 16) && number) {
            //  JOptionPane.showMessageDialog(new JFrame(), "syntax of Credit card number is correct", "", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Credit card number ", "", JOptionPane.ERROR_MESSAGE);
            textCreditCardNumber.setText("");
            return false;
        }

    }




    /**
     * costruisce una data secondo il formato "dd/MM/yyyy"
     * @param day stringa giorno
     * @param month stringa mese
     * @param year stringa anno
     * @return data nel formato predefinito
     */

    private Date buildDate(String day, String month, String year) {

        Date date = new Date();
        String strDateOfBirth = day + "/" + month + "/" + year;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }



    /**
     *implementa la data di scadenza della carta di credito
     * @return data di scadenza
     */
    private Date getNewExpirationDate() {
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
    private boolean dateBeforeToday(Date date) {
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

    private boolean checkCvvNumber(String cvvNumber) {
        boolean number = checkNumber(cvvNumber);

        int digits = cvvNumber.length();

        if ((digits <= 3) && number) {
            // JOptionPane.showMessageDialog(new JFrame(), " CVV is correct", "", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid CVV ", "", JOptionPane.ERROR_MESSAGE);
            textSecurityCode.setText("");
            return false;
        }



    }





}