package client.gui;

//import com.sun.xml.internal.bind.v2.runtime.Name; //TODO da problemi
import client.Calendar;
import client.proxy.CustomerProxy;
//import javafx.scene.layout.BorderRepeat; //TODO da problemi se non commentato
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GUISignUp extends JFrame{
    final int WIDTH = 700;
    final int HEIGHT = 700;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );


    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelRadioButton = new JPanel();
    private JPanel panelDate = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel contentPanel = new JPanel();

    private JScrollPane scrollPanel = new JScrollPane(panelOut);

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

    private JButton buttonCustomerConfirm = new JButton("Confirm Registration as Cutomer");
    private JButton buttonDogSitterConfirm = new JButton("Confirm Registration as DogSitter");
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
    private User user;   //todo eliminare variabili inutilizzate


    public GUISignUp() {
        setTitle("CaniBau (Sign up)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.proxy = new CustomerProxy(email);
        this.email = email;

        initComponents();
    }


    private void initComponents()  {

        panelData.setLayout(new GridLayout(12, 1, 20, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("First Step: "));

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
     /*   Date strDate= proxy.getDateOfBirth();
        // System.out.println(strDate);  //
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.addItem(day);
        monthList.addItem(month);
        yearList.addItem(year);*/

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
        panelData.add(labelPaymentMethod);
        add(panelOut);

        panelRadioButton.setLayout(new GridLayout(1,0));
        panelData.add(panelRadioButton);

        //TODO DA SISTEMARE I BOTTONI
        panelButton.setLayout(new GridLayout(1, 2,5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 90, 10, 90));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonCustomerConfirm, BorderLayout.SOUTH);
        panelButton.add(buttonDogSitterConfirm, BorderLayout.SOUTH);

       // contentPanel.setLayout(new GridLayout(infoPanel.length,1, 5,5));





        //TODO METODO DELLA MODIFICA dei dati da SISTEMARE
        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm Registration as Cutomer")) {

                    addCustomerValues();
                    //System.out.println(addCustomerValues());

                    //TODO CONTROLLARE CHE I CAMPI PASSWORD E CONFIRM PASSWORD SIANO UGUALI

                    JOptionPane.showConfirmDialog(new JFrame(), "Please confirm your registration", "Second Step:", JOptionPane.OK_CANCEL_OPTION);
                    dispose();
                    GUILogin guiLogin = new GUILogin();
                    guiLogin.setVisible(true);
                  //  textEmail.setText("");
                  //  textPassword.setText("");


                }

                if (registrationAe.getActionCommand().equals("Confirm Registration as DogSitter")) {

                      //TODO   addDogSitterValues();

                    //  as Dogsitter: aprire un altro frame solo con le label dogsitter
                    GUIDogSitterLabel guiDogSitterLabel = new GUIDogSitterLabel();
                    guiDogSitterLabel.setVisible(true);

                    ActionListener registrationDogSitter = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                          //  MainFrame frame = new MainFrame();
                          //  frame.setVisible(true);

                          // GUIDogSitterLabel

                        }
                    };


                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    //dispose();
                    System.exit(0);  //todo esco oppure torno alla schermata di login??
                }

            }
        };
        buttonCancel.addActionListener(registration);
        buttonCustomerConfirm.addActionListener(registration);
        buttonDogSitterConfirm.addActionListener(registration);


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
        ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if (proxy.getPaymentMethod() == null ){
                    cash.isSelected();

                }
                else {
                    creditCard.isSelected();

                }

            }
        };
        cash.addActionListener(a);
        creditCard.addActionListener(a);


        //TODO DA SISTEMARE
     /*  scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);*/



    }

    //metodi per inserire le tuple nel database
    //todo ho bisogno dei metodi set in CustomerProxy
    private void addCustomerValues() {
      //  String s = ;
      //  proxy.setName(textName.setText());
      /*  String strName = proxy.setName();
        textName.setText(strName);
        textName.setEditable(true);
        labelName.setLabelFor(textName);

        System.out.println(strName);*/

        //todo MOSTRA IL CONTENUTO DEL DATABASE
      /*  String strName = proxy.getName();
        textName.setText(strName);
        textName.setEditable(true);
        labelName.setLabelFor(textName);*/


       //todo MODIFICA IL DATABASE
      /*  proxy.updateName(textName.getText());
        textName.setEditable(true);
        labelName.setLabelFor(textName);*/


    }



    private void addDogSitterValues(){



    }







}
