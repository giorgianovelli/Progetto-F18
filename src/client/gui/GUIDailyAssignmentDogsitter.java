package client.gui;


import client.proxy.DogSitterProxy;
import client.clientEnumerations.CalendarState;
import server.Assignment;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class show to the DogSitter
 * if there are assignments for each day
 * and extend GUIDailyAssignments.
 */
public class GUIDailyAssignmentDogsitter extends GUIDailyAssignments {

    /**
     * The dog sitter proxy.
     */
    private DogSitterProxy dogSitterProxy;

    /**
     * This GUI.
     */
    private GUIDailyAssignmentDogsitter guiDailyAssignmentDogsitter;



    /**
     * Constructor
     *
     * @param cs   identifies the menu from which this interface is called
     * @param email       of the customer
     * @param todayDate  identifies the days in the calendar
     *
     */
    public GUIDailyAssignmentDogsitter(CalendarState cs, String email, Date todayDate) {

        super(cs, email, todayDate);

        this.email = email;
        this.todayDate = todayDate;
        guiDailyAssignmentDogsitter = this;
        initComponents(cs);


    }


    /**
     * Method that initializes graphic components of the GUI
     *
     * @param cs identifies the menu from which this interface is called
     *
     */
    @Override
    protected void initComponents(CalendarState cs) {
        dogSitterProxy = new DogSitterProxy(email);
        listAssigment = dogSitterProxy.getAssignmentList();


        HashMap<Integer, Assignment> todayAssigment = new HashMap<Integer, Assignment>();

        gridLayout = new GridLayout(1, 1);

        p = new JPanel();
        lb = new JLabel();
        scroll = new JScrollPane(p);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setLayout(new GridLayout(9, 1, 20, 20));

        if (cs.equals(CalendarState.NORMAL)) {

            int n = 0;

            for (Integer i : listAssigment.keySet()) {


                Assignment a = null;
                a = listAssigment.get(i);
                Date dateStart = a.getDateStart();
                Date dateEnd = a.getDateEnd();
                SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = date1.format(dateStart);
                String dateStringEnd1 = date1.format(dateEnd);
                String dateString2 = date1.format(todayDate);
                String dateStringEnd2 = date1.format(todayDate);
                dateString1.equals(dateString2);
                dateStringEnd1.equals(dateStringEnd2);


                if (dateString1.equals(dateString2) || (dateStringEnd1.equals(dateStringEnd2))) {

                    todayAssigment.put(n, a);
                }

                n++;

            }


            labelDescription = new JLabel[todayAssigment.size()];
            button = new JButton[todayAssigment.size()];
            infoPanel = new JPanel[todayAssigment.size()];


            if (todayAssigment.isEmpty()) {

                lb = new JLabel(" There aren't assignments today ", SwingConstants.CENTER);

                p.add(lb);

            } else {

                int j = 0;

                for (Map.Entry<Integer, Assignment> entry : todayAssigment.entrySet()) {
                    Assignment a = null;
                    String labelString = "";
                    a = entry.getValue();
                    String nameCostumer = dogSitterProxy.getCustomerNameOfAssignment(a.getCode());
                    String surnameCostumer = dogSitterProxy.getCustomerSurnameOfAssignment(a.getCode());
                    labelString = "<html>" + "Assignment with " + nameCostumer + " " + surnameCostumer + "</html>";


                    labelDescription[j] = new JLabel(labelString);
                    button[j] = new JButton("More info");
                    System.out.println(entry.getValue());
                    System.out.println(email);
                    button[j].addActionListener(e -> new GUIAssignmentInformationDogsitter(entry.getValue(), email, guiDailyAssignmentDogsitter).setVisible(true));


                    createPanelOrder(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;


                }
            }
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            getContentPane().add(scroll);

        }

    }

}


