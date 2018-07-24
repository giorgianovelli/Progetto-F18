package client.gui;

import client.proxy.CustomerProxy;
import enumeration.AssignmentState;
import server.Assignment;
import server.Dog;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This GUI class shows the information of an assignment in the Customer client.
 */

public class GUIAssignmentInformationCustomer extends JFrame {

    /**
     * The frame width.
     */
    final int WIDTH = 512;

    /**
     * The frame height.
     */
    final int HEIGHT = 550;

    /**
     * The screen dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * This GUI object.
     */
    private GUIAssignmentInformationCustomer guiAssignmentInformationCustomer;

    /**
     *  Default GridLayout for panels.
     */
    private GridLayout gridLayout = new GridLayout(1, 2);

    /**
     * GridLayout that will grow in size depending on the number of the dogs.
     */
    private GridLayout gridLayout2 = new GridLayout(1, 1);

    /**
     * The most external panel.
     */
    private JPanel panelOut = new JPanel(new BorderLayout());

    /**
     * Panel containing data and dogs.
     */
    private JPanel panelContents = new JPanel(new BorderLayout());

    /**
     * Panel contaning data.
     */
    private JPanel panelAssignmentData = new JPanel(new GridLayout(8, 1));

    /**
     * Panel displaying the dog list.
     */
    private JPanel panelDogs = new JPanel(gridLayout2);

    /**
     * Panel displaying the review, if possible.
     */
    private JPanel panelReview = new JPanel(new BorderLayout());

    /**
     * Panel with buttons.
     */
    private JPanel panelClose = new JPanel(new BorderLayout());

    /**
     * Panel containing the button that opens the review.
     */
    private JPanel panelButtonReview = new JPanel();

    /**
     * Panel containg the assignment's code.
     */
    private JPanel panelCode = new JPanel(gridLayout);

    /**
     * Panel containg the assignment's starting date.
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
     * Panel containg the assignment's payment method.
     */
    private JPanel panelPaymentMethod = new JPanel(gridLayout);

    /**
     * Panel state.
     */
    private JPanel panelState = new JPanel(gridLayout);
    /**
     * ScrollPanel for scrolling
     */
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    /**
     * Label that permanently displays "Code:"
     */
    private JLabel labelCode1 = new JLabel("Code: ");

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

    private JLabel labelState = new JLabel("State: ");


    /**
     * Label that displays the assignment's code.
     */
    private JLabel labelCode2 = new JLabel();

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

    private JLabel labelState2 = new JLabel();

    /**
     * Label that displays the assignment's review, if possible.
     */
    private JLabel labelReview = new JLabel();

    /**
     * Button that shows the review of the assignment, if available.
     */
    private JButton buttonReview = new JButton("Show more");


    /**
     * Button that closes this GUI.
     */
    private JButton buttonClose = new JButton("Close");

    /**
     * Email of the customer.
     */
    private String email;


    /**
     * Constructor using GUIListAssignments
     *
     * @param a                  Assignment
     * @param email              of the customer
     * @param guiListAssignments GUI from which is launched
     */

