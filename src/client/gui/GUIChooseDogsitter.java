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
import java.util.Date;
import java.util.HashSet;

public class GUIChooseDogsitter extends JFrame {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    CustomerProxy customerProxy;


    private JPanel panelOut = new JPanel();
    private JPanel panelDogsitter;
    private JPanel panelLabel;
    private JPanel panelButtons;
    private JPanel panelContainer;
    private JPanel panelClose = new JPanel(new BorderLayout());
    private GridLayout gridLayout = new GridLayout(1,1,5,0);
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonInfo;
    private JButton buttonSelect;
    private JButton buttonClose = new JButton("Close");
    private JLabel labelDogsitter;

    private HashSet<String> dogsitterList;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> selectedDogs;
    private Address meetingPoint;
    private boolean paymentInCash;
    private String paymentMethod;
    private String emailCustomer;

    /**
     *
     * @param dogsitterList
     * @param dateStartAssignment
     * @param dateEndAssignment
     * @param selectedDogs
     * @param meetingPoint
     * @param paymentInCash
     * @param emailCustomer
     */

    public GUIChooseDogsitter(HashSet<String> dogsitterList, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash, String emailCustomer, GUINewAssignment guiNewAssignment) {
        setTitle("Choose the dogsitter");       // TODO Da cambiare??
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        this.dogsitterList = dogsitterList;
        this.dateStartAssignment = dateStartAssignment;
        this.dateEndAssignment = dateEndAssignment;
        this.selectedDogs = selectedDogs;
        this.meetingPoint = meetingPoint;
        this.paymentInCash = paymentInCash;
        this.emailCustomer = emailCustomer;
        customerProxy = new CustomerProxy(emailCustomer);

        guiNewAssignment.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiNewAssignment.setEnabled(true);
            }
        });


        initComponents();
    }


    /**
     * This method implements graphic objects
     */

    public void initComponents() {
        panelOut.setLayout(new BorderLayout());
        panelContainer = new JPanel(gridLayout);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20,320,20,320));

        GUIChooseDogsitter guiChooseDogsitter = this;

        panelOut.add(panelContainer, BorderLayout.NORTH);
        panelOut.add(panelClose, BorderLayout.SOUTH);
        panelClose.add(buttonClose, BorderLayout.CENTER);

        for (String mailDogsitter: dogsitterList){

            DogSitterProxy dogSitterProxy = new DogSitterProxy(mailDogsitter);


            labelDogsitter = new JLabel("<html><br>" + dogSitterProxy.getName() + " " + dogSitterProxy.getSurname() + "<br/>" + mailDogsitter, SwingConstants.LEFT);

            panelLabel = new JPanel();
            panelLabel.setBorder(BorderFactory.createEmptyBorder(0,40,20, 0));
            panelLabel.add(labelDogsitter);

            panelButtons = new JPanel();
            panelButtons.setLayout(new GridLayout(1,2,10,0));
            panelButtons.setBorder(BorderFactory.createEmptyBorder(30,0,30, 40));

            buttonInfo = new JButton("Info");
            buttonSelect = new JButton("Select");
            panelButtons.add(buttonInfo);
            panelButtons.add(buttonSelect);

            panelDogsitter = new JPanel();
            panelDogsitter.setLayout(new BorderLayout());
            panelDogsitter.setBorder(BorderFactory.createTitledBorder(""));
            panelDogsitter.add(panelLabel, BorderLayout.WEST);
            panelDogsitter.add(panelButtons, BorderLayout.EAST);
            panelContainer.add(panelDogsitter);

            gridLayout.setRows(gridLayout.getRows() + 1);

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIConfirmAssignment guiConfirmAssignment = new GUIConfirmAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, selectedDogs, meetingPoint, emailCustomer, paymentInCash, guiChooseDogsitter);
                    guiConfirmAssignment.setVisible(true);
                }
            };

            ActionListener actionListener1 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIDogsitterInfo guiDogsitterInfo = new GUIDogsitterInfo(mailDogsitter, guiChooseDogsitter );
                    guiDogsitterInfo.setVisible(true);
                }
            };


            buttonSelect.addActionListener(actionListener);
            buttonInfo.addActionListener(actionListener1);

        }



        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiChooseDogsitter.dispatchEvent(new WindowEvent(guiChooseDogsitter, WindowEvent.WINDOW_CLOSING));
            }
        };

        buttonClose.addActionListener(actionListener2);



        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);

    }
}
