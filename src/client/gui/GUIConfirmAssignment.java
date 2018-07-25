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

/**
 * This class allows the creation of a new assignment with a dog sitter.
 */
 class GUIConfirmAssignment extends JFrame {
     /**
      * The frame width.
      */
     final int WIDTH = 512;

     /**
      * The frame height.
      */
     final int HEIGHT = 512;

     /**
      * This GUI object.
       */
     private GUIConfirmAssignment guiConfirmAssignment;

     /**
      * The most external panel.
      */
    private JPanel panelOut = new JPanel();

     /**
      * Panel that contains the buttons.
      */
    private JPanel panelButtons = new JPanel();

     /**
      * Gridlayout that will grow in size.
      */
    private GridLayout gridLayout2 = new GridLayout(1, 1);

     /**
      * Default GridLayout for the panels.
      */
    private GridLayout gridLayout = new GridLayout(1, 2);

     /**
      * Panel that displays the doglist.
      */
    private JPanel panelDogs = new JPanel(gridLayout2);

     /**
      * GridLayout for the panelAssignmentData.
      */
    private GridLayout gridLayout3 = new GridLayout(6, 2, 0, 10);

     /**
      * Panel that displays the data.
      */
    private JPanel panelAssignmentData = new JPanel(gridLayout3);

     /**
      * ScrollPanel for scrolling.
      */
    private JScrollPane scrollPane = new JScrollPane(panelOut);

     /**
      * Panel that displays starting date.
      */
    private JPanel panelStartDate = new JPanel(gridLayout);

     /**
      * Panel containg the assignment's ending date.
      */
    private JPanel panelEndDate = new JPanel(gridLayout);

     /**
      * Panel containg the assignment's dogsttier.
      */
    private JPanel panelDogsitter = new JPanel(gridLayout);

     /**
      * Panel containg the assignment's meeting point.
      */
    private JPanel panelMeetingPoint = new JPanel(gridLayout);

     /**
      * Panel containg the assignment's amount.
      */
     private JPanel panelAmount = new JPanel(gridLayout);

     /**
      * Panel containg the assignment's amount.
      */
    private JPanel panelPaymentMethod = new JPanel(gridLayout);


     /**
      * Label that permanently displays "Start Date:"
      */
     private JLabel labelStartDate1 = new JLabel("Start Date: ");

     /**
      * Label that permanently displays "End Date:"
      */
     private JLabel labelEndDate1 = new JLabel("End Date: ");

     /**
      * Label that permanently displays "Dogsitter:"
      */
     private JLabel labelDogsitter1 = new JLabel("Dogsitter: ");


     /**
      * Label that permanently displays "Meeting Point:"
      */
     private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ");

     /**
      * Label that permanently displays "Amount:"
      */
     private JLabel labelAmount1 = new JLabel("Amount: ");

     /**
      * Label that permanently displays "Payment Method:"
      */
     private JLabel labelPaymentMethod1 = new JLabel("Payment Method: ");


     /**
      * Label that displays the assignment's starting date.
      */
     private JLabel labelStartDate2 = new JLabel();

     /**
      * Label that displays the assignment's enduing date.
      */
     private JLabel labelEndDate2 = new JLabel();

     /**
      * Label that displays the assignment's dogsitter.
      */
     private JLabel labelDogsitter2 = new JLabel();

     /**
      * Label that displays the assignment's meeting point.
      */
     private JLabel labelMeetingPoint2 = new JLabel();

     /**
      * Label that displays the assignment's amount.
      */
     private JLabel labelAmount2 = new JLabel();

     /**
      * Label that displays the assignment's payment method.
      */
     private JLabel labelPaymentMethod2 = new JLabel();

     /**
      * Button for closing the GUI.
      */
    private JButton buttonCancel = new JButton("Cancel");

     /**
      * Button for confirming the assignment.
      */
    private JButton buttonConfirm = new JButton("Confirm");

     /**
      * Frame that appears when you complete the assignment.
      */
    private JFrame success = new JFrame();

     /**
      * Mail of the dogsitter.
      */
    private String mailDogsitter;

     /**
      * Starting date of the assignment.
      */
    private Date dateStartAssignment;

     /**
      * Ending date of the assignment.
      */
    private Date dateEndAssignment;

     /**
      * List of the selected dogs.
      */
    private HashSet<Dog> dogsSelected;

     /**
      * Meeting point of the assignment.
      */
    private Address meetingPoint;

     /**
      * The customer proxy.
      */
    private CustomerProxy customerProxy;

     /**
      * The dogsitter proxy.
      */
    private DogSitterProxy dogSitterProxy;

     /**
      * Boolean for checking if the payment method is "cash" or "credit card".
      */
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

                Date todayDate = new Date();
                if (!strPaymentMethod.equals("Cash") && ((customerProxy.getPaymentMethod().getAmount() < doubleAmount) || customerProxy.getPaymentMethod().getExpirationDate().before(todayDate))) {
                    JOptionPane.showMessageDialog(null, "Payment failed, check your credit card!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    customerProxy.addAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, dogsSelected, meetingPoint, paymentMethod);
                    JOptionPane.showMessageDialog(success = new JFrame(), "Assignment successfully created!", "Assignment information",
                            JOptionPane.INFORMATION_MESSAGE);
                    if (!success.isActive()) {
                        GUICustomer.guiNewAssignment.dispatchEvent(new WindowEvent(GUICustomer.guiNewAssignment, WindowEvent.WINDOW_CLOSING));
                        GUINewAssignment.guiChooseDogsitter.dispatchEvent(new WindowEvent(GUINewAssignment.guiChooseDogsitter, WindowEvent.WINDOW_CLOSING));
                        guiConfirmAssignment.dispatchEvent(new WindowEvent(guiConfirmAssignment, WindowEvent.WINDOW_CLOSING));
                    }
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