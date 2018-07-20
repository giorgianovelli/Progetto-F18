package client.gui;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import server.Dog;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

 class GUIConfirmAssignment extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private GUIConfirmAssignment guiConfirmAssignment;

    private JPanel panelOut = new JPanel();
    private JPanel panelButtons = new JPanel();
    private GridLayout gridLayout2 = new GridLayout(1, 1);
    private GridLayout gridLayout = new GridLayout(1, 2);
    private JPanel panelDogs = new JPanel(gridLayout2);
    private GridLayout gridLayout3 = new GridLayout(6, 2, 0, 10);
    private JPanel panelAssignmentData = new JPanel(gridLayout3);
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    private JPanel panelStartDate = new JPanel(gridLayout);
    private JPanel panelEndDate = new JPanel(gridLayout);
    private JPanel panelDogsitter = new JPanel(gridLayout);
    private JPanel panelMeetingPoint = new JPanel(gridLayout);
    private JPanel panelAmount = new JPanel(gridLayout);
    private JPanel panelPaymentMethod = new JPanel(gridLayout);


    private JLabel labelStartDate1 = new JLabel("Start Date: ");
    private JLabel labelEndDate1 = new JLabel("End Date: ");
    private JLabel labelDogsitter1 = new JLabel("Dogsitter: ");
    private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ");
    private JLabel labelAmount1 = new JLabel("Amount: ");
    private JLabel labelPaymentMethod1 = new JLabel("Paymenth Method: ");


    private JLabel labelStartDate2 = new JLabel();
    private JLabel labelEndDate2 = new JLabel();
    private JLabel labelDogsitter2 = new JLabel();
    private JLabel labelMeetingPoint2 = new JLabel();
    private JLabel labelAmount2 = new JLabel();
    private JLabel labelPaymentMethod2 = new JLabel();

    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonConfirm = new JButton("Confirm");


    private JFrame success = new JFrame();

    private String mailDogsitter;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> dogsSelected;
    private Address meetingPoint;
    private CustomerProxy customerProxy;
    private DogSitterProxy dogSitterProxy;
    private boolean paymentMethod;


    /**
     * @param mailDogsitter       mail of the dogsitter
     * @param dateStartAssignment starting date of the assignment
     * @param dateEndAssignment   ending date of the assignment
     * @param dogsSelected        dogs selected for the assignment
     * @param meetingPoint        address where customer and dogsitter will meet
     * @param emailCustomer       mail of the customer
     * @param paymentMethod       used y the client
     */

    GUIConfirmAssignment(String mailDogsitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> dogsSelected, Address meetingPoint, String emailCustomer, boolean paymentMethod, GUIChooseDogsitter guiChooseDogsitter) {
        setTitle("Assignment summary");
        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiConfirmAssignment = this;

        this.mailDogsitter = mailDogsitter;
        this.dateStartAssignment = dateStartAssignment;
        this.dateEndAssignment = dateEndAssignment;
        this.dogsSelected = dogsSelected;
        this.meetingPoint = meetingPoint;
        this.paymentMethod = paymentMethod;
        customerProxy = new CustomerProxy(emailCustomer);
        dogSitterProxy = new DogSitterProxy(mailDogsitter);

        guiChooseDogsitter.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiChooseDogsitter.setEnabled(true);
            }
        });


        initComponents();
    }

    /**
     * Method that initializes graphic objects
     */

    private void initComponents() {

        panelOut.setLayout(new BorderLayout());
        panelOut.add(panelDogs, BorderLayout.CENTER);
        panelDogs.setBorder(BorderFactory.createTitledBorder("Dogs: "));


        panelAssignmentData.setBorder(BorderFactory.createTitledBorder("Your Assignment Summary:"));
        panelAssignmentData.add(panelStartDate);
        panelAssignmentData.add(panelEndDate);
        panelAssignmentData.add(panelDogsitter);
        panelAssignmentData.add(panelMeetingPoint);
        panelAssignmentData.add(panelAmount);
        panelAssignmentData.add(panelPaymentMethod);

        panelStartDate.add(labelStartDate1);
        panelStartDate.add(labelStartDate2);
        panelEndDate.add(labelEndDate1);
        panelEndDate.add(labelEndDate2);
        panelDogsitter.add(labelDogsitter1);
        panelDogsitter.add(labelDogsitter2);
        panelMeetingPoint.add(labelMeetingPoint1);
        panelMeetingPoint.add(labelMeetingPoint2);
        panelAmount.add(labelAmount1);
        panelAmount.add(labelAmount2);
        panelPaymentMethod.add(labelPaymentMethod1);
        panelPaymentMethod.add(labelPaymentMethod2);


        panelButtons.setLayout(new GridLayout(1, 2, 5, 0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        panelOut.add(panelAssignmentData, BorderLayout.NORTH); // Primo
        panelOut.add(panelButtons, BorderLayout.SOUTH); //Secondo

        panelButtons.add(buttonCancel);
        panelButtons.add(buttonConfirm);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDateStart = dateFormat.format(dateStartAssignment);
        String strEndDate = dateFormat.format(dateEndAssignment);
        String strMeetingPoint = printMeetingPoint();
        String strPaymentMethod;
        Double doubleAmount = customerProxy.estimatePriceAssignment(dogsSelected, dateStartAssignment, dateEndAssignment);

        if (paymentMethod) {
            strPaymentMethod = "Cash";
        } else {
            strPaymentMethod = customerProxy.getPaymentMethod().getNumber();
        }

        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(dogSitterProxy.getName() + " " + dogSitterProxy.getSurname());
        labelAmount2.setText("€ " + String.valueOf(doubleAmount));
        labelPaymentMethod2.setText(strPaymentMethod);


        int i = 1;
        for (Dog dog : dogsSelected) {

            JPanel panelDog = new JPanel(new GridLayout(1, 1));
            panelDogs.add(panelDog);
            JLabel label = new JLabel(i + ".     " + dog.getName());
            panelDog.add(label);
            gridLayout2.setRows(gridLayout2.getRows() + 1);
            i++;
        }

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerProxy.addAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, dogsSelected, meetingPoint, paymentMethod);
                JOptionPane.showMessageDialog(success = new JFrame(), "Assignment successfully confirmed!", "Assignment information",
                        JOptionPane.INFORMATION_MESSAGE);
                if (!success.isActive()) {
                    GUICustomer.guiNewAssignment.dispatchEvent(new WindowEvent(GUICustomer.guiNewAssignment, WindowEvent.WINDOW_CLOSING));
                    GUINewAssignment.guiChooseDogsitter.dispatchEvent(new WindowEvent(GUINewAssignment.guiChooseDogsitter, WindowEvent.WINDOW_CLOSING));
                    guiConfirmAssignment.dispatchEvent(new WindowEvent(guiConfirmAssignment, WindowEvent.WINDOW_CLOSING));
                }
            }
        };

        buttonCancel.addActionListener(e -> guiConfirmAssignment.dispatchEvent(new WindowEvent(guiConfirmAssignment, WindowEvent.WINDOW_CLOSING)));
        buttonConfirm.addActionListener(actionListener);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

    /**
     * @return Address object as String
     */

    private String printMeetingPoint() {
        String toReturn = "";
        for (String token : meetingPoint.toString().split(";")) {
            if (!token.isEmpty()) {
                toReturn = toReturn + "<html><br>" + token + "<html/>" + "\n";
            }
        }
        return toReturn;
    }
}