package client.gui;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import enumeration.AssignmentState;
import server.Assignment;
import server.Dog;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This GUI class shows the information of an assignment in the Customer client.
 */

public class GUIAssignmentInformationDogsitter extends JFrame {

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
    private GUIAssignmentInformationDogsitter guiAssignmentInformationDogsitter;

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
    private JPanel panelClose = new JPanel(new GridLayout(1,3,10,10));

    /**
     * Panel containing the button that opens the review.
     */
    private JPanel panelButtonReview = new JPanel();

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

    private JPanel panelState = new JPanel(gridLayout);

    /**
     * ScrollPanel for scrolling
     */
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    /**
     * Label that permanently displays "Start Date:"
     */
    private JLabel labelStartDate1 = new JLabel("Start Date: ");

    /**
     * Label that permanently displays "End Date:"
     */
    private JLabel labelEndDate1 = new JLabel("End Date: ");

    /**
     * Label that permanently displays "Customer:"
     */
    private JLabel labelDogsitter1 = new JLabel("Customer: ");


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
     * Label displaying "State:".
     */
    private JLabel labelState1 = new JLabel("State:");

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
     * Button used to confirm the assignment.
     */
    private JButton buttonConfirm = new JButton("Confirm");

    /**
     * Button used to delete the assignment.
     */
    private JButton buttonDelete = new JButton("Delete");

    /**
     * Email of the dogsitter.
     */
    private String email;

    /**
     * GUI that creates this object.
     */
    private GUIShowDogsitterAssignment guiShowDogsitterAssignment = null;

    /**
     * GUI that can create this object.
     */
    private GUIDailyAssignmentDogsitter guiDailyAssignmentDogsitter = null;

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
        this.guiShowDogsitterAssignment = guiShowDogsitterAssignment;
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
        this.guiDailyAssignmentDogsitter = guiDailyAssignmentDogsitter;
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
        panelAssignmentData.add(panelState);

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
        panelState.add(labelState1);
        panelState.add(labelState2);

        panelButtonReview.add(buttonReview);
        panelReview.add(labelReview, BorderLayout.WEST);
        panelReview.add(panelButtonReview, BorderLayout.EAST);

        panelClose.add(buttonClose);
        panelClose.add(buttonConfirm);
        panelClose.add(buttonDelete);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        buttonClose.addActionListener(e -> guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING)));

        DogSitterProxy dogSitterProxy = new DogSitterProxy(email);
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.yesButtonText", "Yes");
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = (JOptionPane.showConfirmDialog(null, "Confirm assignment?", "Confirm", JOptionPane.YES_NO_OPTION));
                if (action == JOptionPane.YES_OPTION) {
                    dogSitterProxy.updateAssignmentState(assignment.getCode(), AssignmentState.CONFIRMED);
                    if (!(guiDailyAssignmentDogsitter == null)) {
                        guiDailyAssignmentDogsitter.dispatchEvent(new WindowEvent(guiDailyAssignmentDogsitter, WindowEvent.WINDOW_CLOSING));
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));
                    } else if (!(guiShowDogsitterAssignment == null)) {
                        guiShowDogsitterAssignment.dispatchEvent(new WindowEvent(guiShowDogsitterAssignment, WindowEvent.WINDOW_CLOSING));
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));

                    } else {
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
        };

        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = (JOptionPane.showConfirmDialog(null, "Delete assignment?","Delete",JOptionPane.YES_NO_OPTION));
                if (action == JOptionPane.YES_OPTION) {
                    dogSitterProxy.updateAssignmentState(assignment.getCode(), AssignmentState.DELETED);
                    if (!(guiDailyAssignmentDogsitter == null)) {
                        guiDailyAssignmentDogsitter.dispatchEvent(new WindowEvent(guiDailyAssignmentDogsitter, WindowEvent.WINDOW_CLOSING));
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));
                    } else if (!(guiShowDogsitterAssignment == null)) {
                        int i;
                        guiShowDogsitterAssignment.dispatchEvent(new WindowEvent(guiShowDogsitterAssignment, WindowEvent.WINDOW_CLOSING));
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));

                    } else {
                        guiAssignmentInformationDogsitter.dispatchEvent(new WindowEvent(guiAssignmentInformationDogsitter, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
        };

        buttonConfirm.addActionListener(actionListener1);
        buttonDelete.addActionListener(actionListener2);
        if (assignment.getState().equals(AssignmentState.WAITING)) {
            buttonDelete.setEnabled(true);
            buttonConfirm.setEnabled(true);
        } else {
            buttonDelete.setEnabled(false);
            buttonConfirm.setEnabled(false);
        }

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        if (assignment.getState().equals(AssignmentState.WAITING)) {
            labelState2.setText("To be confirmed");
        } else if (assignment.getState().equals(AssignmentState.DELETED)) {
            labelState2.setText("Deleted");
        } else {
            labelState2.setText("Confirmed");
        }

        /*if (assignment.getState() == null) {
            labelState2.setText("To be confirmed");
        } else if (!assignment.getState()) {
            labelState2.setText("Deleted");
        } else {
            labelState2.setText("Confirmed");
        }*/



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
        String strCredit = "";

        if (!customerProxy.isInCashPaymentMethodOfAssignment(assignment.getCode())) {
            strPayment = "Credit card";
            strCredit = "Credit card";
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

        if ((assignment.getState().equals(AssignmentState.DELETED)) && !strCredit.equals("Cash")) {
            labelPaymentMethod2.setText("Customer refunded");
        }
    }

    /*
    public boolean isInNext24Hours(Date dateStart) {
        long ds = dateStart.getTime();
        Date dateToday = new Date();
        long dt = dateToday.getTime();
       if ((ds - dt) < 86400000) {
           return true;
       }
       return false;
    }
    */
}
