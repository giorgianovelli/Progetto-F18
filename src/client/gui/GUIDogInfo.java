package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GUIDogInfo extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 300;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private Dog dog;
    private String email;
    private CustomerProxy proxy;

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButton = new JPanel();
    //private JPanel panelDate = new JPanel();


    private JLabel labelName = new JLabel("Name:", SwingConstants.LEFT);
    private JLabel labelAge = new JLabel("Age:", SwingConstants.LEFT);
    private JLabel labelBreed = new JLabel("Breed:", SwingConstants.LEFT);
    private JLabel labelWeight = new JLabel("Weight:", SwingConstants.LEFT);


    private JTextField textName = new JTextField();
    private JTextField textWeight = new JTextField();
    private JTextField textBreed = new JTextField(); //può diventare una combobox?
    private JTextField textAge = new JTextField();

    private JButton buttonConfirm = new JButton("Confirm");
    private JButton buttonCancel = new JButton("Cancel");


    /*private JComboBox<String> dayList;
    private JComboBox<String> monthList;
    private JComboBox<String> yearList;


    private String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private ArrayList<String> years_tmp = new ArrayList<String>();
*/



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
/*
        for (int years = 1930; years <= Calendar.getCurrentYear(); years++) {
            years_tmp.add(years + "");
        }

        dayList = new JComboBox<>(day);
        monthList = new JComboBox<>(month);
        yearList = new JComboBox(years_tmp.toArray());

        Date strDate = dog.getAge();
        SimpleDateFormat dateFormatdd = new SimpleDateFormat("dd");
        SimpleDateFormat dateFormatmm = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatyyy = new SimpleDateFormat("yyyy");

        String day = dateFormatdd.format(strDate);
        String month = dateFormatmm.format(strDate);
        String year = dateFormatyyy.format(strDate);

        dayList.setSelectedItem(day);
        monthList.setSelectedItem(month);
        yearList.setSelectedItem(year);*/

        ActionListener registration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Confirm")) {
                    if (textName.getText().equals("") || textAge.getText().equals("") || textWeight.getText().equals("") || textBreed.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "ERROR! Empty fields", "", JOptionPane.ERROR_MESSAGE);

                    }
                    else
                    {
                        if(setNewValues()){
                            JOptionPane.showMessageDialog(new JFrame(), "the data update was successful", "", JOptionPane.INFORMATION_MESSAGE);

                            dispose();
                        }
                    }
                }

                if (registrationAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }

            }
        };


        panelData.setLayout(new GridLayout(4,2, 10,10));
        panelData.setBorder(BorderFactory.createTitledBorder("Dog fields:"));

        panelData.add(labelName);
        textName.setText(dog.getName());
        panelData.add(textName);

        panelData.add(labelAge);
        textAge.setText(Integer.toString(dog.getAge()));
        panelData.add(textAge);

        panelData.add(labelBreed);
        textBreed.setText(dog.getBreed());
        panelData.add(textBreed);

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
        boolean updateName = proxy.updateDogName(dogID,textName.getText());
        boolean updateBreed = proxy.updateDogBreed(dogID, textBreed.getText());
        boolean updateWeight = proxy.updateDogWeight(dogID, Double.parseDouble(textWeight.getText()));
        //TODO manca l'aggiornamento della data di nascita


        if(updateName && updateBreed && updateWeight){
            update = true;
        }


        return update;


    }
}
