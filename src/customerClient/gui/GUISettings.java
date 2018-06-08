package customerClient.gui;

import customerClient.CustomerProxy;
import server.User;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

public class GUISettings extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 500;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private User user;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelAvailability = new JPanel();
    private JPanel panelButton = new JPanel();

    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelSurname = new JLabel("Surname:", SwingConstants.LEFT);
    //TODO labelEmail non s'ha da fare!
    private JLabel labelEmail = new JLabel("Email:", SwingConstants.LEFT);
    private JLabel labelPhoneNumber = new JLabel("Phone number:", SwingConstants.LEFT);
    private JLabel labelDate = new JLabel("Date of birth (dd/MM/yyyy):", SwingConstants.LEFT);
    private JLabel labelAddress = new JLabel("Address:", SwingConstants.LEFT);
    private JLabel labelPaymentMethod = new JLabel("PaymentMethod:", SwingConstants.LEFT);
    //Label solo per dogSitter
    private JLabel labelArea = new JLabel("Area:", SwingConstants.LEFT);
    private JLabel labelDogBreed = new JLabel("Dog Breed:", SwingConstants.LEFT);
    private JLabel labelDogsNumber = new JLabel("Number of dogs:", SwingConstants.LEFT);
    private JLabel labelAvailability = new JLabel("Availability:", SwingConstants.LEFT);
    private JLabel labelBiography = new JLabel("Biography:", SwingConstants.LEFT);

    private JTextField textName = new JTextField(SwingConstants.RIGHT);
    private JTextField textSurname = new JTextField();
    private JFormattedTextField textDate = new JFormattedTextField();
    private JTextField textAddress = new JTextField();
    private JTextField textEmail = new JTextField();
    private JTextField textPhoneNumber = new JTextField();
    private JTextField textPaymentMethod = new JTextField();
    //TextField solo per dogSitter
    private JTextField textArea = new JTextField();
    private JTextField textDogBreed = new JTextField();
    private JTextField textDogsNumber = new JTextField();
    private JTextField textAvailability = new JTextField();
    private JTextField textBiography = new JTextField("immetti la tua biografia", 5);

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");

    /*private JRadioButton cash = new JRadioButton("Cash");
    private JRadioButton  creditCard = new JRadioButton("Credit Card");*/

    private JCheckBox cash = new JCheckBox("Cash");
    private JCheckBox  creditCard = new JCheckBox("Credit Card");


    //TODO attributi per client-server
    private CustomerProxy proxy;
    private String email;





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

        panelData.setLayout(new GridLayout(8, 1, 30, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("Customer Fields: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelAvailability);
        panelOut.add(panelButton, BorderLayout.SOUTH);

        panelData.add(labelName);
        panelData.add(textName);
        panelData.add(labelSurname);
        panelData.add(textSurname);
        panelData.add(labelDate);
        panelData.add(textDate);
        panelData.add(labelAddress);
        panelData.add(textAddress);
        panelData.add(labelEmail);
        panelData.add(textEmail);
        panelData.add(labelPhoneNumber);
        panelData.add(textPhoneNumber);
        panelData.add(labelPaymentMethod);
        add(panelOut);

        panelButton.setLayout(new GridLayout(1, 2));
        panelButton.setBorder(BorderFactory.createEmptyBorder(70, 10, 20, 10));
        panelButton.add(buttonCancel, BorderLayout.SOUTH);
        panelButton.add(buttonConfirm, BorderLayout.SOUTH);


        //TODO METODO DELLA MODIFICA dei dati da SITEMARE
        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    setNewValues();
                    //da sistemare
                }
                if (registrationAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }

            }
        };
        buttonCancel.addActionListener(registration);
        buttonConfirm.addActionListener(registration);



        //  JRadioButton  per scegliere metodo di pagamento
        cash.setMnemonic(KeyEvent.VK_C);
        cash.setActionCommand("");
        panelData.add(cash, BorderLayout.EAST);

        creditCard.setMnemonic(KeyEvent.VK_D);
        panelData.add(creditCard, BorderLayout.EAST);


        // buttongroup per far in modo che si possa selezionare SOLO un metodo di pagamento
        ButtonGroup group = new ButtonGroup();
        group.add(cash);
        group.add(creditCard);


        //TODO Listener dei JRADIOBUTTON da sistemare
        ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if (proxy.getCustomerPaymentMethod() == null ){
                    cash.isSelected();

                }
                else {
                    creditCard.isSelected();
                    //todo messaggio di prova da eliminare
                    //JOptionPane.showMessageDialog(null,"You select : "+creditCard.getText());
                }

            }
        };
        cash.addActionListener(a);
        creditCard.addActionListener(a);



        //TODO values DogSitter creare un metodo che cancelli i campi del DogSitter nel caso in cui si effettui il login come Customer
        textArea.setText("");
        textArea.setEditable(true);
        labelArea.setLabelFor(textArea);

        textDogBreed.setText("");
        textDogBreed.setEditable(true);
        labelDogBreed.setLabelFor(textDogBreed);

        textDogsNumber.setText("");
        textDogsNumber.setEditable(true);
        labelDogsNumber.setLabelFor(textDogsNumber);

        textAvailability.setText("");
        textAvailability.setEditable(true);
        labelAvailability.setLabelFor(textAvailability);

        textBiography.setText("Immettere la propria biografia");
        textBiography.setEditable(true);
        labelBiography.setLabelFor(textBiography);

        //todo panelDate solo per dogSitter
       /* panelData.add(labelArea);
        panelData.add(textArea);
        panelData.add(labelDogBreed);
        panelData.add(textDogBreed);
        panelData.add(labelDogsNumber);
        panelData.add(textDogsNumber);
        panelData.add(labelAvailability);
        panelData.add(textAvailability);
        panelData.add(labelBiography);
        panelData.add(textBiography);*/


    }

    //TODO Metodo da sistemare
    private void setValues() {

        String strName = proxy.getCustomerName();
        textName.setText(strName);
        textName.setEditable(true);
        labelName.setLabelFor(textName);


        String strSurname = proxy.getCustomerSurname();
        textSurname.setText(strSurname);
        textSurname.setEditable(true);
        labelSurname.setLabelFor(textSurname);

        String strPhoneNumber = proxy.getCustomerPhoneNumber();
        textPhoneNumber.setText(strPhoneNumber);
        textPhoneNumber.setEditable(true);
        labelPhoneNumber.setLabelFor(textPhoneNumber);

        //TODO ERRORI
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDate =  dateFormat.format(proxy.getCustomerDateOfBirth());
        textDate.setText(strDate);
        textDate.setEditable(true);
        labelDate.setLabelFor(textDate);


        /*//String strEmail = proxy.get();
        textEmail.setText(user.getEmail());
        textEmail.setEditable(true);
        labelEmail.setLabelFor(textEmail);*/



        Address customerAddress = proxy.getCustomerAddress();
        System.out.println(customerAddress.toString());
        textAddress.setText(customerAddress.toString()); //ERRORE :Siccome Address è di tipo Date prende tutta la stringa
        textAddress.setEditable(true);
        labelAddress.setLabelFor(textAddress);

        /*// String strPaymentMethod = proxy.getCustomerPaymentMethod();
        textPaymentMethod.setText(String.valueOf(user.getPaymentMethod()));//Da sistemare
        textPaymentMethod.setEditable(true);
        labelPaymentMethod.setLabelFor(textPaymentMethod);*/

        //TODO Possibile metodo per mostrare le label in base a chi ha effettuato l'accesso (se customer o dogsitter)
      /*  ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Login as customer")){

                    panelData.add(labelArea).setVisible(false);
                    panelData.add(textArea).setVisible(false);

                    /*textArea.removeAll();
                    labelArea.setVisible(false);
                    setVisible(true);
                }
                if (ae.getActionCommand().equals("Login as dogsitter")) {
                    panelData.add(labelArea);
                    panelData.add(textArea);
                    panelData.add(labelDogBreed);
                    panelData.add(textDogBreed);
                    panelData.add(labelDogsNumber);
                    panelData.add(textDogsNumber);
                    panelData.add(labelAvailability);
                    panelData.add(textAvailability);
                    panelData.add(labelBiography);
                    panelData.add(textBiography);



                }

            }
        };

    }*/


    }

    //TODO metodo che conterrà i nuovi valori
    private void setNewValues() {

        boolean updName = proxy.updateCustomerName(getName());
        textName.setText(String.valueOf(updName));
        textName.setEditable(true);
        labelName.setLabelFor(textName);
    }


}
