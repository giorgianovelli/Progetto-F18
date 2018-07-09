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

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelRadioButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelPayment = new JPanel(); //da eliminare
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
    private JLabel labelPaymentMethod = new JLabel("PaymentMethod:", SwingConstants.LEFT); //da eliminare

    //TODO label per titolare carta di credito
    private JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);
    private JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);
    private JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT); //data di scadenza
    private JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);

    private JTextField textName = new JTextField();
    private JTextField textSurname = new JTextField();
    private JTextField textStreet = new JTextField();
    private JTextField textNumber = new JTextField();
    private JTextField textCity = new JTextField();
    private JTextField textCountry = new JTextField();
    private JTextField textCap = new JTextField();
    private JTextField textPhoneNumber = new JTextField();

    //TODO textfield per titolare carta di credito
    private JTextField textCreditCardOwnerName = new JTextField();
    private JTextField textCreditCardNumber = new JTextField();
    private JTextField textSecurityCode = new JTextField();

    //todo textfiel aggiunti per rispettare parametri metodo "updatePaymentMethod" in CUSTOMERPROXY
    private JTextField textCreditCardOwneSurname = new JTextField();
    private Date dateExpiration = new Date();

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");


   /* private JRadioButton cash = new JRadioButton("Cash");
    private JRadioButton creditCard = new JRadioButton("Credit Card");*/

    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private JComboBox<String> expirationMonth;
    private JComboBox<String> expirationYear;


    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<String>();


    //  private String[] expirationDays = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    private JTextField textExpirationDays = new JTextField();


    // attributi per client-server
    private CustomerProxy proxy;
    private String email;
    private User user;    //todo controllare tutte le variabili inutilizzate ed eliminarle



//______________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
     * @param email: reference to the user
     */


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

        //todo METODO
        setValues();

        initComponents();
    }

//______________________________________________________________________________________________________________________________________________________________




    private void initComponents() {
        /**
         * Panel
         */
        panelData.setLayout(new GridLayout(13, 1, 20, 10));
        panelData.setBorder(BorderFactory.createTitledBorder("Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        panelOut.add(panelRadioButton);

        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelSurname);
        panelData.add(textSurname);
        panelData.add(labelDate);

        //-----------------------------------------------------------------------------------

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

        //-----------------------------------------------------------------------------------

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
        panelAddress.add(textNumber);
        panelData.add(panelAddress);

        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);
       /* panelData.add(labelPaymentMethod);

        panelRadioButton.setLayout(new GridLayout(1, 0));
        panelData.add(panelRadioButton);*/

        panelData.add(labelCreditCardOwnerName);
        panelData.add(textCreditCardOwnerName);
        panelData.add(labelCreditCardNumber);
        panelData.add(textCreditCardNumber);
        panelData.add(labelExpirationDate);


        //-----------------------------------------------------------------------------------

        /**
         * JCOMBOBOX di EXPIRATION DATE
         */

        expirationMonth = new JComboBox<>(expirationMonths);
        expirationYear = new JComboBox<>(expirationYears);

        /**
         *  Serve per fare in modo che le jcombobox di "ExpirationDate" siano corrette
         *
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

        try{
            exYear = expirationDateFormatyyyy.parse(expiration_Years); //prende come data solo l'anno
        } catch (ParseException e){
            e.printStackTrace();
        }

        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), exYear));

        /**
         * stampa il giorno sulla label nell'interfaccia
         */

        textExpirationDays.setText(expiration_Days);
        textExpirationDays.setEditable(false);
        labelExpirationDate.setLabelFor(textExpirationDays);



        //-----------------------------------------------------------------------------------

        /**
         * PANEL DI EXPIRATION DATE per sistemare le jcombobox
         * e PANEL dei BOTTONI
         */

        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));

        // panelExpiration.add(exDate());
        panelExpiration.add(textExpirationDays);
        panelExpiration.add(expirationMonth);
        panelExpiration.add(expirationYear);
        panelData.add(panelExpiration);

        panelData.add(labelSecurityCode);
        panelData.add(textSecurityCode);
        // panelData.add(panelPayment);
        add(panelOut);
        panelButton.setLayout(new GridLayout(1, 2, 5, 5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);

        //-----------------------------------------------------------------------------------

        /**
         * TODO METODO DELLA MODIFICA dei dati da SISTEMARE
         */

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {

                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textNumber.getText().equals("") || textPhoneNumber.getText().equals("")|| textCreditCardOwnerName.getText().equals("")|| textCreditCardNumber.getText().equals("")|| textSecurityCode.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    }

                    //todo da ELIMINARE  se metodo di pagamento non è selezionato
                    //  if (cash.isSelected() == false && creditCard.isSelected() == false) {
                    //     JOptionPane.showMessageDialog(new JFrame(), "ERROR! Payment method is not selected", "", JOptionPane.ERROR_MESSAGE);

                    // }
                    else {
                        setNewValues();
                        JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);

                        dispose();

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


//______________________________________________________________________________________________________________________________________________________________________________

    /**
     *  METODO PER INSERIRE I VALORI CONTENUTI NEL DATABASE
     */

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

        PaymentMethod strPaymentmethod = proxy.getPaymentMethod();
        textCreditCardOwnerName.setText(strPaymentmethod.getName());
        textCreditCardOwnerName.setEditable(true);
        labelCreditCardOwnerName.setLabelFor(textCreditCardOwnerName);

        textCreditCardNumber.setText(strPaymentmethod.getNumber());
        textCreditCardNumber.setEditable(true);
        labelCreditCardNumber.setLabelFor(textCreditCardNumber);


        textSecurityCode.setText(strPaymentmethod.getCvv());
        textSecurityCode.setEditable(true);
        labelSecurityCode.setLabelFor(textSecurityCode);



    }


//____________________________________________________________________________________________________________________________________________________________________________


    /**
     *  METODO PER  AGGIORNARE IL DATABASE (per cambiare i valori aggiornati dall'utente nel database)
     */

    private void setNewValues() {
        proxy.updateName(textName.getText());
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        proxy.updateSurname(textSurname.getText());
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        //aggiorna la data di nascita
        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean updateDate = proxy.updateDateOfBirth(dateOfBirth);

        if(!(updateDate)){
            System.out.println("Error in updating the date of birth");
        }

        proxy.updateAddress(textCountry.getText(), textCity.getText(), textStreet.getText(), textNumber.getText(), textCap.getText());
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