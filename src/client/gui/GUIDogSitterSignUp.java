package client.gui;

import client.Calendar;
import client.proxy.DogSitterProxy;
import server.Availability;
import server.DogSize;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This class allows the registration of a new dogsitter.
 */
public class GUIDogSitterSignUp extends GUISignUp {

    /**
     * The dog sitter proxy.
     */
    private DogSitterProxy dogSitterProxy;

    /**
     * This GUI.
     */
    private GUIDogSitterSignUp guiDogsitterSignUp;

    /**
     * Panel containing biography.
     */
    private JPanel bioPanel;

    /**
     * The north panel containing the dog sitter parameters.
     */
    private JPanel northPanel;

    /**
     * A internal panel who contains other panels.
     */
    private JPanel inPanel;

    /**
     * Label containing the biography of the dog sitter.
     */
    private JLabel bioLabel ;

    /**
     * The text area for the biography.
     */
    private JTextArea bioText;

    /**
     * Scroll Panel for the biography of the dog sitter.
     */
    private JScrollPane bioScroll ;

    /**
     * Label containing the numbers of dogs.
     */
    private JLabel labelDogsNumber;

    /**
     * Combo box for the numbers of dogs.
     */
    private JComboBox<String> dogsNumber;

    /**
     * Array of string for the numbers of dogs.
     */
    private String[] numberOfDogs;

    /**
     * Panel of radio button.
     */
    private JRadioButton cashflag;

    /**
     * Panel of radio button.
     */
    private JRadioButton cashflag2;

    /**
     * Label that allows the selection of cash payment.
     */
    private JLabel labelCash;

    /**
     * Label for the place of work of the dog sitter.
     */
    private JLabel areaLabel;

    /**
     * The text area for the place of work of the dog sitter.
     */
    private JTextField areaField;

    /**
     * The central panel containing the dog sitter availability.
     */
    private JPanel centerPanel;

    /**
     * Panel of radio buttons.
     */
    private JPanel panelRadioButton;

    /**
     * Panel containing the availability.
     */
    private JPanel availabilityPanel;

    /**
     * Panel containing the dogs size.
     */
    private JPanel dogSizePanel;

    /**
     * Label containing the dogs size.
     */
    private JLabel dogSizeLabel;

    /**
     * The list containing the dogs size.
     */
    //private JList dogSizeList;

    /**
     * Array of string containing the dog size.
     */
    private String[] dogSize;

    /**
     * Combo box for dogsitter availability.
     */
    private AvailabilityBox availabilityBox;

    /**
     * The list of the dogs size.
     */
    private HashSet <DogSize> listDogSize;

    /**
     * Scroll Panel for the external panel.
     */
    private JScrollPane scrollPane;


    private ArrayList<SizeCheckBox> listCheckbox;

    String[] dogSizesArray;


    /**
     * Constructor
     *
     * @param guiLogin GUI from which is invoked.
     *
     */
    public GUIDogSitterSignUp(GUILogin guiLogin){
        super(guiLogin);

        setSize(WIDTH + 350, HEIGHT);

        guiDogsitterSignUp = this;
    }


    /**
     * Method that initializes graphic components of the GUI
     */
    @Override
    protected void initComponents() {
        dogSitterProxy = new DogSitterProxy(textEmail.getText());

        buttonCustomerConfirm = new JButton("Confirm");
        scrollPane = new JScrollPane(panelOut);

        bioPanel = new JPanel();
        bioLabel = new JLabel("Biography:",SwingConstants.LEFT);
        bioText = new JTextArea();
        bioText.setLineWrap(true);
        bioText.setWrapStyleWord(true);
        bioScroll = new JScrollPane(bioText);

        labelCash = new JLabel("Allow cash payment:",SwingConstants.LEFT);
        cashflag = new JRadioButton("YES");
        cashflag2 = new JRadioButton("NO");
        panelRadioButton = new JPanel();

        areaLabel = new JLabel("Area:");
        areaField = new JTextField();

        dogSizeLabel = new JLabel("Dog Size:");
        dogSize = new String[]{"SMALL", "MEDIUM", "BIG", "GIANT"};

        listCheckbox = new ArrayList<>();
        dogSizesArray = new String[]{"SMALL", "MEDIUM", "BIG", "GIANT"};

        dogSizePanel = new JPanel(new GridLayout(5,1));
        dogSizePanel.add(dogSizeLabel);

        SizeCheckBox checkBox;

        for(int i = 0; i< dogSizesArray.length; i++){
            checkBox = new SizeCheckBox(dogSizesArray[i]);
            listCheckbox.add(checkBox);
        }


        for (SizeCheckBox checkBoxToAdd : listCheckbox){
            dogSizePanel.add(checkBoxToAdd);

        }


        centerPanel = new JPanel();

        labelDogsNumber = new JLabel("Number of dogs:");
        numberOfDogs = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        dogsNumber = new JComboBox<>(numberOfDogs);

        availabilityBox = new AvailabilityBox();

        availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new GridLayout(1,1));

