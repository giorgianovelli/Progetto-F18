package customerClient.gui;

import customerClient.CustomerProxy;
import dogSitterClient.DogSitterProxy;
import server.Dog;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private GridLayout gridLayout = new GridLayout(1,1);
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonInfo;
    private JButton buttonSelect;
    private JLabel labelDogsitter;

    private HashSet<String> dogsitterList;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> selectedDogs;
    private Address meetingPoint;
    private boolean paymentInCash;
    private String paymentMethod;
    private String emailCustomer;


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    // Costruttore

    public GUIChooseDogsitter(HashSet<String> dogsitterList, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash, String emailCustomer) {
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

        initComponents();
    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    public void initComponents() {
        panelOut.setLayout(new BorderLayout());
        panelContainer = new JPanel(gridLayout);




        for (String mailDogsitter: dogsitterList){
            DogSitterProxy dogSitterProxy = new DogSitterProxy(mailDogsitter);
            labelDogsitter = new JLabel("<html><br>" + dogSitterProxy.getName() + " " + dogSitterProxy.getSurname() + "<br/>" + mailDogsitter, SwingConstants.LEFT);
            panelLabel = new JPanel();
            panelLabel.setBorder(BorderFactory.createEmptyBorder(0,40,0, 0));
            panelLabel.add(labelDogsitter);
            panelButtons = new JPanel();
            panelButtons.setLayout(new GridLayout(1,2,10,0));
            panelButtons.setBorder(BorderFactory.createEmptyBorder(15,0,15, 40));
            buttonInfo = new JButton("Info");
            buttonSelect = new JButton("Select");
            panelButtons.add(buttonInfo);
            panelButtons.add(buttonSelect);
            panelDogsitter = new JPanel();
            panelDogsitter.setLayout(new BorderLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            panelDogsitter.add(panelLabel, BorderLayout.WEST);
            panelDogsitter.add(panelButtons, BorderLayout.EAST);
            panelContainer.add(panelDogsitter);
            gridLayout.setRows(gridLayout.getRows() + 1);


            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIConfirmAssignment guiConfirmAssignment = new GUIConfirmAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, selectedDogs, meetingPoint, emailCustomer, paymentInCash);
                    guiConfirmAssignment.setVisible(true);
                }
            };

            buttonSelect.addActionListener(actionListener);

        }

        panelOut.add(panelContainer, BorderLayout.NORTH);
        System.out.println(dogsitterList.toString());



        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);

    }
}
