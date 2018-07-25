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

/**
 * This class allows to change customer settings by filling out all the required fields.
 */
public class GUISettings extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 600;

    /**
     * Frame height.
     */
    final int HEIGHT = 600;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Out panel, it contains all the other panels.
     */
    protected JPanel panelOut = new JPanel();

    /**
     * The panel  contains Customer fields (Name, Surname, Date of Birth, Country, City, Cap, Address, Phone Number).
     */
    protected JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to confirm the changes that the user has made or cancel them
     */
    protected JPanel panelButton = new JPanel();

    /**
     * The panel allows to enter the date of birth.
     */
    protected JPanel panelDate = new JPanel();

    /**
     * The panel  contains address fields ( Street, Number of Street ).
     */
    protected JPanel panelAddress = new JPanel();

    /**
     * The panel  contains Credit Card information.
     */
    protected JPanel panelPayment = new JPanel();

    /**
     * The panel allows to enter the expiration date.
     */
    protected JPanel panelExpiration = new JPanel();

    /**
     * The label for the Name of the user
     */
    protected JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);

    /**
     * The label for the Surname of the user
     */
    protected JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);

    /**
     * The label for the date of birth of the user.
     */
    protected JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);

    /**
     * The label for the address of the user.
     */
    protected JLabel labelAddress = new JLabel("Address:", SwingConstants.LEFT);

    /**
     * The label for the country of the user.
     */
    protected JLabel labelCountry = new JLabel("Country:", SwingConstants.LEFT);

    /**
     * The label for the city of the user.
     */
    protected JLabel labelCity = new JLabel("City:", SwingConstants.LEFT);

    /**
     * The label for the street address of the user.
     */
    protected JLabel labelStreet = new JLabel("Street:", SwingConstants.LEFT);

    /**
     * The label for the number street of the user.
     */
    protected JLabel labelNumber = new JLabel();

    /**
     * The label for the cap city of the user.
     */
    protected JLabel labelCap = new JLabel("Cap:", SwingConstants.LEFT);

    /**
     * The label for the telephone number.
     */
    protected JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);

    /**
     * The label for the name of credit card owner.
     */
    protected JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);

    /**
     * The label for the credit card number.
     */
    protected JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);

    /**
     * The label for the expiration date of the credit card.
     */
    protected JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT); //data di scadenza

    /**
     * The label for the security code of the credit cart.
     */
    protected JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);

    /**
     * The label for the surname of credit card owner.
     */
    protected JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);

    /**
     * The text area for the name of the costumer.
     */
    protected JTextField textName = new JTextField();

    /**
     * The text area for the surname of the costumer.
     */
    protected JTextField textSurname = new JTextField();

    /**
     * The text area for the street address.
     */
    protected JTextField textStreet = new JTextField();

    /**
     * The text area for the number street address.
     */
    protected JTextField textStreetNumber = new JTextField();

    /**
     * The text area for the city address.
     */
    protected JTextField textCity = new JTextField();

    /**
     * The text area for the country where the costumer is live.
     */
    protected JTextField textCountry = new JTextField();

    /**
     * The text area for the cap city.
     */
    protected JTextField textCap = new JTextField();

    /**
     * The text area for the telephone number.
     */
    protected JTextField textPhoneNumber = new JTextField();


    /**
     * The text area for the name of the credit card owner.
     */
    protected JTextField textCreditCardOwnerName = new JTextField();

    /**
     * The text area for the credit card number.
     */
    protected JTextField textCreditCardNumber = new JTextField();

    /**
     * The text area for the security code of the credit card.
     */
    protected JTextField textSecurityCode = new JTextField();

    /**
     * The text area for the surname of the credit card owner.
     */
    protected JTextField textCreditCardOwneSurname = new JTextField();

    /**
     * Button with the inscription "Confirm".
     */
    protected JButton buttonConfirm = new JButton("Confirm");

    /**
     * Button with the inscription "Cancel".
     */
    protected JButton buttonCancel = new JButton("Cancel");

    /**
     * Combo box with list of days.
     */
    protected JComboBox<String> dayList;

    /**
     * Combo box with list of months.
     */
    protected JComboBox<String> monthList;

    /**
     * Combo box with list of years.
     */
    protected JComboBox<String> yearList;

    /**
     * Combo box with a list of month for the credit card expiration.
     */
    protected JComboBox<String> expirationMonth;

    /**
     * Combo box with a list of years for the credit card expiration.
     */
    protected JComboBox<String> expirationYear;

    /**
     * An array of strings for the days number.
     */
    protected String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * An array of strings for the months number.
     */
    protected String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    /**
     * An array list for the years.
     */
    protected ArrayList<String> years_tmp = new ArrayList<>();

    /**
     * An array of strings for the months number for the credit card expiration.
     */
    protected String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    /**
     * An array of strings for the years number for the credit card expiration.
     */
    protected String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    //  protected JTextField textExpirationDays = new JTextField();



    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The email address of the costumer.
     */
    protected String email;

    /**
     * This GUI.
     */
    private GUISettings guiSettings;



    /**
     * Constructor
     * @param email  the user's email.
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
        guiSettings = this;
        guiHome.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiHome.setEnabled(true);
            }
        });



        initComponents();
    }

    /**
     * Method that initializes the GUI components
     */

    protected void initComponents() {

        this.proxy = new CustomerProxy(email);
        setValues();

        panelOut = new JPanel();
        panelData = new JPanel();
        panelButton = new JPanel();
        panelDate = new JPanel();
        panelAddress = new JPanel();
        panelPayment = new JPanel();
        panelExpiration = new JPanel();






        panelOut.setLayout(new BorderLayout());
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




        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());



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




        expirationMonth = new JComboBox<>(expirationMonths);
        expirationYear = new JComboBox<>(expirationYears);


        PaymentMethod strExpirationDate = proxy.getPaymentMethod();
        SimpleDateFormat expirationDateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        String expiration_Months = expirationDateFormatmm.format(strExpirationDate.getExpirationDate());
        String expiration_Years = expirationDateFormatyyyy.format(strExpirationDate.getExpirationDate());

        expirationMonth.setSelectedItem(expiration_Months);
        expirationYear.setSelectedItem(expiration_Years);



        Date exYear = new Date();

        try {
            exYear = expirationDateFormatyyyy.parse(expirationMonth.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //todo
        //  String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), exYear));





        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));

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

                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textStreetNumber.getText().equals("") || textPhoneNumber.getText().equals("")
                            || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);


                    } else if (checkDateOfBirth(dayList.getSelectedItem().toString(), monthList.getSelectedItem().toString(), yearList.getSelectedItem().toString()) && inputCap && inputAddressNumber && inputPhoneNumber) {

                        boolean inputCrediCardNumber = checkCreditCardNumber(textCreditCardNumber.getText());
                        Date inputDate = getNewExpirationDate();
                        boolean inputCvv = checkCvvNumber(textSecurityCode.getText());

                        if (inputCrediCardNumber && !(dateBeforeToday(inputDate)) && inputCvv) {

                            checkSetNewValues();
                            setNewValues();
                            JOptionPane.showMessageDialog(new JFrame(), "the information regarding the customer has been updated successfully", "", JOptionPane.INFORMATION_MESSAGE);
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
     * Method that inserts the values content in the database
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
     * Method that updates the database
     */

    protected void setNewValues() {
        proxy.updateName(textName.getText().toUpperCase());
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        proxy.updateSurname(textSurname.getText().toUpperCase());
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);


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
        // System.out.println("upPaymentMethod " + upPaymentMethod);

        textCreditCardNumber.setEditable(true);
        labelCreditCardNumber.setLabelFor(textCreditCardNumber);
        textCreditCardOwnerName.setEditable(true);
        labelCreditCardOwnerName.setLabelFor(textCreditCardOwnerName);
        textCreditCardOwneSurname.setEditable(true);
        labelCrediCardOwnerSurname.setLabelFor(textCreditCardOwneSurname);
        expirationMonth.setEnabled(true);
        expirationYear.setEnabled(true);
        textSecurityCode.setEditable(true);
        labelSecurityCode.setLabelFor(textSecurityCode);


        if (!(upPaymentMethod)) {
            System.out.println("Error in updating PaymentMethod");
        }


    }

    /**
     * This method check and update the credit card information.
     * @return true if credit card information was update successfully,else
     * @return false with message error.
     */
    protected boolean checkSetNewValues(){
        Date inputDate = getNewExpirationDate();// aggiorna la data di scadenza
        boolean upPaymentMethod = proxy.updatePaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText().toUpperCase(), textCreditCardOwneSurname.getText().toUpperCase(), inputDate, textSecurityCode.getText());

        if(upPaymentMethod){
            setNewValues();
            JOptionPane.showMessageDialog(new JFrame(), "Credit card information has been updated", "", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Credit card information has not been updated\n" + " change credit card number please ", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }


    /**
     * Method that checks if the entered data is a number
     * @param number number to check
     * @return true if entered data is a number,else
     * @return false with message error.
     *
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
     * This method checks if the cap number of the city has letters and numbers and has less than 5 digits.
     * @param capNumber
     * @return true if the condition occurs, else
     * @return false with message error.
     */


    protected boolean checkCapNumber(String capNumber) {
        int digits = capNumber.length();

        if ((digits <= 5)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid Cap ", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }


    /**
     * This method checks if the street number has only numbers and has less than 5 digits.
     * @param addressNumber
     * @return true if the condition occurs, else
     * @return false with message error.
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
     * This method checks if the telephone number is composed by only ten digits.
     * @param phoneNumber
     * @return true if the condition occurs, else
     * @return false with message error.
     */
    protected boolean checkPhoneNumber(String phoneNumber) {
        boolean number = checkNumber(phoneNumber);

        int digits = phoneNumber.length();

        if ((digits == 10) && number) {
            return true;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR! Invalid phone number", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }


    /**
     * check credit card number which must be composed of sixteen digits
     * @param crediCardNumber
     * @return true if the condition occurs, else
     * @return false with message error.
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
     * This method creates the date format "dd/MM/yyyy".
     * @param day
     * @param month
     * @param year
     * @return data the date format.
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
     * This method implements the credit card expiration date.
     * @return inputDate
     */

    protected Date getNewExpirationDate() {
        Date exYear = new Date();
        Date inputDate;
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");


        try {
            exYear = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }


        inputDate = buildDate(Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), exYear)), expirationMonth.getSelectedItem().toString(), expirationYear.getSelectedItem().toString());

        return inputDate;

    }



    /**
     * This method check if the expiration date of credit cards entered by the user is lower than today.
     * @param date the date entered by the user
     * @return false if the condition occurs, else
     * @return true with message error.
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
     * This method checks the security code of the credit card if is composed by only 3 digits
     * @param cvvNumber
     * @return true if the condition occurs, else
     * @return false with message error.
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


    /**
     * This method checks the date of birth for the months that do not have 31 days
     * @param day
     * @param month
     * @param year
     * @return false if the user try to enter more days that the month has, else
     * @return true
     */

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






}