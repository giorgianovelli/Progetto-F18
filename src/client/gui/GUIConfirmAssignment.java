package customerClient.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import server.Dog;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class GUIConfirmAssignment extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelOut = new JPanel();
    private JPanel panelButtons = new JPanel();
    private JPanel panelAssignmentData = new JPanel();
    private GridLayout gridLayout = new GridLayout(8, 2, 0, 10);
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    private JLabel labelStartDate1 = new JLabel("Start Date: ", SwingConstants.CENTER);
    private JLabel labelEndDate1 = new JLabel("End Date: ", SwingConstants.CENTER);
    private JLabel labelDogsitter1 = new JLabel("Dogsitter: ", SwingConstants.CENTER);
    private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ", SwingConstants.CENTER);
    private JLabel labelAmount1 = new JLabel("Amount: ", SwingConstants.CENTER);
    private JLabel labelPaymentMethod1 = new JLabel("Paymenth Method: ", SwingConstants.CENTER);
    private JLabel labelDogs1 = new JLabel("Dogs: ", SwingConstants.CENTER);
    private JLabel labelEmpty = new JLabel("\t");

    private JLabel labelStartDate2 = new JLabel();
    private JLabel labelEndDate2 = new JLabel();
    private JLabel labelDogsitter2= new JLabel();
    private JLabel labelMeetingPoint2 = new JLabel();
    private JLabel labelAmount2 = new JLabel();
    private JLabel labelPaymentMethod2 = new JLabel();

    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonConfirm = new JButton("Confirm");


    public JFrame success = new JFrame();

    private String mailDogsitter;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> dogsSelected;
    private Address meetingPoint;
    private CustomerProxy customerProxy;
    private String emailCustomer;
    private Double doubleAmount;
    private boolean paymentMethod;


    /**
     *
     * @param mailDogsitter
     * @param dateStartAssignment
     * @param dateEndAssignment
     * @param dogsSelected
     * @param meetingPoint
     * @param emailCustomer
     * @param paymentMethod
     */

    public GUIConfirmAssignment(String mailDogsitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> dogsSelected, Address meetingPoint, String emailCustomer, boolean paymentMethod) {
        setTitle("Assignment summary");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.mailDogsitter = mailDogsitter;
        this.dateStartAssignment = dateStartAssignment;
        this.dateEndAssignment = dateEndAssignment;
        this.dogsSelected = dogsSelected;
        this.meetingPoint = meetingPoint;
        this.emailCustomer = emailCustomer;
        this.paymentMethod = paymentMethod;
        customerProxy = new CustomerProxy(emailCustomer);

        initComponents();
    }

    /**
     *
     */

    private void initComponents() {

        panelOut.setLayout(new BorderLayout());
        panelAssignmentData.setLayout(gridLayout);
        panelAssignmentData.setBorder(BorderFactory.createTitledBorder("Your Assignment Summary:"));
        panelAssignmentData.add(labelStartDate1);
        panelAssignmentData.add(labelStartDate2);
        panelAssignmentData.add(labelEndDate1);
        panelAssignmentData.add(labelEndDate2);
        panelAssignmentData.add(labelDogsitter1);
        panelAssignmentData.add(labelDogsitter2);
        panelAssignmentData.add(labelMeetingPoint1);
        panelAssignmentData.add(labelMeetingPoint2);
        panelAssignmentData.add(labelAmount1);
        panelAssignmentData.add(labelAmount2);
        panelAssignmentData.add(labelPaymentMethod1);
        panelAssignmentData.add(labelPaymentMethod2);
        panelAssignmentData.add(labelDogs1);
        panelAssignmentData.add(labelEmpty);
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
        String strDogs = printDogNames();
        String[] strDogsSplitted = strDogs.split("\n");
        String strPaymentMethod;
        doubleAmount = customerProxy.estimatePriceAssignment(dogsSelected, dateStartAssignment, dateEndAssignment);

        if (paymentMethod) {
            strPaymentMethod = "Cash";
        } else {
            strPaymentMethod = customerProxy.getPaymentMethod().getNumber();
        }

        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(mailDogsitter);
        labelAmount2.setText(String.valueOf(doubleAmount));
        labelPaymentMethod2.setText(strPaymentMethod);

        int i = 1;
        for (String token: strDogsSplitted) {
            if (!token.isEmpty()) {
                JLabel tmpLabel1 = new JLabel("[" + i + "]", SwingConstants.CENTER);
                JLabel tmplabel2 = new JLabel(token);
                gridLayout.setRows(gridLayout.getRows() + 1);
                panelAssignmentData.add(tmpLabel1);
                panelAssignmentData.add(tmplabel2);
                i++;
            }
        }

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerProxy.addAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, dogsSelected, meetingPoint, paymentMethod);
                JOptionPane.showMessageDialog(success = new JFrame(), "Assignment successfully confirmed!", "Assignment information",
                        JOptionPane.INFORMATION_MESSAGE);
                if (!success.isActive()) {
                    GUICustomer.guiNewAssignment.dispose();
                    GUINewAssignment.guiChooseDogsitter.dispose();
                    dispose();

                }
            }
        };

        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };

        buttonCancel.addActionListener(actionListener1);
        buttonConfirm.addActionListener(actionListener);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

    /**
     *
     * @return
     */

    public String printMeetingPoint() {
        String toReturn = "";
        for (String token: meetingPoint.toString().split(";")) {
            if (!token.isEmpty()) {
                toReturn = toReturn + "<html><br>" +  token +  "<html/>" + "\n";
            }
        }
        return toReturn;
    }

    /**
     *
     * @return
     */

    public String printDogNames(){
        String toReturn = "";
        for (Dog d : dogsSelected) {
            toReturn = toReturn + "<html><br>" + d.getName() + "<html/>" + "\n";
        }
        return toReturn;
    }

    /**
     *
     * @return
     */

    public JFrame getSuccess() {
        return success;
    }
}
