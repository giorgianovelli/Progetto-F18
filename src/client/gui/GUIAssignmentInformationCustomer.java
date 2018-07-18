package client.gui;

import client.proxy.CustomerProxy;
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


public class GUIAssignmentInformationCustomer extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 550;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private GUIAssignmentInformationCustomer guiAssignmentInformationCustomer;


    private GridLayout gridLayout = new GridLayout(1, 2);
    private GridLayout gridLayout2 = new GridLayout(1, 1);
    private JPanel panelOut = new JPanel(new BorderLayout());
    private JPanel panelContents = new JPanel(new BorderLayout());
    private JPanel panelAssignmentData = new JPanel(new GridLayout(7, 1));
    private JPanel panelDogs = new JPanel(gridLayout2);
    private JPanel panelReview = new JPanel(new BorderLayout());
    private JPanel panelClose = new JPanel(new BorderLayout());
    private JPanel panelButtonReview = new JPanel();

    private JPanel panelCode = new JPanel(gridLayout);
    private JPanel panelStartDate = new JPanel(gridLayout);
    private JPanel panelEndDate = new JPanel(gridLayout);
    private JPanel panelDogsitter = new JPanel(gridLayout);
    private JPanel panelMeetingPoint = new JPanel(gridLayout);
    private JPanel panelAmount = new JPanel(gridLayout);
    private JPanel panelPaymentMethod = new JPanel(gridLayout);

    private JScrollPane scrollPane = new JScrollPane(panelOut);


    private JLabel labelCode1 = new JLabel("Code: ");
    private JLabel labelStartDate1 = new JLabel("Start Date: ");
    private JLabel labelEndDate1 = new JLabel("End Date: ");
    private JLabel labelDogsitter1 = new JLabel("Dogsitter: ");
    private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ");
    private JLabel labelAmount1 = new JLabel("Amount: ");
    private JLabel labelPaymentMethod1 = new JLabel("Paymenth Method: ");


    private JLabel labelCode2 = new JLabel();
    private JLabel labelStartDate2 = new JLabel();
    private JLabel labelEndDate2 = new JLabel();
    private JLabel labelDogsitter2 = new JLabel();
    private JLabel labelMeetingPoint2 = new JLabel();
    private JLabel labelAmount2 = new JLabel();
    private JLabel labelPaymentMethod2 = new JLabel();

    private JLabel labelReview = new JLabel();
    private JButton buttonReview = new JButton("Show more");

    private JButton buttonClose = new JButton("Close");

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
        String amount = String.format("%.2f", doubleAmount).replace(",", ".");
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
                buttonReview.addActionListener(e -> new GUIShowReview(entry.getValue()).setVisible(true));
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
