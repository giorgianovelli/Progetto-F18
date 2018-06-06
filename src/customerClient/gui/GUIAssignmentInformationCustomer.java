package customerClient.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;


public class GUIAssignmentInformationCustomer extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelAssignmentData = new JPanel();
    private GridLayout gridLayout = new GridLayout(8, 2, 0, 10);
    private JScrollPane scrollPane = new JScrollPane(panelAssignmentData);
    private JLabel labelCode1 = new JLabel("Code: ", SwingConstants.CENTER);
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

    private String email;


//________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    /**
     *
     * @param a
     */

    public GUIAssignmentInformationCustomer(Assignment a, String email){
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;


        initComponents(a);
    }

//_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________



    public GUIAssignmentInformationCustomer(){
        setTitle("Assignment information");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        // initComponents();
    }

//______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    /**
     *
     * @param a
     */

    private void initComponents(Assignment a){
        panelAssignmentData.setLayout(gridLayout);
        panelAssignmentData.add(labelCode1);
        panelAssignmentData.add(labelCode2);
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

        String strDogsitter = customerProxy.getDogSitterNameOfAssignment(intCode) + " " + customerProxy.getDogSitterSurnameOfAssignment(intCode);
        String strDogs = a.printDogNames();
        String[] strDogsSplitted = strDogs.split("\n");
        String strMeetingPoint = a.printMeetingPoint();
        Double doubleAmount = customerProxy.estimatePriceAssignment(a.getDogList(), a.getDateStart(), a.getDateEnd());              // Importo pagato o da pagare per l'appuntamento da prelevare dal DB
        String strPayment = customerProxy.getCustomerPaymentMethod().getNumber();

        //Passaggio delle variabili alle Jlabel che contengono i dati

        labelCode2.setText(intCode.toString());
        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(strDogsitter);
        labelAmount2.setText(String.valueOf(doubleAmount));
        labelPaymentMethod2.setText(strPayment);

        // Creazione e passaggio JLabel per i cani

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
    }
}
