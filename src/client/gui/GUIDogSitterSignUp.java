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

public class GUIDogSitterSignUp extends GUISignUp {

    private DogSitterProxy dogSitterProxy;
    private GUIDogSitterSignUp guiDogsitterSignUp;



    private JPanel bioPanel;
    private JPanel northPanel;
    private JPanel inPanel;

    private JLabel bioLabel ;
    private JTextArea bioText;
    private JScrollPane bioScroll ;

    private JLabel labelDogsNumber;
    private JComboBox<String> dogsNumber;
    private String[] numberOfDogs;

    private JRadioButton cashflag;
    private JRadioButton cashflag2;
    private JLabel labelCash;

    private JLabel areaLabel;
    private JTextField areaField;

    private JPanel centerPanel;
    private JPanel panelRadioButton;

    private JPanel availabilityPanel;

    private JPanel dogSizePanel;


    private JLabel dogSizeLabel;
    private JList dogSizeList;
    private String[] dogSize;

    private AvailabilityBox availabilityBox;


    private HashSet<DogSize > listDogSize;



    private JScrollPane scrollPane;






    public GUIDogSitterSignUp(GUILogin guiLogin){
        super(guiLogin);

        setSize(WIDTH + 350, HEIGHT);

        guiDogsitterSignUp = this;
    }

    @Override
    protected void initComponents() {
        dogSitterProxy = new DogSitterProxy(textEmail.getText());

        buttonCustomerConfirm = new JButton("Confirm");
        scrollPane = new JScrollPane(panelOut);

        bioPanel = new JPanel();
        bioLabel = new JLabel("Biography:",SwingConstants.LEFT);
        bioText = new JTextArea();
        bioScroll = new JScrollPane(bioText);

        labelCash = new JLabel("Allow cash payment:",SwingConstants.LEFT);
        cashflag = new JRadioButton("YES");
        cashflag2 = new JRadioButton("NO");
        panelRadioButton = new JPanel();

        areaLabel = new JLabel("Area:");
        areaField = new JTextField();

        dogSizeLabel = new JLabel("Dog Size:");
        dogSize = new String[]{"SMALL", "MEDIUM", "BIG", "GIANT"};
        dogSizeList = new JList(dogSize);


        listDogSize = new HashSet<>();

        dogSizeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                List<String>  selectedValues = dogSizeList.getSelectedValuesList();


                for (String dogSize : selectedValues){
                    listDogSize.add(DogSize.valueOf(dogSize));
                }

            }
        });



        dogSizePanel = new JPanel(new GridLayout(2,1));
        dogSizePanel.add(dogSizeLabel);
        dogSizePanel.add(dogSizeList);

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

    @Override
    protected void checkAddCustomerValues() {
        boolean inputPassword = changePasswordFields(Password, confirmPassword);
        boolean inputCap = checkCapNumber(textCap.getText());
        boolean inputAddressNumber = checkAddressNumber(textStreetNumber.getText());
        boolean inputPhoneNumber = checkPhoneNumber(textPhoneNumber.getText());

        if(inputPassword && inputCap && inputAddressNumber && inputPhoneNumber){

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


        return dogSitterProxy.dogSitterSignUp(textEmail.getText().toUpperCase(), textName.getText().toUpperCase(), textSurname.getText().toUpperCase(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod, area , listDogSize, Integer.parseInt(dogsNumber.getSelectedItem().toString()), bioText.getText().toUpperCase(), dateTimeAvailability, acceptCash);



    }




}

class AvailabilityBox extends JPanel{

    private final int DAYS = 7;
    private JLabel[] dayLabel;

    private JPanel[] fTimeBox, tTimeBox, contentPanel;
    private JPanel outPanel = new JPanel();
    private JPanel tagPanel;
    private JLabel[] tagLabel;
    private WeekDays[] weekDays = WeekDays.values();
    private JComboBox<String> [] fhourList;
    private JComboBox<String> [] thourList;
    private JComboBox<String> [] fminuteList;
    private JComboBox<String> [] tminuteList;

    private String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] minute = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "59"};

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

    public JComboBox<String>[] getFhourList() {
        return fhourList;
    }

    public JComboBox<String>[] getThourList() {
        return thourList;
    }

    public JComboBox<String>[] getFminuteList() {
        return fminuteList;
    }

    public JComboBox<String>[] getTminuteList() {
        return tminuteList;
    }
}
