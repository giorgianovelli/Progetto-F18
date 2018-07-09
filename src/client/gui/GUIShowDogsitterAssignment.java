package client.gui;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import enumeration.CalendarState;
import server.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUIShowDogsitterAssignment extends GUIListAssignments {

    private DogSitterProxy dogSitterProxy;


    public GUIShowDogsitterAssignment(CalendarState cs, HashMap<Integer, Assignment> listAssignment, String email, GUIHome guiDogsitter){
        super(cs,listAssignment, email, guiDogsitter);

        initComponents(cs, guiDogsitter);


    }


    @Override
    protected void initComponents(CalendarState cs, GUIHome guiDogsitter) {


        dogSitterProxy = new DogSitterProxy(email);


        gridLayout = new GridLayout(1,1);
        assignmentNumber = listAssignment.size();

        gridLayout = new GridLayout(1,1);
        contentPanel = new JPanel();
        panelOut = new JPanel();
        scrollPanel = new JScrollPane(panelOut);

        infoPanel = new JPanel[assignmentNumber];
        labelDescription = new JLabel[assignmentNumber];
        buttonAction = new JButton[assignmentNumber];
        labelState = new JLabel[assignmentNumber];

        contentPanel.setLayout(gridLayout);
        panelOut.setLayout(new BorderLayout());

        System.out.println(dogSitterProxy.toString());
        int j = 0;

        for(Integer i : listAssignment.keySet()){
            Assignment a = null;

            a = listAssignment.get(i);

            String nameCustomer = dogSitterProxy.getCustomerNameOfAssignment(a.getCode());
            String surnameCustomer = dogSitterProxy.getCustomerSurnameOfAssignment(a.getCode());

            ActionListener showInfo = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(listAssignment.get(i), email);
                    assignmentInfo.setVisible(true);

                }
            };




            labelDescription[j]= new JLabel("Assignment with " + nameCustomer + " " + surnameCustomer);
            buttonAction[j]= new JButton("Info");
            buttonAction[j].addActionListener(showInfo);


            createPanelAssignment(a,j);

            gridLayout.setRows(gridLayout.getRows() + 1);
            j++;

        }



        panelOut.add(contentPanel, BorderLayout.NORTH);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);



    }

}