    GUIAssignmentInformationCustomer(Assignment a, String email, GUIListAssignments guiListAssignments) {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiListAssignments.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiListAssignments.setEnabled(true);
            }
        });
        this.email = email;
        guiAssignmentInformationCustomer = this;


        initComponents(a);
    }


    /**
     * Constructor using GUICustomer
     *
     * @param a           Assignment
     * @param email       of the customer
     * @param guiCustomer GUI from which is launched
     */


    GUIAssignmentInformationCustomer(Assignment a, String email, GUICustomer guiCustomer) {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiCustomer.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiCustomer.setEnabled(true);
            }
        });
        this.email = email;
        guiAssignmentInformationCustomer = this;


        initComponents(a);
    }

    /**
     * Constructor using GUIDailyAssignments
     *
     * @param a                   Assignment
     * @param email               of the customer
     * @param guiDailyAssignments GUI from which is launched
     */


    GUIAssignmentInformationCustomer(Assignment a, String email, GUIDailyAssignments guiDailyAssignments) {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiDailyAssignments.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiDailyAssignments.setEnabled(true);
            }
        });
        this.email = email;
        guiAssignmentInformationCustomer = this;


        initComponents(a);
    }


    /**
     * Method that initalizes graphic components of the GUI
     *
     * @param a Assignment
     */

    public void initComponents(Assignment a) {

        panelOut.add(panelContents, BorderLayout.NORTH);
        panelOut.add(panelClose, BorderLayout.CENTER);

        panelContents.add(panelAssignmentData, BorderLayout.NORTH);
        panelContents.add(panelDogs, BorderLayout.CENTER);
        panelContents.add(panelReview, BorderLayout.SOUTH);

        panelAssignmentData.setBorder(BorderFactory.createTitledBorder("Summary: "));
        panelDogs.setBorder(BorderFactory.createTitledBorder("Dogs: "));
        panelReview.setBorder(BorderFactory.createTitledBorder("Review: "));
        panelButtonReview.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        panelAssignmentData.add(panelCode);
        panelAssignmentData.add(panelStartDate);
        panelAssignmentData.add(panelEndDate);
        panelAssignmentData.add(panelDogsitter);
        panelAssignmentData.add(panelMeetingPoint);
        panelAssignmentData.add(panelAmount);
        panelAssignmentData.add(panelPaymentMethod);
        panelAssignmentData.add(panelState);

        panelCode.add(labelCode1);
        panelCode.add(labelCode2);
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
        panelState.add(labelState);
        panelState.add(labelState2);

        if (a.getState() == AssignmentState.WAITING) {
            labelState2.setText("To be confirmed by the dogsitter");
        } else if (a.getState() == AssignmentState.CONFIRMED) {
            labelState2.setText("Confirmed");
        } else {
            labelState2.setText("Deleted");
        }
        /*if (a.getState() == null) {
            labelState2.setText("To be confirmed by the dogsitter");
        } else if (a.getState()) {
            labelState2.setText("Confirmed");
        } else {
            labelState2.setText("Deleted");
        }*/

        panelButtonReview.add(buttonReview);
        panelReview.add(labelReview, BorderLayout.WEST);
        panelReview.add(panelButtonReview, BorderLayout.EAST);


        //Pannello bottone Close e Action Listener

        panelClose.add(buttonClose);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20, 190, 20, 190));


        buttonClose.addActionListener(e -> guiAssignmentInformationCustomer.dispatchEvent(new WindowEvent(guiAssignmentInformationCustomer, WindowEvent.WINDOW_CLOSING)));

        //Implementazione scrollbar

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        // Dichiarazione variabili che andranno nelle JLabel


        CustomerProxy customerProxy = new CustomerProxy(email);
        Integer intCode = a.getCode();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDateStart = dateFormat.format(a.getDateStart());
        String strEndDate = dateFormat.format(a.getDateEnd());

        HashMap<Integer, Review> listReview = customerProxy.getReviewList();

        String strDogsitter = customerProxy.getDogSitterNameOfAssignment(intCode) + " " + customerProxy.getDogSitterSurnameOfAssignment(intCode);
        HashSet<Dog> dogList = a.getDogList();
        String strMeetingPoint = a.printMeetingPoint();
        Double doubleAmount = customerProxy.estimatePriceAssignment(a.getDogList(), a.getDateStart(), a.getDateEnd());              // Importo pagato o da pagare per l'appuntamento da prelevare dal DB
        String amount = "€ " + String.format("%.2f", doubleAmount).replace(",", ".");
        String strPayment = customerProxy.getPaymentMethod().getNumber();
        if (customerProxy.isInCashPaymentMethodOfAssignment(a.getCode())) {
            strPayment = "Cash";
        }

        //Passaggio delle variabili alle Jlabel che contengono i dati


        for (Map.Entry<Integer, Review> entry : listReview.entrySet()) {
            if (a.getCode() == entry.getKey()) {
                String title = entry.getValue().getTitle();
                String date = dateFormat.format(entry.getValue().getDate());
                String vote = entry.getValue().starsRating();
                labelReview.setText("<html>" + title + "<br>" + date + "<br>" + vote + "<br/>");
                buttonReview.addActionListener(e -> new GUIShowReview(entry.getValue(), guiAssignmentInformationCustomer).setVisible(true));
            }
        }

        if (labelReview.getText().equals("")) {
            labelReview.setText("No review");
            buttonReview.setEnabled(false);
        }

        labelCode2.setText(intCode.toString());
        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(strDogsitter);
        labelAmount2.setText(amount);
        labelPaymentMethod2.setText(strPayment);

        // Creazione e passaggio JLabel per i cani

        if (!(a.getState() == null)) {
            /*if (!a.getState() && !strPayment.equals("Cash")) {
                labelPaymentMethod2.setText("Refunded");
            }*/
            if (!(a.getState().equals(AssignmentState.CONFIRMED)) && !strPayment.equals("Cash")) {
                labelPaymentMethod2.setText("Refunded");
            }
        }

        int i = 1;
        for (Dog dog : dogList) {

            JPanel panelDog = new JPanel(new GridLayout(1, 1));
            JLabel tmplabel = new JLabel(i + ".     " + dog.getName());
            panelDog.add(tmplabel);
            panelDogs.add(panelDog);
            gridLayout2.setRows(gridLayout.getRows() + 1);
            i++;
        }

    }

}
