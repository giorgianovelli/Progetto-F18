package client.gui;

import client.proxy.CustomerProxy;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class GUIDogs extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 500;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private CustomerProxy proxy;
    private String email;

    private HashSet<Dog> dogList;
    private JPanel panelContainer;
    private JPanel panelButton;
    private JPanel panelOut = new JPanel();
    private JScrollPane dogScroll;

    private JButton addDogButton;

    private DogBox [] dogBoxes;

    private GridLayout gridLayout = new GridLayout(1,1);



    public GUIDogs(String email){
        setTitle("Your dogs");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);


        this.email = email;
        proxy = new CustomerProxy(this.email);

        dogList = proxy.getDogList();
        dogBoxes = new DogBox[dogList.size()];

        initComponents();

    }

    private void initComponents(){
        int i = 0;
        //Dog d = null;
        panelOut.setLayout(new BorderLayout());
        panelContainer = new JPanel();


        panelContainer = new JPanel(gridLayout);
        panelContainer.setBorder(BorderFactory.createTitledBorder("Your Dogs: "));
        panelButton = new JPanel();
        addDogButton = new JButton("Add new dog");
        addDogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddDog guiAddDog = new GUIAddDog(email);
                guiAddDog.setVisible(true);

                dispose();
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
                    GUIDogInfo dogInfo = new GUIDogInfo(dog, email);
                    dogInfo.setVisible(true);
                    dispose();
                }
            });

            dogBoxes[i].getDisableButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(proxy.disableDog(dog.getID())){
                        JOptionPane.showMessageDialog(new Frame(), dog.getName()+" disabled!", "", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
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


class DogBox extends JPanel{

    private JLabel nameLabel;
    private JButton infoButton;
    private JButton disableButton;

    private JPanel panelDog;
    private JPanel panelLabel;
    private JPanel panelButton;




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
    public JButton getInfoButton() {
        return infoButton;
    }

    public JButton getDisableButton() {
        return disableButton;
    }
}