        availabilityPanel.setBorder(BorderFactory.createTitledBorder("Availability"));
        availabilityPanel.add(availabilityBox);

        /**
         * Panels
         */

        northPanel = new JPanel();
        inPanel = new JPanel();
        inPanel.setLayout(new GridLayout(1,2,10,10));
        panelData.setLayout(new GridLayout(15, 1, 40, 5));
        panelData.setBorder(BorderFactory.createTitledBorder("FIRST STEP_Dogsitter Fields: "));
        panelPayment.setBorder(BorderFactory.createTitledBorder("Credit Card information: "));
        panelPayment.setLayout(new GridLayout(5, 1, 40, 5));


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

        panelData.add(areaLabel);
        panelData.add(areaField);

        panelData.add(labelDogsNumber);
        panelData.add(dogsNumber);

        panelData.add(labelCash);
        panelRadioButton.setLayout(new GridLayout(1,0));

        // buttongroup per far in modo che si possa selezionare SOLO un metodo di pagamento
        ButtonGroup group = new ButtonGroup();
        group.add(cashflag);
        group.add(cashflag2);

        panelRadioButton.add(cashflag);
        panelRadioButton.add(cashflag2);
        panelData.add(panelRadioButton);


        bioScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bioScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        bioPanel.setLayout(new BorderLayout());
        bioLabel.setBorder(BorderFactory.createEmptyBorder(10,0,20,0));
        bioPanel.add(bioLabel, BorderLayout.NORTH);
        bioPanel.add(bioScroll, BorderLayout.CENTER);

        dogSizePanel.setBorder(BorderFactory.createEmptyBorder(5,10,100,250));
        inPanel.add(dogSizePanel);
        inPanel.add(bioPanel);


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


        /**
         * PANEL DI EXPIRATION DATE per sistemare le jcombobox
         *
         */

        panelExpiration.setLayout(new GridLayout(1, 2, 5, 5));
        panelExpiration.add(expirationMonth);
        panelExpiration.add(expirationYear);
        panelPayment.add(panelExpiration);
        panelPayment.add(labelSecurityCode);
        panelPayment.add(textSecurityCode);

