package client.gui;

import client.proxy.CustomerProxy;
import server.Assignment;
import server.Review;
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

public class GUIListAssignments extends JFrame{

    /**
     * Number of assignment.
     */
    protected int assignmentNumber;

    /**
     * Number of review.
     */
    protected int reviewNumber;

    /**
     * Frame width.
     */
    final int WIDTH = 512;

    /**
     * Frame height.
     */
    final int HEIGHT = 512;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * Grid Layout for contentPanel.
     */
    protected GridLayout gridLayout = new GridLayout(1,1);

    /**
     * The panel contains the array of infoPanel.
     */
    protected JPanel contentPanel = new JPanel();

    /**
     * Out panel.
     */
    protected JPanel panelOut = new JPanel();

    /**
     * Scroll Panel allows to show the list of assignments or reviews.
     */
    protected JScrollPane scrollPanel = new JScrollPane(panelOut);


    /**
     * Array of Label for the state of the assignment.
     */
    protected JLabel labelState[];

    /**
     * Array of Label for the descripione of the assignment/review.
     */
    protected JLabel[] labelDescription;

    /**
     * Array of buttons, it allows to show the information.
     */
    protected JButton[] buttonAction;

    /**
     * Array of panels, it contains [labelState], labelDescription and buttonAction.
     */
    protected JPanel[] infoPanel;

    /**
     * The panel contains the label for the description.
     */
    protected JPanel panelLabel;

    /**
     * The panel contains buttonAction.
     */
    protected JPanel panelButtons;

    /**
     * List of Assignments.
     */
    protected HashMap<Integer, Assignment> listAssignment;

    /**
     * List of Reviews.
     */
    protected HashMap<Integer, Review> listReview;

    /**
     * Map of label for each CalendarState.
     */
    protected HashMap<CalendarState,String> strLabel;

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The user's email.
     */
    protected String email;

    /**
     * This GUI.
     */
    private GUIListAssignments guiListAssignments;

