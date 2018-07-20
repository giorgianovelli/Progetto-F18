package client.gui;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
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

public class GUIAssignmentInformationDogsitter extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 550;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private GUIAssignmentInformationDogsitter guiAssignmentInformationDogsitter;

    private GridLayout gridLayout = new GridLayout(1, 2);
    private GridLayout gridLayout2 = new GridLayout(1, 1);
    private JPanel panelOut = new JPanel(new BorderLayout());
    private JPanel panelContents = new JPanel(new BorderLayout());
    private JPanel panelAssignmentData = new JPanel(new GridLayout(7, 1));
    private JPanel panelDogs = new JPanel(gridLayout2);
    private JPanel panelReview = new JPanel(new BorderLayout());
    private JPanel panelClose = new JPanel(new BorderLayout());
    private JPanel panelButtonReview = new JPanel();

    private JPanel panelStartDate = new JPanel(gridLayout);
    private JPanel panelEndDate = new JPanel(gridLayout);
    private JPanel panelDogsitter = new JPanel(gridLayout);
    private JPanel panelMeetingPoint = new JPanel(gridLayout);
    private JPanel panelAmount = new JPanel(gridLayout);
    private JPanel panelPaymentMethod = new JPanel(gridLayout);

    private JScrollPane scrollPane = new JScrollPane(panelOut);

    private JLabel labelStartDate1 = new JLabel("Start Date: ");
    private JLabel labelEndDate1 = new JLabel("End Date: ");
    private JLabel labelDogsitter1 = new JLabel("Customer: ");
    private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ");
    private JLabel labelAmount1 = new JLabel("Amount: ");
    private JLabel labelPaymentMethod1 = new JLabel("Paymenth Method: ");

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
     * Constuctor of the class
     * @param assignment of which informations are displayed
     * @param email of the dogsitter
     * @param guiShowDogsitterAssignment GUI from which is created
     */

    GUIAssignmentInformationDogsitter(Assignment assignment, String email, GUIShowDogsitterAssignment guiShowDogsitterAssignment)  {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiShowDogsitterAssignment.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiShowDogsitterAssignment.setEnabled(true);
            }
        });

        this.email = email;
        guiAssignmentInformationDogsitter = this;

        initComponents(assignment);
    }

    /**
     * Constructor using GUIDogsitter
     *
     * @param assignment Assignment
     * @param email of the customer
     * @param guiDogSitter GUI from which is launched
     */


    GUIAssignmentInformationDogsitter(Assignment assignment, String email, GUIDogSitter guiDogSitter)  {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiDogSitter.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiDogSitter.setEnabled(true);
            }
        });

        this.email = email;
        guiAssignmentInformationDogsitter = this;

        initComponents(assignment);
    }

    /**
     * Constructor using GUIDailyAssignmentDogsitter
     *
     * @param assignment Assignment
     * @param email of the customer
     * @param guiDailyAssignmentDogsitter GUI from which is launched
     */

    GUIAssignmentInformationDogsitter(Assignment assignment, String email, GUIDailyAssignmentDogsitter guiDailyAssignmentDogsitter)  {
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        guiDailyAssignmentDogsitter.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiDailyAssignmentDogsitter.setEnabled(true);
            }
        });

        this.email = email;
        guiAssignmentInformationDogsitter = this;

        initComponents(assignment);
    }


    /**
     * Initializes graphic objects
     * @param assignment object that gives data to be displayed
     */

    public void initComponents(Assignment assignment) {
        panelOut.add(panelContents, BorderLayout.NORTH);
        panelOut.add(panelClose, BorderLayout.CENTER);

        panelContents.add(panelAssignmentData, BorderLayout.NORTH);
        panelContents.add(panelDogs, BorderLayout.CENTER);
        panelContents.add(panelReview, BorderLayout.SOUTH);

        panelAssignmentData.setBorder(BorderFactory.createTitledBorder("Summary: "));
        panelDogs.setBorder(BorderFactory.createTitledBorder("Dogs: "));
        panelReview.setBorder(BorderFactory.createTitledBorder("Review: "));
        panelButtonReview.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

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

        panelButtonReview.add(buttonReview);
        panelReview.add(labelReview, BorderLayout.WEST);
        panelReview.add(panelButtonReview, BorderLayout.EAST);

        panelClose.add(buttonClose);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20, 190, 20, 190));

        buttonClose.addActionListener(e -> guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING)));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        DogSitterProxy dogSitterProxy = new DogSitterProxy(email);

        Integer intCode = assignment.getCode();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDateStart = dateFormat.format(assignment.getDateStart());
        String strEndDate = dateFormat.format(assignment.getDateEnd());

        HashMap<Integer, Review> listReview = dogSitterProxy.getReviewList();
        CustomerProxy customerProxy = new CustomerProxy(dogSitterProxy.getCustomerEmailOfAssignment(assignment.getCode()));
        String strCustomer = dogSitterProxy.getCustomerNameOfAssignment(intCode) + " " + dogSitterProxy.getCustomerSurnameOfAssignment(intCode);
        HashSet<Dog> dogList = assignment.getDogList();
        String strMeetingPoint = assignment.printMeetingPoint();
        Double doubleAmount = customerProxy.estimatePriceAssignment(dogList, assignment.getDateStart(), assignment.getDateEnd());
        String amount = "€ " + String.format("%.2f", doubleAmount).replace(",", ".");
        String strPayment;

        if (!customerProxy.isInCashPaymentMethodOfAssignment(assignment.getCode())) {
            strPayment = "Credit card";
        } else {
            strPayment = "Cash";
        }

        for (Map.Entry<Integer, Review> entry : listReview.entrySet()) {
            if (assignment.getCode() == entry.getKey()) {
                String date = dateFormat.format(entry.getValue().getDate());
                String title = entry.getValue().getTitle();
                String vote = entry.getValue().starsRating();
                labelReview.setText("<html>" + title + "<br>" + date + "<br>" + vote + "<br/>");
                buttonReview.addActionListener(e -> new GUIShowReview(entry.getValue()).setVisible(true));
            }
        }

        if (labelReview.getText().equals("")) {
            labelReview.setText("No review");
            buttonReview.setEnabled(false);
        }

        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(strCustomer);
        labelAmount2.setText(amount);
        labelPaymentMethod2.setText(strPayment);


        int i = 1;
        for (Dog dog : dogList) {

            JPanel panelDog = new JPanel(new GridLayout(1, 1));
            JLabel tmplabel = new JLabel(i + ".     " + dog.getName());
            gridLayout2.setRows(gridLayout.getRows() + 1);
            panelDogs.add(panelDog);
            panelDog.add(tmplabel);
            i++;
        }
    }
}
