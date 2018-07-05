package client.gui;

import client.proxy.CustomerProxy;
import server.Assignment;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;


public class GUIAssignmentInformationCustomer extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 550;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private GridLayout gridLayout = new GridLayout(1, 2);
    private GridLayout gridLayout2 = new GridLayout(1,1);
    private JPanel panelOut = new JPanel(new BorderLayout());
    private JPanel panelAssignmentData = new JPanel(new GridLayout(7,1));
    private JPanel panelDogs = new JPanel(gridLayout2);
    private JPanel panelClose = new JPanel(new BorderLayout());

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
    private JLabel labelDogs1 = new JLabel("Dogs: ");
    private JLabel labelEmpty = new JLabel("\t");

    private JLabel labelCode2 = new JLabel();
    private JLabel labelStartDate2 = new JLabel();
    private JLabel labelEndDate2 = new JLabel();
    private JLabel labelDogsitter2= new JLabel();
    private JLabel labelMeetingPoint2 = new JLabel();
    private JLabel labelAmount2 = new JLabel();
    private JLabel labelPaymentMethod2 = new JLabel();

    private JButton buttonClose = new JButton("Close");

    private String email;


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

    /**
     *
     * @param a
     */

    private void initComponents(Assignment a){

        panelOut.add(panelAssignmentData, BorderLayout.NORTH);
        panelOut.add(panelDogs, BorderLayout.CENTER);
        panelOut.add(panelClose, BorderLayout.SOUTH);

        panelAssignmentData.setBorder(BorderFactory.createTitledBorder("Summary: "));
        panelDogs.setBorder(BorderFactory.createTitledBorder("Dogs: "));

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

        //Pannello bottone Close e Action Listener

        panelClose.add(buttonClose);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20, 190, 20, 190));

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        };
        buttonClose.addActionListener(actionListener);

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
        HashSet<Dog> dogList = a.getDogList();
        String strDogs = a.printDogNames();
        String[] strDogsSplitted = strDogs.split("\n");
        String strMeetingPoint = a.printMeetingPoint();
        Double doubleAmount = customerProxy.estimatePriceAssignment(a.getDogList(), a.getDateStart(), a.getDateEnd());              // Importo pagato o da pagare per l'appuntamento da prelevare dal DB
        String amount = String.format("%.2f", doubleAmount).replace(",",".");
        String strPayment = customerProxy.getPaymentMethod().getNumber();

        //Passaggio delle variabili alle Jlabel che contengono i dati

        labelCode2.setText(intCode.toString());
        labelStartDate2.setText(strDateStart);
        labelEndDate2.setText(strEndDate);
        labelMeetingPoint2.setText(strMeetingPoint);
        labelDogsitter2.setText(strDogsitter);
        labelAmount2.setText(amount);
        labelPaymentMethod2.setText(strPayment);

        // Creazione e passaggio JLabel per i cani


        int i = 1;
        for (Dog dog: dogList) {

                JPanel panelDog = new JPanel(new GridLayout(1,1));
                JLabel tmplabel = new JLabel(i + ".     " + dog.getName());
                panelDog.add(tmplabel);
                panelDogs.add(panelDog);
                gridLayout2.setRows(gridLayout.getRows() + 1);
                i++;
        }

    }
}
