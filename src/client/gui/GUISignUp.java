package client.gui;

//import com.sun.xml.internal.bind.v2.runtime.Name; //TODO da problemi
import client.Calendar;
import client.proxy.CustomerProxy;
//import javafx.scene.layout.BorderRepeat; //TODO da problemi se non commentato
import client.proxy.DogSitterProxy;
import server.User;
import server.bank.PaymentMethod;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GUISignUp extends JFrame{
    final int WIDTH = 600;
    final int HEIGHT = 650;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );


    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelRadioButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelExpiration = new JPanel();
    private JPanel panelPayment = new JPanel();

    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);
    private JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);
    private JLabel labelEmail = new JLabel("Email:", SwingConstants.LEFT);
    private JLabel labelPassword = new JLabel("Password:", SwingConstants.LEFT);
    private JLabel labelConfirmPassword = new JLabel("Confirm Password:", SwingConstants.LEFT);
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
    private JTextField textEmail = new JTextField();
    private JPasswordField textPassword = new JPasswordField();
    private JPasswordField textConfirmPassword = new JPasswordField();
    private JTextField textStreet = new JTextField();
    private JTextField textNumber = new JTextField();
    private JTextField textCity = new JTextField();
    private JTextField textCountry = new JTextField();
    private JTextField textCap = new JTextField();
    private JTextField textPhoneNumber = new JTextField();

    private JButton buttonCustomerConfirm = new JButton("Next");
   // private JButton buttonDogSitterConfirm = new JButton("Continue Registration as DogSitter");
    private JButton buttonCancel = new JButton("Cancel");
    //todo per Date of birth
    private Date dateofBirth = new Date();

    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;

    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13","14","15","16", "17", "18", "19", "20", "21", "22", "23", "24", "25","26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<>();

    //todo valori per payment method

    private JLabel labelCreditCardOwnerName = new JLabel("Name of the credit card holder:", SwingConstants.LEFT);
    private JLabel labelCreditCardNumber = new JLabel("16-digit Credit card number:", SwingConstants.LEFT);
    private JLabel labelExpirationDate = new JLabel("Expiration Date:", SwingConstants.LEFT);
    private JLabel labelSecurityCode = new JLabel("Security code:", SwingConstants.LEFT);
    private JLabel labelCrediCardOwnerSurname = new JLabel("Owner Surname:", SwingConstants.LEFT);
    private JTextField textCreditCardOwneSurname = new JTextField();
    private JLabel labelAmount = new JLabel("Amount:", SwingConstants.LEFT);
    private JTextField textAmount = new JTextField();

    private JTextField textPaymentMethod = new JTextField();
    private JTextField textCreditCardOwnerName = new JTextField();
    private JTextField textCreditCardNumber = new JTextField();
    //private JComboBox<String> expirationDay;
    private JComboBox<String> expirationMonth;
    private JComboBox<String> expirationYear;
    private JTextField textSecurityCode = new JTextField();

    private String[] expirationMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] expirationYears = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    private JTextField textExpirationDays = new JTextField();


    //TODO attributi per client-server
  //  private String email;
    private CustomerProxy proxy;
    private DogSitterProxy dogSitterProxy;


//______________________________________________________________________________________________________________________________________________________________________________

    /**
     * Constructor
     */

    public GUISignUp() {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        proxy = new CustomerProxy(textEmail.getText());
       // dogSitterProxy = new DogSitterProxy(textEmail.getText());


        initComponents();
    }