    /**
     * Constructor
     * @param cs Calendar State
     * @param listAssignment list of assignments.
     * @param email the user's email.
     * @param guiCustomer GUI from where GUIListAssignment is invoked.
     */
    public GUIListAssignments(CalendarState cs, HashMap<Integer, Assignment> listAssignment,  String email, GUIHome guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        guiListAssignments = this;

        guiCustomer.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiCustomer.setEnabled(true);
            }
        });

        this.listAssignment = listAssignment;
        this.email = email;

        initComponents(cs, guiCustomer);
    }

    /**
     * Initialize the GUI components.
     * @param cs Calendar State.
     * @param guiCustomer GUI from where GUIListAssignment is invoked.
     */
    protected void initComponents(CalendarState cs, GUIHome guiCustomer){

        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.yesButtonText", "Yes");

        this.proxy = new CustomerProxy(email);
        this.listReview = proxy.getReviewList();


        assignmentNumber = listAssignment.size();
        reviewNumber = listReview.size();

        initArray(cs);

        contentPanel.setLayout(gridLayout);
        panelOut.setLayout(new BorderLayout());

        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            HashMap<Integer, Assignment> newListAssignment = assignmentBeforeToday(); //nuova lista degli appuntamente precedenti alla data attuale

            int j = 0;
            int review = 0;
            boolean haveAReview = false;//Fa vedere solo gli appuntamenti che non hanno ancora una recensione

            for(Integer i : newListAssignment.keySet()){
                Assignment a = null;

                a = listAssignment.get(i);
                haveAReview = false;

                for (Integer k : listReview.keySet()){
                    Review r = null;
                    r = listReview.get(k);
                    if(a.getCode()== r.getCode()){
                        haveAReview = true;
                        review++;
                    }
                }

                if(!haveAReview){

                    setComponents(setLabelString(cs, a, null), "Write a review", j);

                    buttonAction[j].addActionListener( e -> new GUIWriteReview(listAssignment.get(i), email, guiListAssignments).setVisible(true));
                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);


                }


                j++;
            }

            noAssignmentToReview(review,newListAssignment);

        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");

            if(reviewNumber == 0){
                createLabelInfo("There aren't reviews to show!");
            }
            else {

                int j = 0;
                for (Integer i : listReview.keySet()) {
                    Review r = null;
                    r = listReview.get(i);

                    setComponents(setLabelString(cs, null, r), "Delete review", j);
                    buttonAction[j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //proxy.removeReview((listAssignment.get(i)).getCode());
                            int action = (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm Delete Review", JOptionPane.YES_NO_OPTION));
                            if (action == JOptionPane.YES_OPTION) {
                                proxy.removeReview((listAssignment.get(i)).getCode());//TODO controllare!!
                                guiListAssignments.dispatchEvent(new WindowEvent(guiListAssignments, WindowEvent.WINDOW_CLOSING));
                            }
                        }

                    });

                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }


        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){ //DA controllare!!!!!
            setTitle("Your reviews");
            if(reviewNumber == 0){
                createLabelInfo("There aren't reviews to show!");
            }
            else
            {
                int j = 0;
                for(Integer i: listReview.keySet()){
                    Review r = null;
                    r = listReview.get(i);

                    setComponents(setLabelString(cs, null, r), "Show more", j);

                    buttonAction[j].addActionListener(e -> new GUIShowReview(listReview.get(i), guiListAssignments).setVisible(true));

                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }

        } else {
            if(assignmentNumber == 0){
                createLabelInfo("There aren't assignments to show!");
            }
            {
                int j = 0;
                for(Integer i : listAssignment.keySet()){
                    Assignment a = null;

                    a = listAssignment.get(i);

                    ActionListener showInfo = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(listAssignment.get(i), email, guiListAssignments);
                            assignmentInfo.setVisible(true);

                        }
                    };

                    setComponents(setLabelString(cs, a, null), "Info", j);
                    buttonAction[j].addActionListener(showInfo);
                    createPanelAssignment(a,j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }

        }

        panelOut.add(contentPanel, BorderLayout.NORTH);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);

        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                guiCustomer.setCalendarState(CalendarState.NORMAL);
            }
         });
    }


    /**
     * Create a panel, which contains the assignment's information.
     * @param a Reference to the object Assignment.
     * @param i index of the panel.
     */
    protected void createPanelAssignment(Assignment a, int i){
        infoPanel[i] = new JPanel();
        panelLabel = new JPanel();

        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1,1));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10,0,15, 10));

        infoPanel[i].setLayout(new BorderLayout());
        infoPanel[i].setMaximumSize(new Dimension(450,100));

        labelState[i] = createLabelState(a);

        panelLabel.add(labelState[i], BorderLayout.WEST);
        panelLabel.add(labelDescription[i], BorderLayout.CENTER);
        panelButtons.add(buttonAction[i]);
        infoPanel[i].add(panelLabel, BorderLayout.WEST);
        infoPanel[i].add(panelButtons, BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        infoPanel[i].setBorder(BorderFactory.createTitledBorder(""));

        contentPanel.add(infoPanel[i]);

    }

    /**
     * Create a panel, which contains the description of the assignment to review.
     * @param i index of the panel.
     */
    protected void createPanelReview (int i){
        infoPanel[i] = new JPanel();
        panelLabel = new JPanel();

        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1,1));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(30,0,20, 10));

        infoPanel[i].setLayout(new BorderLayout());
        labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

        panelLabel.add(labelDescription[i], BorderLayout.CENTER);
        panelButtons.add(buttonAction[i]);
        infoPanel[i].add(panelLabel, BorderLayout.WEST);
        infoPanel[i].add(panelButtons, BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        infoPanel[i].setBorder(BorderFactory.createTitledBorder(""));

        contentPanel.add(infoPanel[i]);
    }


    /**
     * Set the labelState color.
     * @param a Reference to the object Assignment.
     * @return JLabel Object with the proper color of the assignment.
     *          green: confirmed.
     *          yellow: to be confirmed.
     *          grey: old assignment
     */
    protected JLabel createLabelState(Assignment a){

        JLabel label;

        ImageIcon green = transformImage(new ImageIcon("images/Green_square.svg.png"), 30,30);
        ImageIcon gray = transformImage(new ImageIcon("images/gray_square.png"), 30,30);
        ImageIcon yellow = transformImage(new ImageIcon("images/yellow.jpg"), 30, 30);

        Boolean state = a.getState();

        if(dateBeforeToday(a.getDateEnd())){
            label = new JLabel(gray);

        }
        else if (state == Boolean.TRUE){
            label = new JLabel(green);
        }
        else {
            label = new JLabel(yellow);
        }

        return label;

    }

    /**
     * Set the icon with the right measures.
     * @param icon icon to set.
     * @param width new width.
     * @param height new height.
     * @return changed icon.
     */

    protected ImageIcon transformImage(ImageIcon icon, int width, int height){
        Image imageTransform = icon.getImage();
        Image newImage = imageTransform.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);

        return icon;
    }


    /**
     * Check if the date is before today's date.
     * @param date date to check.
     * @return return the value "true" if the date is before today's date.
     */

    protected boolean dateBeforeToday(Date date){
        Date todayDate = new Date(System.currentTimeMillis());

        if(date.before(todayDate)){
            return true;
        }

        return false;
    }

    /**
     * Initialize the arrays of the GUI components.
     * @param cs Calendar state.
     */
    protected void initArray(CalendarState cs){
        if(cs.equals(CalendarState.DELETING_REVIEW)|| cs.equals(CalendarState.SHOW_REVIEWS)){
            infoPanel = new JPanel[reviewNumber];
            labelDescription = new JLabel[reviewNumber];
            buttonAction = new JButton[reviewNumber];

        } else {
            infoPanel = new JPanel[assignmentNumber];
            labelDescription = new JLabel[assignmentNumber];
            buttonAction = new JButton[assignmentNumber];
            labelState = new JLabel[assignmentNumber];
        }

    }

    /**
     * Check the assignments' date.
     * @return list of assignments where the date is before today's date.
     */

    protected HashMap<Integer, Assignment> assignmentBeforeToday(){
        HashMap<Integer, Assignment> newListAssignment = new HashMap<>();
        for(Integer i : listAssignment.keySet()){
            Assignment a = null;
            a = listAssignment.get(i);
            if(dateBeforeToday(a.getDateEnd())){
                newListAssignment.put(a.getCode(), a);
            }

        }

        return newListAssignment;

    }

    /**
     * Set the frame for the case in which there aren't assignments to review.
     * @param number number of assignments already reviewed.
     * @param listAssignment list of assignments.
     */
    protected void noAssignmentToReview(int number, HashMap<Integer, Assignment> listAssignment){
        if(number == listAssignment.size()){

            JLabel noReviewLabel = new JLabel("There aren't assignments to review!");
            setSize(WIDTH, 200);
            panelOut.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(60,100,10,30));
            contentPanel.add(noReviewLabel, BorderLayout.CENTER);
        }

    }


    /**
     * Initialize the map of string for description label.
     * @param a assignment.
     * @param r review.
     */
    protected void setLabelStringMap(Assignment a, Review r){
        strLabel = new HashMap<>();

        String nameDogSitter = "";
        String surnameDogSitter = "";

        String dateStringReview ="";
        String dateStringStartAssigment = "";

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        if(r!=null){
            Date reviewDate = r.getDate();
            dateStringReview = date.format(reviewDate);
            strLabel.put(CalendarState.SHOW_REVIEWS,"<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.starsRating() + "</html>" );
            strLabel.put(CalendarState.DELETING_REVIEW, "<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.starsRating() + "</html>");
        }


        if(a!=null){
            Date startAssignment = a.getDateStart();
            dateStringStartAssigment = date.format(startAssignment);
            nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
            surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
            strLabel.put(CalendarState.NORMAL, "Assignment with " + nameDogSitter + " " + surnameDogSitter);
            strLabel.put(CalendarState.REVIEWING, "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "<br/>" + dateStringStartAssigment +  "</html>");
        }

    }

    /**
     * select the string from the map of predefined strings for description label
     * @param calendarState Calendar State.
     * @param a assignment.
     * @param r review.
     * @return requested string.
     */
    protected String setLabelString(CalendarState calendarState, Assignment a, Review r){
        setLabelStringMap(a, r);
        return strLabel.get(calendarState);

    }

    /**
     * Initialize infoPanel components.
     * @param strLabel labelDescription string.
     * @param strButton buttonAction string.
     * @param i index of the array.
     */
    protected void setComponents( String strLabel, String strButton, int i){
        labelDescription[i]= new JLabel(strLabel);
        buttonAction[i]= new JButton(strButton);


    }

    /**
     * Create a predefined frame for the case in which there aren't reviews or assignments.
     * @param stringLabel string for the message in the frame.
     */
    protected void createLabelInfo(String stringLabel){
        JLabel noReviewLabel = new JLabel(stringLabel);
        setSize(WIDTH, 200);
        panelOut.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60,100,10,30));
        contentPanel.add(noReviewLabel, BorderLayout.CENTER);

    }

}
