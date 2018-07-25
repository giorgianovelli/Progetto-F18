package client.gui;

import client.proxy.CustomerProxy;
import enumeration.AssignmentState;
import server.Assignment;
import client.clientEnumerations.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.lang.*;


/**
 * This class show if there are assignments for each day
 * and allows you to delete existing assignments.
 */
public class GUIDailyAssignments extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 600;

    /**
     * Frame height.
     */
    final int HEIGHT = 512;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * The external panel p  who contains the array of infoPanel.
     */
    protected JPanel p = new JPanel();

    /**
     * The panel who contains the array of button and buttonInfo.
     */
    protected JPanel panelButtons = new JPanel();

    /**
     * Grid Layout for the panel p.
     */
    protected GridLayout gridLayout = new GridLayout(1, 1);

    /**
     * Array of buttons, how allows to delete assignment.
     */
    protected JButton button[] = new JButton[SwingConstants.RIGHT];

    /**
     * Array of buttons, it allows to show information.
     */
    protected JButton buttonInfo[] = new JButton[SwingConstants.RIGHT];

    /**
     * Array of Label for the description of the assignment.
     */
    protected JLabel[] labelDescription = new JLabel[SwingConstants.LEFT];

    /**
     * Array of panels, who contains labelDescription and panelButtons.
     */
    protected JPanel[] infoPanel;

    /**
     * The label who show the message with no assignment for that day.
     */
    protected JLabel lb = new JLabel();

    /**
     * The scroll panel of the windows
     */
    protected JScrollPane scroll = new JScrollPane(p);

    /**
     * The list of Assignments.
     */
    protected HashMap<Integer, Assignment> listAssigment;

    /**
     * The customer proxy.
     */
    protected CustomerProxy proxy;

    /**
     * The costumer's email.
     */
    protected String email;

    /**
     * The calendar days of the customer
     */
    protected Date todayDate;

    /**
     * The gui attribute for this class.
     */
    public GUIDailyAssignments guiDailyAssignments;



    /**
     * Constructor
     *
     * @param cs   identifies the menu from which this interface is called
     * @param email       of the customer
     * @param todayDate  identifies the days in the calendar
     * @param guiHome    GUI from which is launched

     */
    public GUIDailyAssignments(CalendarState cs, String email, Date todayDate, GUIHome guiHome) {

        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        this.email = email;
        this.todayDate = todayDate;
        guiDailyAssignments = this;
        initComponents(cs);

        guiHome.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiHome.setEnabled(true);
            }
        });

    }


    /**
     * Constructor
     *
     * @param cs   identifies the menu from which this interface is called
     * @param email       of the customer
     * @param todayDate  identifies the days in the calendar
     * @param guiCustomer GUI from where GUIDailyAssignment is invoked.

     */
    public GUIDailyAssignments(CalendarState cs, String email, Date todayDate, GUICustomer guiCustomer) {

        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        this.email = email;
        this.todayDate = todayDate;
        guiDailyAssignments = this;
        initComponents(cs);

        guiCustomer.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiCustomer.setEnabled(true);
            }
        });
    }


    /**
     * Constructor
     *
     * @param cs   identifies the menu from which this interface is called
     * @param email       of the customer
     * @param todayDate  identifies the days in the calendar

     */
    public GUIDailyAssignments(CalendarState cs, String email, Date todayDate) {

        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        this.email = email;
        this.todayDate = todayDate;
        guiDailyAssignments = this;
        initComponents(cs);

    }


    /**
     * Method that initializes graphic components of the GUI
     *
     * @param cs identifies the menu from which this interface is called

     */
    protected void initComponents(CalendarState cs) {
        proxy = new CustomerProxy(email);
        this.listAssigment = proxy.getAssignmentList();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setLayout(new GridLayout(9, 1, 20, 20));


        if ((cs.equals(CalendarState.REMOVING)) || (cs.equals((CalendarState.NORMAL)))) {

            setTitle("Daily assignment");

            HashMap<Integer, Assignment>todayAssigment = new HashMap<>();

            int n = 0;

            for (Integer i : listAssigment.keySet()) {


                Assignment a = null;
                a = listAssigment.get(i);
                Date dateStart = a.getDateStart();
                Date dateEnd = a.getDateEnd();
                SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = date1.format(dateStart);
                String dateStringEnd1 = date1.format(dateEnd);
                SimpleDateFormat date2 = new SimpleDateFormat("dd/MM/yyyy");
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
            buttonInfo = new JButton[todayAssigment.size()];
            infoPanel = new JPanel[todayAssigment.size()];



            if ((cs.equals(CalendarState.REMOVING))) {

                if (todayAssigment.isEmpty()) {

                    lb = new JLabel(" There aren't assignments today to be cancel ", SwingConstants.CENTER);
                    p.add(lb);


                } else {

                    int j = 0;

                    for (Integer i : todayAssigment.keySet()) {
                        Assignment a = null;
                        String labelString = "";
                        a = todayAssigment.get(i);
                        String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                        String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());


                        labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";



                        labelDescription[j] = new JLabel(labelString);
                        buttonInfo[j] = new JButton("More info");
                        button[j] = new JButton("Delete");


                        button[j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                int action = JOptionPane.showConfirmDialog(null, "Are you sure to cancel ?", "Conferm Actions", JOptionPane.YES_NO_OPTION);
                                if (action == JOptionPane.YES_OPTION) {
                                    proxy.removeAssignment(todayAssigment.get(i).getCode()); //TODO funzionante e verificato
                                    guiDailyAssignments.dispatchEvent(new WindowEvent(guiDailyAssignments, WindowEvent.WINDOW_CLOSING));
                                }

                            }
                        });

                        ActionListener showInfo = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(todayAssigment.get(i), email, guiDailyAssignments);
                                assignmentInfo.setVisible(true);

                            }
                        };


                        buttonInfo[j].addActionListener(showInfo);

                        createPanelOrderDelete(j);

                        j++;

                    }

                }

                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                getContentPane().add(scroll);

            }



            else{

                if (todayAssigment.isEmpty()) {
                    lb = new JLabel(" There aren't assignments today ", SwingConstants.CENTER);
                    p.add(lb);

                } else {

                    HashMap<Integer, Assignment> assignmentHashMap = assignmentToCancel(todayAssigment);

                    if (assignmentHashMap.isEmpty()) {
                        lb = new JLabel(" There aren't assignments today ", SwingConstants.CENTER);

                        p.add(lb);
                    }else{

                        int j = 0;

                        for (Integer i : assignmentHashMap.keySet()) {
                            Assignment a = null;
                            String labelString = "";
                            a = todayAssigment.get(i);
                            String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                            String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                            labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";


                            labelDescription[j] = new JLabel(labelString);
                            button[j] = new JButton("More info");

                            ActionListener showInfo = new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(todayAssigment.get(i), email, guiDailyAssignments);
                                    assignmentInfo.setVisible(true);

                                }
                            };


                            button[j].addActionListener(showInfo);


                            createPanelOrder(j);
                            j++;


                        }

                    }




                    }


                    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    getContentPane().add(scroll);


            }
        }
    }






        /**
         * method who create a panel that contains the assignment information: status, description and  button
         * @param i panel index

         */
        protected void createPanelOrder ( int i){

            infoPanel[i] = new JPanel();


            infoPanel[i].setLayout(new BorderLayout());
            infoPanel[i].setMaximumSize(new Dimension(450, 150));

            labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

            infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
            infoPanel[i].add(button[i], BorderLayout.EAST);

            infoPanel[i].setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 15)); //5,5,5,5

            p.add(infoPanel[i]);

        }


        /**
         * method who create a panel that contains the assignment information: status, description and  PanelButtons
         * @param i panel index

         */
        protected void createPanelOrderDelete(int i) {

        infoPanel[i] = new JPanel();
        panelButtons = new JPanel();

        panelButtons.setBorder(BorderFactory.createEmptyBorder(5,0,5, 150));


        infoPanel[i].setLayout(new BorderLayout());
        infoPanel[i].setMaximumSize(new Dimension(450,150));

        labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 100));

        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        panelButtons.add(button[i]);
        panelButtons.add(buttonInfo[i]);

        infoPanel[i].add(panelButtons, BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,10,5,150));

        p.add(infoPanel[i]);
        }


    /**
     * This method allows the dog sitter to refuse an assignment without graphic traces
     *
     * @param listAssignment identifies the list of assignments for each day on the calendar
     * @return assignmentHashMap
     */
        protected HashMap<Integer, Assignment> assignmentToCancel(HashMap<Integer, Assignment> listAssignment){
            HashMap<Integer, Assignment> assignmentHashMap = new HashMap<>();

            for(Integer i : listAssignment.keySet()){
                if(listAssignment.get(i).getState() != AssignmentState.DELETED){
                    assignmentHashMap.put(i, listAssignment.get(i));
                }
            }

            return assignmentHashMap;
        }





}


