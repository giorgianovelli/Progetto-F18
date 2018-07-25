package client.gui;

import client.Calendar;
import client.gui.GUIHome;
import client.gui.GUISettings;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class GUIDogSitterSettings extends GUISettings {

    private DogSitterProxy dogSitterProxy;
    private GUIDogSitterSettings guiDogSitterSettings;


    private JPanel bioPanel;
    private JPanel northPanel;
    private JPanel inPanel;

    private JLabel bioLabel;
    private JTextArea bioText;
    private JScrollPane bioScroll;

    private JLabel labelDogsNumber;
    private JComboBox<String> dogsNumber;
    private String[] numberOfDogs;

    private JRadioButton cashflag;
    private JRadioButton cashflag2;
    private JLabel labelCash;

    private JPanel centerPanel;
    private JPanel panelRadioButton;

    private JPanel availabilityPanel;

    private JPanel dogSizePanel;


    private JLabel dogSizeLabel;
    private JList dogSizeList;
    private String[] dogSize;

    private JLabel labelArea;
    private JList dogsitterAreas;
    private String[] areas;
    private HashSet<String> listArea;
    private JPanel areaListPanel;

    private AvailabilityDogSitterBox availabilityBox;

    private HashSet<DogSize> listDogSize;

    private JScrollPane scrollPane;


    /**
     * Constructor
     *
     * @param email   the user's email.
     * @param guiHome interface from where GUISettings is invoked
     */
    public GUIDogSitterSettings(String email, GUIHome guiHome) {
        super(email, guiHome);

        setSize(WIDTH + 350, HEIGHT);
        guiDogSitterSettings = this;

    }


    @Override
    protected void initComponents() {
        dogSitterProxy = new DogSitterProxy(email);


        buttonConfirm = new JButton("Confirm");
        scrollPane = new JScrollPane(panelOut);


        bioPanel = new JPanel();
        bioLabel = new JLabel("Biography:", SwingConstants.LEFT);
        bioText = new JTextArea();
        bioText.setLineWrap(true);
        bioText.setWrapStyleWord(true);
        bioScroll = new JScrollPane(bioText);

        labelCash = new JLabel("Allow cash payment:", SwingConstants.LEFT);
        cashflag = new JRadioButton("YES");
        cashflag2 = new JRadioButton("NO");
        panelRadioButton = new JPanel();


        dogSizeLabel = new JLabel("Dog Size:");
        dogSize = new String[]{"SMALL", "MEDIUM", "BIG", "GIANT"};
        dogSizeList = new JList(dogSize);


        listDogSize = new HashSet<>();

        dogSizePanel = new JPanel(new GridLayout(2, 1));
        dogSizePanel.add(dogSizeLabel);
        dogSizePanel.add(dogSizeList);


        labelArea = new JLabel("Area:");
        areas = new String[]{"GENOVA","TORINO", "BELGIOSO", "VERCELLI", "TORTONA", "PAVIA", "MILANO", "PIACENZA"};
        dogsitterAreas = new JList(areas);

        listArea = new HashSet<>();

        dogsitterAreas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                List<String> selectedValues = dogsitterAreas.getSelectedValuesList();


                for (String area : selectedValues) {
                    listArea.add(area);
                }
            }
        });



        areaListPanel = new JPanel(new GridLayout(2, 1));
        areaListPanel.add(labelArea);
        areaListPanel.add(dogsitterAreas);




        centerPanel = new JPanel();

        labelDogsNumber = new JLabel("Number of dogs:");
        numberOfDogs = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        dogsNumber = new JComboBox<>(numberOfDogs);

        availabilityBox = new AvailabilityDogSitterBox();

        availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new GridLayout(1, 1));

        availabilityPanel.setBorder(BorderFactory.createTitledBorder("Availability"));
        availabilityPanel.add(availabilityBox);

        /**
         * Panels
         */

        northPanel = new JPanel();
        inPanel = new JPanel();
        inPanel.setLayout(new GridLayout(2, 2, 10, 10));
        panelData.setLayout(new GridLayout(10, 1, 40, 5));
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
         *  Serve per fare in modo che le jcombobox di "Date of Birth" siano corrette
         */

        Date strDate = dogSitterProxy.getDateOfBirth();
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
         * others panels
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

        panelData.add(labelDogsNumber);
        panelData.add(dogsNumber);

        panelData.add(labelCash);
        panelRadioButton.setLayout(new GridLayout(1, 0));

        // buttongroup per far in modo che si possa selezionare SOLO un metodo di pagamento
        ButtonGroup group = new ButtonGroup();
        group.add(cashflag);
        group.add(cashflag2);
        cashflag.setSelected(true);

        panelRadioButton.add(cashflag);
        panelRadioButton.add(cashflag2);
        panelData.add(panelRadioButton);


        bioScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bioScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        bioPanel.setLayout(new BorderLayout());
        bioLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bioPanel.add(bioLabel, BorderLayout.NORTH);
        bioPanel.add(bioScroll, BorderLayout.CENTER);

        dogSizePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 100, 250));
        inPanel.add(dogSizePanel);
        inPanel.add(bioPanel);
        areaListPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 100, 250));
        inPanel.add(areaListPanel);

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
         *  Serve per fare in modo che le jcombobox di "ExpirationDate" siano corrette
         */
        PaymentMethod strExpirationDate = dogSitterProxy.getPaymentMethod();
        SimpleDateFormat expirationDateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");

        String expiration_Months = expirationDateFormatmm.format(strExpirationDate.getExpirationDate());
        String expiration_Years = expirationDateFormatyyyy.format(strExpirationDate.getExpirationDate());

        expirationMonth.setSelectedItem(expiration_Months);
        expirationYear.setSelectedItem(expiration_Years);

        /**
         * insertion last day of the month automatically
         */

        Date exYear = new Date();

        try {
            exYear = expirationDateFormatyyyy.parse(expirationMonth.getSelectedItem().toString()); //prende come data solo l'anno
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), exYear));



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
        panelOut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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


        /**
         * Button panels
         */
        panelButton.setLayout(new GridLayout(1, 2, 5, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(15, 200, 10, 200));
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


                    } else if (inputCap && inputAddressNumber && inputPhoneNumber) {

                        boolean inputCrediCardNumber = checkCreditCardNumber(textCreditCardNumber.getText());
                        Date inputDate = getNewExpirationDate();
                        boolean inputCvv = checkCvvNumber(textSecurityCode.getText());

                        if (inputCrediCardNumber && !(dateBeforeToday(inputDate)) && inputCvv) {

                            setNewValues();
                            JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                            guiDogSitterSettings.dispatchEvent(new WindowEvent(guiDogSitterSettings, WindowEvent.WINDOW_CLOSING));
                        }

                    }
                }
            }
        };
        buttonCancel.addActionListener(e -> guiDogSitterSettings.dispatchEvent(new WindowEvent(guiDogSitterSettings, WindowEvent.WINDOW_CLOSING)));
        buttonConfirm.addActionListener(registration);

        setValues();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }


    /**
     * insert the values content in the database
     */

    @Override
    protected void setValues() {
        String strName = dogSitterProxy.getName();
        textName.setText(strName);
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        String strSurname = dogSitterProxy.getSurname();
        textSurname.setText(strSurname);
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);


        Address dogSitterAddress = dogSitterProxy.getAddress();
        textStreet.setText(dogSitterAddress.getStreet());
        textStreet.setEditable(true);
        labelStreet.setLabelFor(textStreet);

        textStreetNumber.setText(dogSitterAddress.getNumber());
        textStreetNumber.setEditable(true);
        labelStreet.setLabelFor(textStreetNumber);

        textCountry.setText(dogSitterAddress.getCountry());
        textCountry.setEditable(true);
        labelCountry.setLabelFor(textCountry);

        textCity.setText(dogSitterAddress.getCity());
        textCity.setEditable(true);
        labelCity.setLabelFor(textCity);

        textCap.setText(dogSitterAddress.getCap());
        textCap.setEditable(true);
        labelCap.setLabelFor(textCap);

        String strPhoneNumber = dogSitterProxy.getPhoneNumber();
        textPhoneNumber.setText(strPhoneNumber);
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);

        PaymentMethod strPaymentmethod = dogSitterProxy.getPaymentMethod();
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


        boolean acceptCash = dogSitterProxy.isAcceptingCash();

        //PER SETTARE I RADIO BUTTON // non va
        cashflag = new JRadioButton("YES", acceptCash);
        cashflag2 = new JRadioButton("NO", acceptCash);
       // cashflag = dogSitterProxy.updateCashFlag();

        ButtonGroup group = new ButtonGroup();
        group.add(cashflag);
        group.add(cashflag2);
        // cashflag.getInheritsPopupMenu(acceptCash);
        // cashflag2.getSelectedObjects();


        Availability dateTimeAvailability = dogSitterProxy.getDateTimeAvailability();
        WorkingTime[] workingTimes = dateTimeAvailability.getArrayDays();
        String [] startHour = new String[7];
        String [] startMinute = new String[7];

        String[] endHour = new String[7];
        String[] endMinute = new String[7];

        for(int i = 0; i<7; i++){
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

            if(workingTimes[i].getStart() == null){
                startHour[i] = "00";
                startMinute[i] = "00";
            }else{
                startHour[i] = hourFormat.format(workingTimes[i].getStart());
                startMinute[i] = minuteFormat.format(workingTimes[i].getStart());
            }
            if(workingTimes[i].getEnd() == null){
                endHour[i] = "00";
                endMinute[i] = "00";
            }else {
                endHour[i] = hourFormat.format(workingTimes[i].getEnd());
                endMinute[i] = minuteFormat.format(workingTimes[i].getEnd());
            }

        }
        availabilityBox.setValues(startHour, startMinute, endHour, endMinute);


        HashSet<DogSize> listDogSizeProxy = new HashSet<>();
        listDogSizeProxy = dogSitterProxy.getListDogSize();
        int[] indexes = new int[listDogSizeProxy.size()];
        int i = 0;
        for (DogSize dogSize : listDogSizeProxy){
            indexes[i] = dogSize.ordinal();
            i++;
        }

        dogSizeList.setSelectedIndices(indexes);

        Area dogSitterProxyArea = dogSitterProxy.getArea();
        HashSet<String > dogsitterArea = dogSitterProxyArea.getPlaces();


        int[] areaIndexes = new int[dogsitterArea.size()];
        int j = 0;
        for (String area : dogsitterArea){
            for(int k = 0; k<areas.length; k++){
                if(area.equals(areas[k])){
                    areaIndexes[j] = k;

                }
            }
            j++;

        }


        dogsitterAreas.setSelectedIndices(areaIndexes);


        int dNumber = dogSitterProxy.getDogsNumber();

        dogsNumber.setSelectedItem(Integer.toString(dNumber));

        String strBiography = dogSitterProxy.getBiography();
        bioText.setText(strBiography);
        bioText.setEditable(true);
        bioLabel.setLabelFor(bioText);

    }

    protected void setNewValues() {

        dogSitterProxy.updateName(textName.getText().toUpperCase());
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        dogSitterProxy.updateSurname(textSurname.getText().toUpperCase());
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        /**
         * aggiorna la data di nascita
         */

        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean updateDate = dogSitterProxy.updateDateOfBirth(dateOfBirth);

        if (!(updateDate)) {
            System.out.println("Error in updating the date of birth");
        }

        dogSitterProxy.updateAddress(textCountry.getText().toUpperCase(), textCity.getText().toUpperCase(), textStreet.getText().toUpperCase(), textStreetNumber.getText().toUpperCase(), textCap.getText().toUpperCase());
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

        dogSitterProxy.updatePhoneNumber(textPhoneNumber.getText());
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);

        // aggiorna la data di scadenza
        PaymentMethod strExpirationDate = dogSitterProxy.getPaymentMethod();
        SimpleDateFormat expirationDateFormatmm = new SimpleDateFormat("MM");
        String expiration_Months = expirationDateFormatmm.format(strExpirationDate.getExpirationDate());

        Date updateExpiration = new Date();
        SimpleDateFormat expirationdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date yearExpiration = new Date();


        String strDateExpiration = Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), yearExpiration) + "/" + expirationMonth.getSelectedItem().toString() + "/" + expirationYear.getSelectedItem().toString();
        try {
            updateExpiration = expirationdateFormat.parse(strDateExpiration);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        boolean upPaymentMethod = dogSitterProxy.updatePaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText().toUpperCase(), textCreditCardOwneSurname.getText().toUpperCase(), updateExpiration, textSecurityCode.getText());
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



        if (cashflag.isSelected()) {
            dogSitterProxy.updateCashFlag(true);
        } else if (cashflag2.isSelected()) {
            dogSitterProxy.updateCashFlag(false);

        }

        Availability availability = new Availability();

        WorkingTime workingTime;
        WeekDays[] weekDays = WeekDays.values();

        Time timeStart, timeEnd;

        for(int i = 0 ; i < 7; i++){
            timeStart = Time.valueOf(availabilityBox.getFhourList()[i].getSelectedItem().toString()+":" +availabilityBox.getFminuteList()[i].getSelectedItem().toString()+":" + "00");
            timeEnd = Time.valueOf(availabilityBox.getThourList()[i].getSelectedItem().toString()+":"+availabilityBox.getTminuteList()[i].getSelectedItem().toString()+":" +"00");
            workingTime = new WorkingTime(timeStart, timeEnd);

            availability.setDayAvailability(workingTime,weekDays[i]);

        }

        dogSitterProxy.updateDateTimeAvailability(availability);

        dogSitterProxy.updateDogsNumber(Integer.parseInt(dogsNumber.getSelectedItem().toString()));

        boolean small = false;
        boolean medium = false;
        boolean big = false;
        boolean giant = false;




        dogSizeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                listDogSize = new HashSet<>();

                List<String> selectedValues = dogSizeList.getSelectedValuesList();


                for (String dogSize : selectedValues) {
                    listDogSize.add(DogSize.valueOf(dogSize));
                }



            }
        });


        for (DogSize dogSize : listDogSize){

            if(dogSize == DogSize.SMALL){
                small = true;
            }
            else if(dogSize == DogSize.MEDIUM){
                medium = true;
            }

            else if(dogSize == DogSize.BIG){
                big = true;
            }

            else if(dogSize == DogSize.GIANT){
                giant = true;
            }
        }

      //  System.out.println("taglie " + small + medium + big + giant);

        dogSitterProxy.updateListDogSize(small, medium, big, giant);

        //SELEZIONE AREE da aggiungere

        Area dogSitterProxyArea = dogSitterProxy.getArea();
        HashSet<String > dogsitterArea = dogSitterProxyArea.getPlaces();
        ArrayList<String> areasToAdd = new ArrayList<>();

        for(String area : listArea){
            if(!dogsitterArea.contains(area)){
                areasToAdd.add(area);
            }

        }

        for(String area : areasToAdd){
            dogSitterProxy.addNewPlaceArea(area);
        }






    }

}

class AvailabilityDogSitterBox extends JPanel{

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

    public AvailabilityDogSitterBox(){

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



    public void setValues(String [] startHour, String [] startMinute, String[] endHour, String [] endMinute){
        for(int i = 0; i<DAYS; i++){
            fhourList[i].setSelectedItem(startHour[i]);
            fminuteList[i].setSelectedItem(startMinute[i]);

            thourList[i].setSelectedItem(endHour[i]);
            tminuteList[i].setSelectedItem(endMinute[i]);
        }
    }
}










