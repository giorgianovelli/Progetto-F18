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

/**
 * This class allows to sign up as a new customer by filling out all the required fields.
 */
public class GUISignUp extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 600;

    /**
     * Frame height.
     */
    final int HEIGHT = 650;

    /**
     * The initialized attribute maximum amount.
     */
    final double MAX_AMOUNT = 500.0;

    /**
     * The initialized attribute minimum amount.
     */
    final double MIN_AMOUNT = 50.0;

    /**
     * The screen's dimension.
     */
    protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * This GUI.
     */
    private GUISignUp guiSignUp;

    /**
     * The GUILogin.
     */
    private GUILogin guiLogin;

    /**
     * Out panel, it contains all the other panels.
     */
    protected JPanel panelOut = new JPanel();

    /**
     * The panel  contains Customer fields (Name, Surname, Date of Birth, Country, City, Cap, Address, Phone Number).
     */
    protected JPanel panelData = new JPanel();

    /**
     * The panel contains buttons to confirm or cancel the changes that the user made
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
     * The panel allows to enter the expiration date of the credit card.
     */
    protected JPanel panelExpiration = new JPanel();

    /**
     * The panel  contains Credit Card information.
     */
    protected JPanel panelPayment = new JPanel();

    /**
     * The label for the Name of the user.
     */
    protected JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);

    /**
     * The label for the Surname of the user.
     */
    protected JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);

    /**
     * The label for the date of birth of the user.
     */
    protected JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);

    /**
     * The label for the email field.
     */
    protected JLabel labelEmail = new JLabel("Email:", SwingConstants.LEFT);

    /**
     * The label for the password field.
     */
    protected JLabel labelPassword = new JLabel("Password:", SwingConstants.LEFT);

    /**
     * The label for the confirm password field.
     */
    protected JLabel labelConfirmPassword = new JLabel("Confirm Password:", SwingConstants.LEFT);

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
     * The label for the cap city of the user.
     */
    protected JLabel labelCap = new JLabel("Cap:", SwingConstants.LEFT);

    /**
     * The label for the telephone number.
     */
    protected JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);

    /**
     * The text area for the name of the user.
     */
    protected JTextField textName = new JTextField();

    /**
     * The text area for the surname of the user.
     */
    protected JTextField textSurname = new JTextField();

    /**
     * The text area for the email of the user.
     */
    protected JTextField textEmail = new JTextField();

    /**
     * The text area for the email password of the user.
     */
    protected JPasswordField textPassword = new JPasswordField();

    /**
     * The text area for the confirmation the email password of the user.
     */
    protected JPasswordField textConfirmPassword = new JPasswordField();

    /**
     * The text area for the street address.
     */
    protected JTextField textStreet = new JTextField();

    /**
     * The text area for the street number address.
     */
    protected JTextField textStreetNumber = new JTextField();

    /**
     * The text area for the city.
     */
    protected JTextField textCity = new JTextField();

    /**
     * The text area for the country where the user live.
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
     * Password attribute of string type.
     */
    protected String Password;

    /**
     * Confirm password attribute of string type.
     */
    protected String confirmPassword;

    /**
     * Button with the inscription "Next".
     */
    protected JButton buttonCustomerConfirm = new JButton("Next");

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
    protected JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT);

    /**
     * The label for the security code of the credit cart.
     */
    protected JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);

    /**
     * The label for the surname of credit card owner.
     */
    protected JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);


    /**
     * The text area for the surname of the credit card owner.
     */
    protected JTextField textCreditCardOwneSurname = new JTextField();

    /**
     * The text area for the name of the credit card owner.
     */
    protected JTextField textCreditCardOwnerName = new JTextField();

    /**
     * The text area for the credit card number.
     */
    protected JTextField textCreditCardNumber = new JTextField();

    /**
     * Combo box with a list of month for the credit card expiration.
     */
    protected JComboBox<String> expirationMonth;

    /**
     * Combo box with a list of year for the credit card expiration.
     */
    protected JComboBox<String> expirationYear;

    /**
     * The text area for the security code of the credit card.
     */
    protected JTextField textSecurityCode = new JTextField();

    /**
     * An array of strings for the months number.
     */
    protected String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    /**
     * An array of strings for the years number.
     */
    protected String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};

    /**
     * The text area for the expiration days.
     */
    protected JTextField textExpirationDays = new JTextField();


    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;




    /**
     * Constructor of this class
     * @param guiLogin interface from where GUISignUp is invoked
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
     * Method that initializes the GUI components
     */

    protected void initComponents() {

        proxy = new CustomerProxy(textEmail.getText());

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




        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());




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


        panelPayment.add(labelCreditCardOwnerName);
        panelPayment.add(textCreditCardOwnerName);
        panelPayment.add(labelCrediCardOwnerSurname);
        panelPayment.add(textCreditCardOwneSurname);
        panelPayment.add(labelCreditCardNumber);
        panelPayment.add(textCreditCardNumber);
        panelPayment.add(labelExpirationDate);



        expirationMonth = new JComboBox<>(expirationMonths);
        expirationYear = new JComboBox<>(expirationYears);

        textExpirationDays.setEditable(false);
        labelExpirationDate.setLabelFor(textExpirationDays);



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
     * This method inserts the parameters provided for the customer into the database.
     */

    protected boolean addCustomerValues() {

        Date dateOfBirth2 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();

        try {
            dateOfBirth2 = dateFormat.parse(strDateOfBirth);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        Double max = MAX_AMOUNT;
        Double min = MIN_AMOUNT;
        Random rand = new Random();
        Double amount = Math.round((min + (max - min) * rand.nextDouble()) * 100d) / 100d;


        Date ex_Year = new Date();

        Date inputDate  = getNewExpirationDate();
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");


        try {
            ex_Year = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PaymentMethod paymentMethod = new PaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText().toUpperCase(), textCreditCardOwneSurname.getText().toUpperCase(), inputDate, textSecurityCode.getText(), amount);


        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expirationMonth.getSelectedItem().toString()), ex_Year));
        textExpirationDays.setText(expiration_Days);


        String strPassword = readPassword(textPassword.getPassword());
        Address address = new Address(textCountry.getText().toUpperCase(), textCity.getText().toUpperCase(), textStreet.getText().toUpperCase(), textStreetNumber.getText().toUpperCase(), textCap.getText().toUpperCase());


        return proxy.customerSignUp(textEmail.getText().toUpperCase(), textName.getText().toUpperCase(), textSurname.getText().toUpperCase(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod);


    }


    /**
     * This method check that the parameters provided for the customer are correctly included into the database.
     * @return true if the account was created successfully
     * else return a message error.
     */
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
                    JOptionPane.showMessageDialog(new JFrame(), "Account successfully created!", "", JOptionPane.INFORMATION_MESSAGE);
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
     * This method checks  if the email entered matches with the standard format
     * @param email insert by the user
     * @return true if the syntax of the email is correct,else
     * @return false with message error.
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
     * This method allows the reading of the password parameter insert by the user
     * @param password  the password insert by the user
     * @return pdw the password read
     */

    protected String readPassword(char[] password) {
        String pwd = "";
        for (int i = 0; i < password.length; i++) {
            pwd += password[i];
        }
        return pwd;
    }


    /**
     * This method check if the password entered in the "Password" field corresponds to the "confirmPassword" field
     * @param Password the new password inserts by the costumer
     * @param confirmPassword  must be like "Password"
     * @return true if the passwords are correctly insert,else
     * @return false with message error
     */

    protected boolean changePasswordFields(String Password, String confirmPassword) {
        boolean updatePassword;

        if (Password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(new JFrame(), "Passwords match!", "", JOptionPane.INFORMATION_MESSAGE);
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
     * Method that checks if the entered data is a number
     * @param number number to check
     * @return true if entered data is a number,else
     * @return false with message error.
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
            textCap.setText("");
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
            textPhoneNumber.setText("");
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
     * This method implements the credit card expiration date.
     * @return inputDate
     */
    protected Date getNewExpirationDate() {
        Date exYear = new Date();
        Date inputDate;
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");


        try {
            exYear = expirationDateFormatyyyy.parse(expirationYear.getSelectedItem().toString());
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



    /**
     * Method getter for the GuiLogin.
     */
    public GUILogin getGuiLogin() {
        return guiLogin;
    }



}