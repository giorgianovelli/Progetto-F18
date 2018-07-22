package client.gui;



import client.proxy.CustomerProxy;
import server.Dog;
import server.places.Address;

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


public class GUINewAssignment extends JFrame{

    final int WIDTH = 800;
    final int HEIGHT = 600;

    //Panels

    private JPanel panelOut = new JPanel();
    private JPanel panelNoButtons = new JPanel();
    private JPanel panelBox = new JPanel();
    private JPanel panelCombo = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelLabel = new JPanel();
    private JPanel panelLabel2 = new JPanel();
    private JPanel panelButtons = new JPanel();
    private JPanel panelDogs = new JPanel();
    private JPanel panelPayment = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(panelOut);
    private HashSet<Dog> dogList;
    private HashSet<String> dogsittersMailList;
    private boolean paymentMethod = true;
    private CustomerProxy customerProxy;

    //Others

    static GUIChooseDogsitter guiChooseDogsitter;

    private GridLayout gridLayout = new GridLayout(1,1);

    private JLabel labelMeetingPoint = new JLabel("Choose where you would like to meet the dogsitter: ");
    private JLabel labelDogs = new JLabel("Select your dogs: ");
    private JLabel labelPayment = new JLabel("Select your payment method: ");

    private JRadioButton radioButtonCreditCard = new JRadioButton("Credit Card");
    private JRadioButton radioButtonCash = new JRadioButton("Cash");

    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");

    private NewAssignmentBox newAssignmentBox;
    private ArrayList<NewAssignmentCheckBox> listCheckbox = new ArrayList<>();

    private NewAssignmentText country = new NewAssignmentText("Country: ");
    private NewAssignmentText city = new NewAssignmentText("City: ");
    private NewAssignmentText address = new NewAssignmentText("Address: ");
    private NewAssignmentText cap = new NewAssignmentText("Postal Code: ");
    private NewAssignmentText number = new NewAssignmentText("Number: ");

    /**
     * Constructor of the class GUINewAssignment
     * @param date Selected date for the new assignment
     * @param email of the customer
     */


