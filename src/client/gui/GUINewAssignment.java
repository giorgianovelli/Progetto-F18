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
import java.util.EventListener;
import java.util.HashSet;


public class GUINewAssignment extends JFrame{

    //TODO javadoc; controllo su validità indirizzo;

    final int WIDTH = 800;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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
    private Date date;
    private String strDate;
    private String email;
    HashSet<Dog> dogList;
    HashSet<String> dogsittersMailList;
    private boolean paymentMethod;
    CustomerProxy customerProxy;

    //Others

    public static GUIChooseDogsitter guiChooseDogsitter;

    private GridLayout gridLayout = new GridLayout(1,1);

    private JLabel labelMeetingPoint = new JLabel("Choose where you would like to meet the dogsitter: ");
    private JLabel labelDogs = new JLabel("Select your dogs: ");
    private JLabel labelPayment = new JLabel("Select your payment method: ");

    private JRadioButton radioButtonCreditCard = new JRadioButton("Credit Card");
    private JRadioButton radioButtonCash = new JRadioButton("Cash");

    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");

    NewAssignmentBox newAssignmentBox;
    ArrayList<NewAssignmentCheckBox> listCheckbox = new ArrayList<NewAssignmentCheckBox>();

    NewAssignmentText country = new NewAssignmentText("Country: ");
    NewAssignmentText city = new NewAssignmentText("City: ");
    NewAssignmentText address = new NewAssignmentText("Address: ");
    NewAssignmentText cap = new NewAssignmentText("Postal Code: ");
    NewAssignmentText number = new NewAssignmentText("Number: ");



    //Costruttore

    /**
     *
     * @param date
     * @param email
     */

    public GUINewAssignment(Date date, String email, GUICustomer guiCustomer) {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
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

        this.date = date;
        this.email = email;
        strDate = dateToString(date);

        newAssignmentBox = new NewAssignmentBox(strDate);


        customerProxy = new CustomerProxy(email);


        initComponents(newAssignmentBox);

        // Action Listener del pulsante Search

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



                if (dateStart.after(dateEnd) || strDateStart.equals(strDateEnd)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date selected is wrong!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);

                } else if (dogsSelected.size() == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "No dogs selected!", "Assignment error",
                            JOptionPane.ERROR_MESSAGE);

                } else if (!radioButtonCash.isSelected() && !radioButtonCreditCard.isSelected()) {
                    JOptionPane.showMessageDialog(new JFrame(), "No payment method selected!", "Assignment error",
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

        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };

        buttonCancel.addActionListener(actionListener1);
        buttonSearch.addActionListener(actionListener);


    }




    /**
     *
     * @param newAssignmentBox
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

        // Labels


        panelLabel.add(labelMeetingPoint, BorderLayout.LINE_START);
        panelLabel2.add(labelDogs, BorderLayout.LINE_START);

        // Scroll panel

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);





    }


    /**
     *
     * @param date
     * @return
     */

    public String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String string = simpleDateFormat.format(date);
        return string;
    }
}








class NewAssignmentBox extends JPanel{


    Date dateEnd;
    JComboBox<String> tdayList;
    JComboBox<String> tmonthList;
    JComboBox<String> fyearList, tyearList;
    JComboBox<String> fhourList, thourList;
    JComboBox<String> fminuteList, tminuteList;
    JLabel fromLabel = new JLabel("From:");
    JLabel toLabel = new JLabel("To:");
    JLabel blanckLabel = new JLabel("");
    JLabel daysLabel = new JLabel("Day:");
    JLabel monthsLabel = new JLabel("Month:");
    JLabel yearsLabel = new JLabel("Year:");
    JLabel hoursLabel = new JLabel("Hour:");
    JLabel minutesLabel = new JLabel("Minute:");


    JLabel fromDayLabel = new JLabel("");
    JLabel fromMonthLabel = new JLabel("");
    JLabel fromYearLabel = new JLabel("");


    String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24","25","26","27","28","29","30","31"};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "59"};


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Costruttore NewAssignmentBox

    /**
     *
     * @param selectedDay
     */

    public NewAssignmentBox(String selectedDay){





        fhourList = new JComboBox<>(hour);
        fminuteList = new JComboBox<>(minute);
        tdayList = new JComboBox<>(day);
        tmonthList = new JComboBox<>(month);
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

        add(blanckLabel);
        add(daysLabel);
        add(monthsLabel);
        add(yearsLabel);
        add(hoursLabel);
        add(minutesLabel);

        add(fromLabel);
        add(fromDayLabel);
        add(fromMonthLabel);
        add(fromYearLabel);
        add(fhourList);
        add(fminuteList);

        add(toLabel);
        add(tdayList);
        add(tmonthList);
        add(tyearList);
        add(thourList);
        add(tminuteList);

    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Getter

    public JLabel getFromDayLabel() {
        return fromDayLabel;
    }

    public JLabel getFromMonthLabel() {
        return fromMonthLabel;
    }

    public JLabel getFromYearLabel() {
        return fromYearLabel;
    }

    public JComboBox<String> getTdayList() {
        return tdayList;
    }

    public JComboBox<String> getTmonthList() {
        return tmonthList;
    }

    public JComboBox<String> getTyearList() {
        return tyearList;
    }

    public JComboBox<String> getFhourList() {
        return fhourList;
    }

    public JComboBox<String> getThourList() {
        return thourList;
    }

    public JComboBox<String> getFminuteList() {
        return fminuteList;
    }

    public JComboBox<String> getTminuteList() {
        return tminuteList;
    }
}



class NewAssignmentText extends JPanel{
    JLabel label;
    TextField field;



    // Costruttore

    /**
     *
     * @param text
     */


    public NewAssignmentText(String text){

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        label = new JLabel(text);
        field = new TextField("",48);
        add(label, gridBagConstraints);
        add(field, gridBagConstraints);
    }

    public TextField getField() {
        return field;
    }
}



class NewAssignmentCheckBox extends JPanel {

    JCheckBox checkBox;




    //Costruttore

    /**
     *
     * @param text
     */


    public NewAssignmentCheckBox(String text) {

        setLayout(new GridLayout(1,1));
        checkBox = new JCheckBox();
        checkBox.setText(text);
        add(checkBox);

    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }


    /**
     *
     * @param name
     * @param dogList
     * @return
     */

    public Dog getDogFromJCheckBox(String name, HashSet<Dog> dogList) {
        for (Dog dog: dogList) {
            if (dog.getName().equals(name)) {
                return dog;
            }
        }
        return null;
    }
}

