package client.gui;

import client.proxy.CustomerProxy;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;


/**
 * This class show the dog's information
 * and allow to disable dogs.
 */
public class GUIDogs extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 512;

    /**
     * Frame height.
     */
    final int HEIGHT = 500;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The list of the user's dogs.
     */
    private HashSet<Dog> dogList;

    /**
     * The panel contains the dogBoxes.
     */
    private JPanel panelContainer;

    /**
     * The panel contains the addDogButton.
     */
    private JPanel panelButton;

    /**
     * Out panel.
     */
    private JPanel panelOut = new JPanel();

    /**
     * Scroll Panel allows to show the list of dogs.
     */
    private JScrollPane dogScroll;

    /**
     * The button allows to add a new dog.
     */
    private JButton addDogButton;

    /**
     * Array of DogBox.
     */
    private DogBox [] dogBoxes;

    /**
     * This GUI.
     */
    private GUIDogs guiDogs;

    /**
     * Grid Layout for panelContainer.
     */
    private GridLayout gridLayout = new GridLayout(1,1);


    /**
     * Constructor
     * @param email the user's email.
     * @param guiHome GUI from where GUIDogs is invoked.
     */
    public GUIDogs(String email, GUIHome guiHome){
        setTitle("Your dogs");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        guiDogs = this;
        guiHome.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiHome.setEnabled(true);
            }
        });


        this.email = email;
        proxy = new CustomerProxy(this.email);

        dogList = proxy.getDogList();
        dogBoxes = new DogBox[dogList.size()];

        initComponents();

    }

    /**
     * Initialize the GUI components.
     */
    private void initComponents(){
        int i = 0;

        panelOut.setLayout(new BorderLayout());
        panelContainer = new JPanel();


        panelContainer = new JPanel(gridLayout);
        panelContainer.setBorder(BorderFactory.createTitledBorder("Your Dogs: "));
        panelButton = new JPanel();
        addDogButton = new JButton("Add new dog");
        addDogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddDog guiAddDog = new GUIAddDog(email, guiDogs);
                guiAddDog.setVisible(true);

            }
        });

        HashSet<Dog> dogListEnabled = new HashSet<>();

        for(Dog dog: dogList)
        {
            if(dog.isEnabled()){
                dogListEnabled.add(dog);
            }
        }
        for (Dog dog:dogListEnabled) {
            //d = dog;
            dogBoxes[i] = new DogBox(dog.getName(), "Change info", "Disable dog");
            dogBoxes[i].getInfoButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIDogInfo dogInfo = new GUIDogInfo(dog, email, guiDogs);
                    dogInfo.setVisible(true);
                }
            });

            dogBoxes[i].getDisableButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(proxy.disableDog(dog.getID())){
                        JOptionPane.showMessageDialog(new Frame(), dog.getName()+" disabled!", "", JOptionPane.INFORMATION_MESSAGE);
                        guiDogs.dispatchEvent(new WindowEvent(guiDogs, WindowEvent.WINDOW_CLOSING));
                    }
                }
            });

            panelContainer.add(dogBoxes[i]);

            gridLayout.setRows(gridLayout.getRows() + 1);

            i++;
        }

        panelButton.add(addDogButton);
        panelButton.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));

        dogScroll = new JScrollPane(panelContainer);
        dogScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dogScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        panelOut.add(dogScroll, BorderLayout.CENTER);
        panelOut.add(panelButton, BorderLayout.SOUTH);
        add(panelOut);

    }
}

/**
 * The class contains the standard components to show a panel.
 */
class DogBox extends JPanel{

    /**
     * Label for the dog's name.
     */
    private JLabel nameLabel;

    /**
     * The button allows to show the dog's information.
     */
    private JButton infoButton;

    /**
     * The button allows to disable the dog.
     */
    private JButton disableButton;

    /**
     * Out panel.
     */
    private JPanel panelDog;

    /**
     * The panel contains nameLabel.
     */
    private JPanel panelLabel;

    /**
     * The panel contains buttons.
     */
    private JPanel panelButton;


    /**
     * Constructor
     * @param name string for the name label.
     * @param button1 string for the information button.
     * @param button2 string for the disable button.
     */
    public DogBox(String name, String button1, String button2 ){
        nameLabel = new JLabel(name);
        infoButton = new JButton(button1);
        disableButton = new JButton(button2);


        panelDog = new JPanel();
        panelDog.setLayout(new GridLayout(1,2, 5,5));

        panelLabel = new JPanel();
        panelLabel.setLayout(new BorderLayout());

        panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1,2, 5 ,5 ));

        panelLabel.add(nameLabel, BorderLayout.WEST);

        panelLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0, 70));


        panelButton.setBorder(BorderFactory.createEmptyBorder(0,20,0, 10));

        panelButton.add(infoButton);
        panelButton.add(disableButton);


        panelDog.add(panelLabel);
        panelDog.add(panelButton);


        add(panelDog);



    }

    /**
     * Getter for the infoButton
     * @return JButton Object
     */
    public JButton getInfoButton() {
        return infoButton;
    }

    /**
     * Getter for the disableButton
     * @return JButton Object
     */
    public JButton getDisableButton() {
        return disableButton;
    }
}