     GUINewAssignment(Date date, String email, GUICustomer guiCustomer) {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        guiCustomer.setEnabled(false);
        GUINewAssignment guiNewAssignment = this;


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiCustomer.setEnabled(true);
            }
        });


        String strDate = dateToString(date);

        newAssignmentBox = new NewAssignmentBox(strDate);


        customerProxy = new CustomerProxy(email);


        initComponents(newAssignmentBox);

        /*

        Action Listener del pulsante Search:

        Controlla che non ci siano errori nella compilazione dei campi,
        dopodichè attraverso il metodo search, restituisce i dogsitter disponibili rispettando
        i campi compilati

        */

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fromDay = String.valueOf(newAssignmentBox.getFromDayLabel().getText());
                String fromMonth = String.valueOf(newAssignmentBox.getFromMonthLabel().getText());
                String fromYear = String.valueOf(newAssignmentBox.getFromYearLabel().getText());
                String fromHour = String.valueOf(newAssignmentBox.getFhourList().getSelectedItem());
                String fromMinute = String.valueOf(newAssignmentBox.getFminuteList().getSelectedItem());
                String day = String.valueOf(newAssignmentBox.getTdayList().getSelectedItem());
                String month = String.valueOf(newAssignmentBox.getTmonthList().getSelectedItem());
                String year = String.valueOf(newAssignmentBox.getTyearList().getSelectedItem());
                String toHour = String.valueOf(newAssignmentBox.getThourList().getSelectedItem());
                String toMinute = String.valueOf(newAssignmentBox.getTminuteList().getSelectedItem());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date dateStart = new Date();
                Date dateEnd = new Date();
                String strDateStart = fromDay + "/" + fromMonth + "/" + fromYear + " " + fromHour + ":" + fromMinute;
                String strDateEnd = day + "/" + month + "/" + year + " " + toHour + ":" + toMinute;

                try {
                    dateStart = simpleDateFormat.parse(strDateStart);
                    dateEnd = simpleDateFormat.parse(strDateEnd);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                String countryText = String.valueOf(country.getField().getText().toUpperCase());
                String cityText = String.valueOf(city.getField().getText().toUpperCase());
                String capText = String.valueOf(cap.getField().getText().toUpperCase());
                String addressText = String.valueOf(address.getField().getText().toUpperCase());
                String numberText = String.valueOf(number.getField().getText().toUpperCase());

                Address meetingPoint = new Address(countryText, cityText, addressText, numberText, capText);
                HashSet<Dog> dogsSelected = new HashSet<>();

                for (NewAssignmentCheckBox newAssignmentCheckBox: listCheckbox) {
                    if(newAssignmentCheckBox.getCheckBox().isSelected()) {
                        dogsSelected.add(newAssignmentCheckBox.getDogFromJCheckBox(newAssignmentCheckBox.getCheckBox().getText(), dogList));
                    }
                }

                if (radioButtonCash.isSelected()) {
                    paymentMethod = true;
                } else if (radioButtonCreditCard.isSelected()) {
                    paymentMethod = false;

                }

                Date todayDate = new Date();
                if (dateStart.after(dateEnd) || strDateStart.equals(strDateEnd) || ((dateStart.getTime() - todayDate.getTime())) < 86400000) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);


                } else if (dogsSelected.size() == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "No dogs selected!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);

                } else switch (month){

                        case("02"): {
                            if (Integer.parseInt(year) % 4 != 0) {
                                if (day.equals("29") || day.equals("30") || day.equals("31")) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                            JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }

                            if(Integer.parseInt(year) % 4 == 0) {
                                if (day.equals("30") || day.equals("31")) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                            JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }
                        }

                        case ("04"): {
                            if (day.equals("31")) {
                                JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }

                        case ("06"): {
                            if (day.equals("31")) {
                                JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }

                        case ("09"): {
                            if (day.equals("31")) {
                                JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }

                        case ("11"): {
                            if (day.equals("31")) {
                                JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }

                        default: {
                            dogsittersMailList = customerProxy.search(dateStart, dateEnd, meetingPoint, dogsSelected, paymentMethod);

                            if (dogsittersMailList.isEmpty()) {
                                JOptionPane.showMessageDialog(new JFrame(), "Sorry, we couldn't find any dogsitter!", "Assignment error",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {

                                guiChooseDogsitter = new GUIChooseDogsitter(dogsittersMailList, dateStart, dateEnd, dogsSelected, meetingPoint, paymentMethod, email, guiNewAssignment);
                                guiChooseDogsitter.setVisible(true);
                            }
                        }
                    }
                }
        };

        buttonCancel.addActionListener(e -> guiNewAssignment.dispatchEvent(new WindowEvent(guiNewAssignment, WindowEvent.WINDOW_CLOSING)));
        buttonSearch.addActionListener(actionListener);

    }

    /**
     * Method that initalizes graphic components of the GUI
     * @param newAssignmentBox Box for selecting dates
     */


    private void initComponents(NewAssignmentBox newAssignmentBox) {

        //Setting layout dei panel

        panelOut.setLayout(new BorderLayout());
        panelNoButtons.setLayout(new BorderLayout());
        panelNoButtons.setBorder(BorderFactory.createTitledBorder("Complete each field to look for a dogsitter: "));
        panelBox.setLayout(new BoxLayout(panelBox, 1));
        panelCombo.setLayout(new GridLayout(1, 1));
        panelAddress.setLayout(new GridLayout(5, 1, 30, 20));
        panelLabel.setLayout(new BorderLayout());
        panelLabel2.setLayout(new BorderLayout());
        panelDogs.setLayout(gridLayout);
        panelDogs.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelPayment.setLayout(new GridLayout(1,2));
        panelButtons.setLayout(new GridLayout(1, 2, 5, 0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        // Aggiunta dei pannelli al pannello più esterno

        panelOut.add(panelNoButtons, BorderLayout.NORTH); // Primo
        panelOut.add(panelButtons, BorderLayout.SOUTH); //Secondo

        //panelNoButtons: primo pannello

        panelNoButtons.add(panelBox, BorderLayout.NORTH);
        panelNoButtons.add(panelDogs, BorderLayout.CENTER);
        panelNoButtons.add(panelPayment, BorderLayout.SOUTH);

        //PanelBox (fa parte del panelNoButtons)

        Dimension dimension = new Dimension(0, 20);
        panelBox.add(panelCombo);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelAddress);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel2);

        // panelDogs (fa parte del panelNoButtons)

        dogList = customerProxy.getDogList();

        for (Dog dog: dogList) {
            NewAssignmentCheckBox dogCheckBox = new NewAssignmentCheckBox(dog.getName());
            listCheckbox.add(dogCheckBox);
            panelDogs.add(dogCheckBox);
            gridLayout.setRows(gridLayout.getRows() + 1);
        }

        // Secondo pannello

        panelPayment.add(labelPayment);
        panelButtons.add(buttonCancel);
        panelButtons.add(buttonSearch);

        // Pannello ComboBox

        panelCombo.add(newAssignmentBox);

        // Pannello barre testuali

        panelAddress.add(country);
        panelAddress.add(city);
        panelAddress.add(cap);
        panelAddress.add(address);
        panelAddress.add(number);

        country.getField().setText(customerProxy.getAddress().getCountry());
        city.getField().setText(customerProxy.getAddress().getCity());
        cap.getField().setText(customerProxy.getAddress().getCap());
        address.getField().setText(customerProxy.getAddress().getStreet());
        number.getField().setText(customerProxy.getAddress().getNumber());

        // Pannello Metodo di pagamento

        panelPayment.add(radioButtonCreditCard);
        panelPayment.add(radioButtonCash);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonCash);
        group.add(radioButtonCreditCard);
        radioButtonCreditCard.setSelected(true);

        // Labels

        panelLabel.add(labelMeetingPoint, BorderLayout.LINE_START);
        panelLabel2.add(labelDogs, BorderLayout.LINE_START);

        // Scroll panel

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
    }


    /**
     * Method for converting object Date to String
     * @param date to be converted
     * @return String converted
     */

    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
}


/**
 * Class NewAssignmentBox is a graphic class that contains JComboBox-es and JLabels for
 * selecting date and hour of the Assignment
 */

class NewAssignmentBox extends JPanel{



    private JComboBox<String> tdayList;
    private JComboBox<String> tmonthList;
    private JComboBox<String> tyearList;
    private JComboBox<String> fhourList, thourList;
    private JComboBox<String> fminuteList, tminuteList;


    private JLabel fromDayLabel = new JLabel("");
    private JLabel fromMonthLabel = new JLabel("");
    private JLabel fromYearLabel = new JLabel("");


    /**
     * Constructor for NewAssignmentBox
     * @param selectedDay String which rapresents selected day in the GUICustomer
     */

     NewAssignmentBox(String selectedDay){


         String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
         fhourList = new JComboBox<>(hour);
         String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "59"};
         fminuteList = new JComboBox<>(minute);
         String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
         tdayList = new JComboBox<>(day);
         String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
         tmonthList = new JComboBox<>(month);
         String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
         tyearList = new JComboBox<>(year);
        thourList = new JComboBox<>(hour);
        tminuteList = new JComboBox<>(minute);


        String[] selectedDaySplitted = selectedDay.split("/");

        fromDayLabel.setText(selectedDaySplitted[0]);
        fromMonthLabel.setText(selectedDaySplitted[1]);
        fromYearLabel.setText(selectedDaySplitted[2]);

        for (int i = 0; i < 32; i++) {
            if (day[i].equals(selectedDaySplitted[0])) {
                tdayList.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < 13; i++) {
            if (month[i].equals(selectedDaySplitted[1])) {
                tmonthList.setSelectedIndex(i);
                break;
            }
        }


        for (int i = 0; i < 24; i++) {
            if (year[i].equals(selectedDaySplitted[2])) {
                tyearList.setSelectedIndex(i);
                break;
            }
        }

        thourList.setSelectedIndex(1);

        fhourList.setLightWeightPopupEnabled(false);
        fminuteList.setLightWeightPopupEnabled(false);
        tdayList.setLightWeightPopupEnabled(false);
        tmonthList.setLightWeightPopupEnabled(false);
        tyearList.setLightWeightPopupEnabled(false);
        thourList.setLightWeightPopupEnabled(false);
        tminuteList.setLightWeightPopupEnabled(false);

        setLayout(new GridLayout(3, 6, 10, 10));

        JLabel blanckLabel = new JLabel("");
        add(blanckLabel);
        JLabel daysLabel = new JLabel("Day:");
        add(daysLabel);
        JLabel monthsLabel = new JLabel("Month:");
        add(monthsLabel);
        JLabel yearsLabel = new JLabel("Year:");
        add(yearsLabel);
        JLabel hoursLabel = new JLabel("Hour:");
        add(hoursLabel);
        JLabel minutesLabel = new JLabel("Minute:");
        add(minutesLabel);

        JLabel fromLabel = new JLabel("From:");
        add(fromLabel);
        add(fromDayLabel);
        add(fromMonthLabel);
        add(fromYearLabel);
        add(fhourList);
        add(fminuteList);

        JLabel toLabel = new JLabel("To:");
        add(toLabel);
        add(tdayList);
        add(tmonthList);
        add(tyearList);
        add(thourList);
        add(tminuteList);

    }

    /**
     * Getter methods of class NewAssignmentBox
     * @return the object
     */

    JLabel getFromDayLabel() {
        return fromDayLabel;
    }

    JLabel getFromMonthLabel() {
        return fromMonthLabel;
    }

    JLabel getFromYearLabel() {
        return fromYearLabel;
    }

    JComboBox<String> getTdayList() {
        return tdayList;
    }

    JComboBox<String> getTmonthList() {
        return tmonthList;
    }

    JComboBox<String> getTyearList() {
        return tyearList;
    }

    JComboBox<String> getFhourList() {
        return fhourList;
    }

    JComboBox<String> getThourList() {
        return thourList;
    }

    JComboBox<String> getFminuteList() {
        return fminuteList;
    }

    JComboBox<String> getTminuteList() {
        return tminuteList;
    }
}

/**
 * Class NewAssignmentText contains a JLabel and a TextField.
 * It is used for the Meeting Point form that needs to be compiled
 * in order to look for a dogsitter.
 */


class NewAssignmentText extends JPanel{
    private TextField field;

    /**
     * Constructor of NewAssignmentText
     * @param text to be setted in TextField
     */

    NewAssignmentText(String text){

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        JLabel label = new JLabel(text);
        field = new TextField("",48);
        add(label, gridBagConstraints);
        add(field, gridBagConstraints);
    }

    TextField getField() {
        return field;
    }
}

/**
 * Class NewAssignmentCheckBox is used in the dogs form to generate
 * the list of dogs owned by the customer.
 * The names of the dogs are near a checkbox that is used to select them.
 */

class NewAssignmentCheckBox extends JPanel {

    private JCheckBox checkBox;

    /**
     * Constructor of the class NewAssignmentCheckBox
     * @param text to be setted in the checkbox
     */

    NewAssignmentCheckBox(String text) {

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
     * Method for getting Dog object from a checkBox
     * @param name String checked in for each
     * @param dogList Hashset controlled to get the correct Dog object
     * @return the Dog object
     */

    Dog getDogFromJCheckBox(String name, HashSet<Dog> dogList) {
        for (Dog dog: dogList) {
            if (dog.getName().equals(name)) {
                return dog;
            }
        }
        return null;
    }
}