//______________________________________________________________________________________________________________________________________________________________________________


    private void initComponents()  {

        /**
         * Panels
         */

        panelData.setLayout(new GridLayout(11, 1,40,5));
        panelData.setBorder(BorderFactory.createTitledBorder("FIRST STEP_Customer Fields: "));
        panelPayment.setBorder(BorderFactory.createTitledBorder("Credit Card information: "));
        panelPayment.setLayout(new GridLayout(5, 1,40,5));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelPayment, BorderLayout.CENTER);
        panelOut.add(panelButton, BorderLayout.SOUTH);
       // panelOut.add(panelRadioButton);


        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelSurname);
        panelData.add(textSurname);
        panelData.add(labelDate);

        //-----------------------------------------------------------------------------------

        /**
         * JCOMBOBOX di DATE OF BIRTH
         */

        for(int years = 1930; years<= Calendar.getCurrentYear() ; years++) {
            years_tmp.add(years+"");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        //-----------------------------------------------------------------------------------

        /**
         * others panels
         */

        panelDate.setLayout(new GridLayout(1,3,5,5));
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
        panelAddress.setLayout(new BoxLayout(panelAddress,BoxLayout.X_AXIS));
        panelAddress.add(textStreet);
        panelAddress.add(textNumber);
        panelData.add(panelAddress);

        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);

        //-----------------------------------------------------------------------------------
        /**
         * TODO  PAYMENT METHOD
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

        panelExpiration.setLayout(new GridLayout(1, 3, 5, 5));
        panelExpiration.add(textExpirationDays);
        panelExpiration.add(expirationMonth);
        panelExpiration.add(expirationYear);
        panelPayment.add(panelExpiration);

        panelPayment.add(labelSecurityCode);
        panelPayment.add(textSecurityCode);
       /* panelData.add(labelAmount);
        panelData.add(textAmount);*/

        add(panelOut);


        //TODO DA SISTEMARE I BOTTONI
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        // panelButton.setLayout(new GridBagLayout());
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonCustomerConfirm, BorderLayout.SOUTH);
       // panelButton.add(buttonDogSitterConfirm, BorderLayout.SOUTH);

        // contentPanel.setLayout(new GridLayout(infoPanel.length,1, 5,5));



        //-----------------------------------------------------------------------------------

        /**
         *  TODO METODO DELLA MODIFICA dei dati da SISTEMARE
         */
        // cotrollo se l'inserimento dati è andato a buon fine oppure no

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Next")) {

                    if (textName.getText().equals("") || textSurname.getText().equals("") || textCountry.getText().equals("") || textCity.getText().equals("") || textCap.getText().equals("") || textStreet.getText().equals("") || textNumber.getText().equals("") || textPhoneNumber.getText().equals("")
                            || textCreditCardOwnerName.getText().equals("") || textCreditCardOwneSurname.getText().equals("") || textCreditCardNumber.getText().equals("") || textSecurityCode.getText().equals("") ) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    } else {

                        boolean add = addCustomerValues();

                        if (add) {
                            JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            //GUICustomerLabel guiCustomerLabel = new GUICustomerLabel(textEmail.getText());
                           // guiCustomerLabel.setVisible(true);
                        }

                    }

                }
                

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    //JOptionPane.showMessageDialog(new JFrame(), "are you sure you want to leave the registration?", "", JOptionPane.YES_NO_OPTION);

                    //dispose();
                    System.exit(0);  //todo esco oppure torno alla schermata di login??
                }

            }
        };
        buttonCancel.addActionListener(registration);
        buttonCustomerConfirm.addActionListener(registration);
       // buttonDogSitterConfirm.addActionListener(registration);


    }

//______________________________________________________________________________________________________________________________________________________________________________

    /**
     *  metodi per inserire le tuple nel database
     */

    private boolean addCustomerValues() {
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
         * DATA DI SCADENZA
         */
        //  Date dateOfExp = new Date();
        //  Date payExpiration = new Date();
        Date ex_Year = new Date();


        PaymentMethod strExpirationDate = new PaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText(), textCreditCardOwneSurname.getText(), ex_Year, textSecurityCode.getText(), 0.0);

        SimpleDateFormat expirationDateFormatmm = new SimpleDateFormat("MM");
        String expiration_Months = expirationDateFormatmm.format(strExpirationDate.getExpirationDate());


        /**
         * TODO inserimento ultimo giorno del mese in modo automatico NON FUNZIONA PRENDE SOLO "31"
         */
        SimpleDateFormat expirationDateFormatyyyy = new SimpleDateFormat("yyyy");
        String expiration_Years = expirationDateFormatyyyy.format(strExpirationDate.getExpirationDate());

        try{
            ex_Year = expirationDateFormatyyyy.parse(expiration_Years); //prende come data solo l'anno
        } catch (ParseException e){
            e.printStackTrace();
        }

        String expiration_Days = Integer.toString(Calendar.getNDayOfMonth(Integer.parseInt(expiration_Months), ex_Year));
        // String expiration_Days = Integer.toString(Calendar.getNDayofMonth(Integer.parseInt(expiration_Months), dateOfExp));


        /**
         * stampa ultimo giorno del mese
         */
        textExpirationDays.setText(expiration_Days);
        labelExpirationDate.setLabelFor(textExpirationDays);

        /**
         * generazione casuale di amount
         */

        Double max = 500.0;
        Double min = 50.0;
        Random rand = new Random();
        Double amount = Math.round((min + (max - min) * rand.nextDouble()) * 100d) / 100d;


        PaymentMethod paymentMethod = new PaymentMethod(textCreditCardNumber.getText(), textCreditCardOwnerName.getText(), textCreditCardOwneSurname.getText(), ex_Year, textSecurityCode.getText(), amount);
        Address address = new Address(textCountry.getText(), textCity.getText(), textStreet.getText(), textNumber.getText(), textCap.getText());

        String strPassword = String.valueOf(textPassword.getPassword());

        return proxy.customerSignUp(textEmail.getText(), textName.getText(), textSurname.getText(), strPassword, textPhoneNumber.getText(), dateOfBirth2, address, paymentMethod);


    }













}
