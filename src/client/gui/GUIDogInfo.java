package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class GUIDogInfo extends JFrame {

    final int WIDTH = 600;
    final int HEIGHT = 300;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private Dog dog;
    private String email;
    private CustomerProxy proxy;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    private JPanel panelDate = new JPanel();


    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelDate = new JLabel("Date of birth:", SwingConstants.LEFT);
    private JLabel labelBreed = new JLabel("Breed:", SwingConstants.LEFT);
    private JLabel labelWeight = new JLabel("Weight:", SwingConstants.LEFT);


    private JTextField textName = new JTextField();
    private JTextField textWeight = new JTextField();

    private JComboBox<String> breedList;
    private String[] breed;


    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");


    private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;


    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<>();




    public GUIDogInfo(Dog dog, String email){
        this.dog = dog;
        this.email = email;
        proxy = new CustomerProxy(this.email);
        initComponent();
    }

    private void initComponent() {
        setTitle("Dog info");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        Date strDate = proxy.getDogDateOfBirth(dog.getID());
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    if (textName.getText().equals("") ||  textWeight.getText().equals("") ) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    }
                    else
                    {
                        if(setNewValues()){
                            JOptionPane.showMessageDialog(new JFrame(), "The data update was successful", "", JOptionPane.INFORMATION_MESSAGE);

                            dispose();
                        }
                    }
                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }

            }
        };

        HashSet<String> breedSet = proxy.getDogsBreedsList();
        breed = new String[breedSet.size()];

        int i = 0;
        for (String breedStr : breedSet){
            breed[i] = breedStr;
            i++;
        }

        breedList = new JComboBox<>(breed);
        breedList.setSelectedItem(dog.getBreed());


        panelData.setLayout(new GridLayout(4,2, 10,10));
        panelData.setBorder(BorderFactory.createTitledBorder("Dog fields:"));

        panelData.add(labelName);
        textName.setText(dog.getName());
        panelData.add(textName);

        panelData.add(labelDate);
        panelDate.setLayout(new GridLayout(1,2,5,5));
        panelDate.add(dayList);
        panelDate.add(monthList);
        panelDate.add(yearList);

        panelData.add(panelDate);

        panelData.add(labelBreed);
        panelData.add(breedList);

        panelData.add(labelWeight);
        textWeight.setText(Double.toString(dog.getWeight()));
        panelData.add(textWeight);

        panelButton.setLayout(new GridLayout(1,2, 5,5));
        panelButton.setBorder(BorderFactory.createEmptyBorder(15, 90, 10, 90));
        buttonConfirm.addActionListener(registration);
        buttonCancel.addActionListener(registration);
        panelButton.add(buttonConfirm);
        panelButton.add(buttonCancel);

        panelOut.setLayout(new BorderLayout());
        panelOut.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButton, BorderLayout.SOUTH);

        add(panelOut);

    }

    private boolean setNewValues(){
        boolean update = false;
        int dogID = dog.getID();
        boolean updateName = proxy.updateDogName(dogID,textName.getText().toUpperCase());
        boolean updateBreed = proxy.updateDogBreed(dogID, breedList.getSelectedItem().toString());
        boolean updateWeight = proxy.updateDogWeight(dogID, Double.parseDouble(textWeight.getText()));


        Date dateOfBirth = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String strDateOfBirth = dayList.getSelectedItem().toString() + "/" + monthList.getSelectedItem().toString() + "/" + yearList.getSelectedItem().toString();
        try {
            dateOfBirth = dateFormat.parse(strDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean updateDate = proxy.updateDogAge(dogID, dateOfBirth );


        if(updateName && updateBreed && updateWeight && updateDate){
            update = true;
        }


        return update;


    }
}
