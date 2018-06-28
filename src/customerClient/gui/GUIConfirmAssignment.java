package customerClient.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import server.Dog;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class GUIConfirmAssignment extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelAssignmentData = new JPanel();
    private GridLayout gridLayout = new GridLayout(8, 2, 0, 10);
    private JScrollPane scrollPane = new JScrollPane(panelAssignmentData);

    private JLabel labelStartDate1 = new JLabel("Start Date: ", SwingConstants.CENTER);
    private JLabel labelEndDate1 = new JLabel("End Date: ", SwingConstants.CENTER);
    private JLabel labelDogsitter1 = new JLabel("Dogsitter: ", SwingConstants.CENTER);
    private JLabel labelMeetingPoint1 = new JLabel("Meeting Point: ", SwingConstants.CENTER);
    private JLabel labelAmount1 = new JLabel("Amount: ", SwingConstants.CENTER);
    private JLabel labelPaymentMethod1 = new JLabel("Paymenth Method: ", SwingConstants.CENTER);
    private JLabel labelDogs1 = new JLabel("Dogs: ", SwingConstants.CENTER);
    private JLabel labelEmpty = new JLabel("\t");

    private JLabel labelCode2 = new JLabel();
    private JLabel labelStartDate2 = new JLabel();
    private JLabel labelEndDate2 = new JLabel();
    private JLabel labelDogsitter2= new JLabel();
    private JLabel labelMeetingPoint2 = new JLabel();
    private JLabel labelAmount2 = new JLabel();
    private JLabel labelPaymentMethod2 = new JLabel();


    private String mailDogsitter;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> dogsSelected;
    private Address meetingPoint;
    private CustomerProxy customerProxy;
    private String emailCustomer;
    private Double doubleAmount;





    public GUIConfirmAssignment(String mailDogsitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> dogsSelected, Address meetingPoint, String emailCustomer) {
        setTitle("Assignment information");
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
        customerProxy = new CustomerProxy(emailCustomer);

        initComponents();
    }

    private void initComponents() {
        panelAssignmentData.setLayout(gridLayout);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDateStart = dateFormat.format(dateStartAssignment);
        String strEndDate = dateFormat.format(dateEndAssignment);
        String strMeetingPoint = printMeetingPoint();
        String strDogs = printDogNames();
        String[] strDogsSplitted = strDogs.split("\n");
        doubleAmount = customerProxy.estimatePriceAssignment(dogsSelected, dateStartAssignment, dateEndAssignment);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

    public String printMeetingPoint() {
        String toReturn = "";
        for (String token: meetingPoint.toString().split(";")) {
            if (!token.isEmpty()) {
                toReturn = toReturn + "<html><br>" +  token +  "<html/>" + "\n";
            }
        }
        return toReturn;
    }

    public String printDogNames(){
        String toReturn = "";
        for (Dog d : dogsSelected) {
            toReturn = toReturn + "<html><br>" + d.getName() + "<html/>" + "\n";
        }
        return toReturn;
    }



}
