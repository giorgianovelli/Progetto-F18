package customerClient.gui;

import customerClient.CustomerProxy;
import server.User;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

public class GUISettings extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelRadioButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();

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
    private JLabel labelPaymentMethod = new JLabel("PaymentMethod:", SwingConstants.LEFT);

    private JTextField textName = new JTextField();
    private JTextField textSurname = new JTextField();
    private JTextField textStreet = new JTextField();
    private JTextField textNumber = new JTextField();
    private JTextField textCity = new JTextField();
    private JTextField textCountry = new JTextField();
    private JTextField textCap = new JTextField();
    private JTextField textPhoneNumber = new JTextField();

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    private JRadioButton cash = new JRadioButton("Cash");
    private JRadioButton  creditCard = new JRadioButton("Credit Card");

    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13","14","15","16", "17", "18", "19", "20", "21", "22", "23", "24", "25","26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<String>();


    //TODO attributi per client-server
    private CustomerProxy proxy;
    private String email;
    private User user;

    public GUISettings(String email) {
        setTitle("Account settings");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        // this.user = user;
        this.email = email;
        this.proxy = new CustomerProxy(email);
        setValues();

        initComponents();
    }


    private void initComponents() {

        panelData.setLayout(new GridLayout(9, 1, 20, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelOut.add(panelRadioButton);

        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelSurname);
        panelData.add(textSurname);
        panelData.add(labelDate);

        for(int years = 1930; years<= Calendar.getCurrentYear() ; years++) {
            years_tmp.add(years+"");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        //per riempire le jcombobox con le date corrette
        Date strDate= proxy.getDateOfBirth();
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);

        panelDate.setLayout(new GridLayout(1,3,5,5));
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
        panelAddress.setLayout(new BoxLayout(panelAddress,BoxLayout.X_AXIS));
        panelAddress.add(textStreet);
        panelAddress.add(textNumber);
        panelData.add(panelAddress);

        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);
        panelData.add(labelPaymentMethod);
        add(panelOut);

        panelRadioButton.setLayout(new GridLayout(1,0));
        panelData.add(panelRadioButton);

        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);


        //  JRadioButton  per scegliere metodo di pagamento
        cash.setMnemonic(KeyEvent.VK_C);
        cash.setActionCommand("");
        panelRadioButton.add(cash, BorderLayout.EAST);

        creditCard.setMnemonic(KeyEvent.VK_D);
        panelRadioButton.add(creditCard, BorderLayout.EAST);


        // buttongroup per far in modo che si possa selezionare SOLO un metodo di pagamento
        ButtonGroup group = new ButtonGroup();
        group.add(cash);
        group.add(creditCard);


        //TODO Listener dei JRADIOBUTTON da sistemare
      /*  ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                    if (proxy.getPaymentMethod() == null ){
                    cash.isSelected();

                    System.out.println(event.getActionCommand());

                }
                else {
                    creditCard.isSelected();
                    System.out.println(event.getActionCommand());
                }
            }
        };
        cash.addActionListener(a);
        creditCard.addActionListener(a);*/

        //TODO SELEZIONE DEI BOTTONI

        // A ItemListener for all Radio buttons
        ItemListener listener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == cash) {
                       //cosa da far fare
                    } else if (e.getSource() == creditCard) {
                        //cosa da far fare al bottone
                    }
                }
            }
        };
        cash.addItemListener(listener);
        creditCard.addItemListener(listener);


        //TODO METODO DELLA MODIFICA dei dati da SISTEMARE
        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    if (textName.getText().equals("") || textSurname.getText().equals ("") ||textCountry.getText().equals("") ||textCity.getText().equals("") ||textCap.getText().equals("") || textStreet.getText().equals("") ||textNumber.getText().equals("") ||textPhoneNumber.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);
                    }
                    // se metodo di pagamento non è selezionato
                    if(cash.isSelected() == false && creditCard.isSelected() == false){
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Payment method is not selected", "", JOptionPane.ERROR_MESSAGE);

                    }
                    else {
                        setNewValues();

                        JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        //TODO nel caso in cui non viene fatta la modifica??
                        //TODO fare in modo che il metodo di pagamento resti selezionato
                    }

                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }

            }
        };
        buttonCancel.addActionListener(registration);
        buttonConfirm.addActionListener(registration);



    }


    private void setValues() {

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

        textNumber.setText(customerAddress.getNumber());
        textNumber.setEditable(true);
        labelStreet.setLabelFor(textNumber);

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


    }


    //metodo AGGIORNARE IL DATABASE (per cambiare i valori aggiornati dall'utente nel database)
    private void setNewValues() {
        proxy.updateName(textName.getText());
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        proxy.updateSurname(textSurname.getText());
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        //todo manca come aggiornare la data nelle JCombobox

        //todo NON FUNZIONA!!! mi fa vedere la data aggiornata al giorno corrente e mi da eccezione se provo a cambiare il giorno
     /*   Date dateofBirth = new Date();
        boolean updateDate= proxy.updateDateOfBirth(dateofBirth);
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(updateDate);
        String month = dateFormatmm.format(updateDate);
        String year = dateFormatyyy.format(updateDate);

        //serve per inserire i giorni mesi e anni  nelle jcombobox penso
        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);
        System.out.println("updateDate:" +updateDate);*/

        proxy.updateAddress( textCountry.getText(), textCity.getText(), textStreet.getText(),textNumber.getText(), textCap.getText());
        textCountry.setEditable(true);
        labelCountry.setLabelFor(textCountry);
        textCity.setEditable(true);
        labelCity.setLabelFor(textCity);
        textStreet.setEditable(true);
        labelStreet.setLabelFor(textStreet);
        textNumber.setEditable(true);
        labelNumber.setLabelFor(textNumber);
        textCap.setEditable(true);
        labelCap.setLabelFor(textCap);

        proxy.updatePhoneNumber(textPhoneNumber.getText());
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);




    }


}