        panelOut.setLayout(new BorderLayout());
        panelOut.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        northPanel.setLayout(new BorderLayout());
        northPanel.add(panelData, BorderLayout.NORTH);
        northPanel.add(availabilityPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(panelPayment, BorderLayout.NORTH);


        inPanel.setBorder(BorderFactory.createTitledBorder(""));
        centerPanel.add(inPanel, BorderLayout.CENTER);


        panelOut.add(northPanel, BorderLayout.NORTH);
        panelOut.add(centerPanel, BorderLayout.CENTER); //biografia e taglia dei cani accettata
        panelOut.add(panelButton, BorderLayout.SOUTH);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        /**
         * Button panels
         */
        panelButton.setLayout(new GridLayout(1, 2, 5, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(15, 200, 10, 200));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonCustomerConfirm, BorderLayout.SOUTH);



        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {

                    Password = "";
                    confirmPassword = "";
                    Password = readPassword(textPassword.getPassword());
                    confirmPassword = readPassword(textConfirmPassword.getPassword());



                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textStreetNumber.getText().equals("") || textPhoneNumber.getText().equals("") ||
                            Password == "" || confirmPassword == "" || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    } else if (checkEmail(textEmail.getText())) {
                        checkAddCustomerValues();


                    }
                }
            }
        };
        buttonCancel.addActionListener(e -> guiDogsitterSignUp.dispatchEvent(new WindowEvent(guiDogsitterSignUp, WindowEvent.WINDOW_CLOSING)));
        buttonCustomerConfirm.addActionListener(registration);


    }


    /**
     * Method that checks the correct functionality of same parameters.
     *
     */
    @Override
    protected void checkAddCustomerValues() {
        boolean inputPassword = changePasswordFields(Password, confirmPassword);
        boolean inputCap = checkCapNumber(textCap.getText());
        boolean inputAddressNumber = checkAddressNumber(textStreetNumber.getText());
        boolean inputPhoneNumber = checkPhoneNumber(textPhoneNumber.getText());

        if(checkDateOfBirth(dayList.getSelectedItem().toString(), monthList.getSelectedItem().toString(), yearList.getSelectedItem().toString()) && inputPassword && inputCap && inputAddressNumber && inputPhoneNumber){

            boolean inputCreditCardNumber = checkCreditCardNumber(textCreditCardNumber.getText());
            Date inputDate = getNewExpirationDate();
            boolean inputCvv = checkCvvNumber(textSecurityCode.getText());

            if(inputCreditCardNumber && !(dateBeforeToday(inputDate)) && inputCvv){
                if(addCustomerValues()){
                    JOptionPane.showMessageDialog(new JFrame(), "Account creation was successful!", "", JOptionPane.INFORMATION_MESSAGE);
                    guiDogsitterSignUp.dispatchEvent(new WindowEvent(guiDogsitterSignUp, WindowEvent.WINDOW_CLOSING));
                }

                else {
                    JOptionPane.showMessageDialog(new JFrame(), "ERROR! Email address already used!", "", JOptionPane.ERROR_MESSAGE);

                }


            }
        }


    }


    @Override
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
     * Method that allows to add new information
     *
     */
    @Override
    protected boolean addCustomerValues() {
        /**
         *  date of birth
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
         * random generation o amount
         */
        Double max = MAX_AMOUNT;
        Double min = MIN_AMOUNT;
        Random rand = new Random();
        Double amount = Math.round((min + (max - min) * rand.nextDouble()) * 100d) / 100d;

        /**
         *  Expiration date
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
        Area area= new Area();
        area.addPlace(areaField.getText().toUpperCase());

        Availability dateTimeAvailability = new Availability();

        WorkingTime workingTime;
        WeekDays[] weekDays = WeekDays.values();

        Time timeStart, timeEnd;

        for(int i = 0 ; i < 7; i++){
            timeStart = Time.valueOf(availabilityBox.getFhourList()[i].getSelectedItem().toString()+":" +availabilityBox.getFminuteList()[i].getSelectedItem().toString()+":" + "00");
            timeEnd = Time.valueOf(availabilityBox.getThourList()[i].getSelectedItem().toString()+":"+availabilityBox.getTminuteList()[i].getSelectedItem().toString()+":" +"00");
            workingTime = new WorkingTime(timeStart, timeEnd);

            dateTimeAvailability.setDayAvailability(workingTime,weekDays[i]);

        }

        boolean acceptCash= false;

        if (cashflag.isSelected()) {
            acceptCash = true;
        } else if (cashflag2.isSelected()) {
            acceptCash = false;

        }


        HashSet<DogSize> dogSizeSelected = new HashSet<>();
        DogSize[] dogSizes = DogSize.values();
        for (SizeCheckBox sizeCheckBox: listCheckbox) {
            if(sizeCheckBox.getCheckBox().isSelected()) {
                dogSizeSelected.add(sizeCheckBox.getDogSizeFromJCheckBox(sizeCheckBox.getCheckBox().getText(), dogSizes));
            }

        }

        return dogSitterProxy.dogSitterSignUp(textEmail.getText().toUpperCase(), textName.getText().toUpperCase(), textSurname.getText().toUpperCase(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod, area , dogSizeSelected, Integer.parseInt(dogsNumber.getSelectedItem().toString()), bioText.getText().toUpperCase(), dateTimeAvailability, acceptCash);



    }


    
}
/**
 * This class is a graphic class that contains combo boxes and labels for
 * selecting date and hour of the availability of the dog sitter
 */
class AvailabilityBox extends JPanel{

    /**
     * an initialized attribute.
     */
    private final int DAYS = 7;

    /**
     * Array of label for the days of the week
     */
    private JLabel[] dayLabel;

    /**
     * Array of panel for the starting and ending date.
     */
    private JPanel[] fTimeBox, tTimeBox, contentPanel;

    /**
     * An external panel.
     */
    private JPanel outPanel = new JPanel();

    /**
     * Label that displays a period of time.
     */
    private JPanel tagPanel;

    /**
     * Array of label for the period of time.
     */
    private JLabel[] tagLabel;

    /**
     * Array of the days of week.
     */
    private WeekDays[] weekDays = WeekDays.values();

    /**
     * Combo box for the starting and ending date (hour).
     */
    private JComboBox<String> [] fhourList;

    /**
     * Combo box for the starting and ending date (hour).
     */
    private JComboBox<String> [] thourList;

    /**
     * Combo box for the starting and ending date (minute).
     */
    private JComboBox<String> [] fminuteList;

    /**
     * Combo box for the starting and ending date (minute).
     */
    private JComboBox<String> [] tminuteList;

    /**
     * Array of string who shows the hour.
     */
    private String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

    /**
     * Array of string who shows the minutes.
     */
    private String[] minute = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "59"};


    /**
     * Constructor for AvailabilityBox
     */
    public AvailabilityBox(){

        fTimeBox = new JPanel[DAYS];
        tTimeBox = new JPanel[DAYS];
        contentPanel = new JPanel[DAYS];

        fhourList = new JComboBox[DAYS];
        thourList = new JComboBox[DAYS];
        fminuteList= new JComboBox[DAYS];
        tminuteList = new JComboBox[DAYS];

        tagPanel = new JPanel();
        tagPanel.setLayout(new GridLayout(3,1));

        tagLabel = new JLabel[3];
        tagLabel[0] = new JLabel("");
        tagLabel[1] = new JLabel("From");
        tagLabel[2] = new JLabel("To");

        tagPanel.add(tagLabel[0]);
        tagPanel.add(tagLabel[1]);
        tagPanel.add(tagLabel[2]);

        dayLabel = new JLabel[DAYS];

        outPanel.setLayout(new GridLayout(1,8, 20, 20));
        outPanel.add(tagPanel);
        for (int i = 0; i<dayLabel.length; i++){
            dayLabel[i] = new JLabel(weekDays[i].name());
            outPanel.add(createPanel(i));
        }

        add(outPanel);

    }
    /**
     * method who create a panel who displayed the time.
     * @param i panel index
     */
    private JPanel createPanel(int i){

        fTimeBox[i] = new JPanel();
        fTimeBox[i].setLayout(new GridLayout(1,2, 5,5));

        tTimeBox[i] = new JPanel();
        tTimeBox[i].setLayout(new GridLayout(1,2,5,5));

        fhourList[i] = new JComboBox<>(hour);
        fminuteList[i] = new JComboBox<>(minute);

        thourList[i] = new JComboBox<>(hour);
        tminuteList[i] = new JComboBox<>(minute);

        fTimeBox[i].add(fhourList[i]);
        fTimeBox[i].add(fminuteList[i]);

        tTimeBox[i].add(thourList[i]);
        tTimeBox[i].add(tminuteList[i]);



        contentPanel[i] = new JPanel();
        contentPanel[i].setLayout(new GridLayout(3,1));
        contentPanel[i].add(dayLabel[i]);
        contentPanel[i].add(fTimeBox[i]);
        contentPanel[i].add(tTimeBox[i]);

        return contentPanel[i];

    }

    /**
     * method who provides  data
     * @return fhourList.
     */
    public JComboBox<String>[] getFhourList() {
        return fhourList;
    }

    /**
     * method who provides data
     * @return thourList.
     */
    public JComboBox<String>[] getThourList() {
        return thourList;
    }

    /**
     * method who provides data
     * @return fminuteList.
     */
    public JComboBox<String>[] getFminuteList() {
        return fminuteList;
    }

    /**
     * method who provides data
     * @return tminuteList.
     */
    public JComboBox<String>[] getTminuteList() {
        return tminuteList;
    }
}


class SizeCheckBox extends JPanel {

    /**
     * A checkbox for selecting a size.
     */
    private JCheckBox checkBox;

    /**
     * Constructor of the class SizeCheckBox
     * @param text to be setted in the checkbox
     */

    SizeCheckBox(String text) {

        setLayout(new GridLayout(1,1));
        checkBox = new JCheckBox();
        checkBox.setText(text);
        add(checkBox);

    }

    /**
     * Getter method
     * @return checkbox
     */

    JCheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * Method for getting DogSize object from a checkBox
     * @param name String checked in for each
     * @param dogSizeList Hashset controlled to get the correct DogSize object
     * @return the DogSize object
     */

    DogSize getDogSizeFromJCheckBox(String name, DogSize[] dogSizeList) {
        for (DogSize dogSize: dogSizeList) {
            if (dogSize.name().equals(name)) {
                return dogSize;
            }
        }
        return null;
    }
}
