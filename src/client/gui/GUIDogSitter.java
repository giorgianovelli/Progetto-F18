package client.gui;

import client.proxy.DogSitterProxy;
import enumeration.CalendarState;
import server.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class GUIDogSitter extends GUIHome{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JMenuItem menuItemReplyToReview = new JMenuItem("Reply to a review");

    private String email;
    private DogSitterProxy proxy;

    public GUIDogSitter(String email) throws ParseException  {
        super(email);
        setTitle("CaniBau (Dog sitter)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new DogSitterProxy(email);

        initComponents();
    }

    private void initComponents() throws ParseException {
        disposeMenuBar();

        panelToday.setLayout(new GridLayout(7, 1, 5, 5));
        panelToday.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        labelTodayAssignments.setForeground(new Color(0,0, 255));
        labelTodayAssignments.setFont(new Font("Serif", Font.BOLD, 24));
        panelToday.add(labelTodayAssignments);
        int i;

        //restituisce il numero di appuntamenti del giorno
        nTodayAssignments = getNDailyAssignments(proxy);

        int nShownTodayAssignments = disposeTodayAssignmentList();

        //carica i primi 5 appuntamenti del giorno
        loadTheFirstFiveAssignments(nShownTodayAssignments);


        add(panelToday, BorderLayout.EAST);

        ActionListener cal = new ActionListener() {
            ;

            @Override
            public void actionPerformed(ActionEvent cae) {
                JButton pressedButton = (JButton) cae.getSource();
                String strTodayDate;
                if (Integer.parseInt(pressedButton.getText()) < 10){
                    strTodayDate =  "0" + pressedButton.getText() + "/" + labelDateMonthYear.getText();
                } else {
                    strTodayDate = pressedButton.getText() + "/" + labelDateMonthYear.getText();
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                try {
                    todayDate = dateFormat.parse(strTodayDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if ((!(cae.getActionCommand().equals(""))) && ((calendarState.equals(CalendarState.NORMAL)) || (calendarState.equals(CalendarState.REMOVING)))){
                    GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
                    guiDailyAssignments.setVisible(true);
                }

            }
        };

        ActionListener ctrlCal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ctrlAe) {
                if (!(ctrlAe.getActionCommand().equals(""))){
                    JButton pressedButton = (JButton) ctrlAe.getSource();
                    execCalendarAction(pressedButton.getText(), proxy);
                }
            }
        };

        ActionListener menuAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent menuAe) {
                if (menuAe.getActionCommand().equals("Quit")){
                    System.exit(0);
                }

                if (menuAe.getActionCommand().equals("Logout")){
                    GUILogin guiLogin = new GUILogin();
                    guiLogin.setVisible(true);
                    setVisible(false);
                }

                if (menuAe.getActionCommand().equals("Show all assignments")){
                    //TODO da implementare
                }

                if (menuAe.getActionCommand().equals("Reply to a review")){
                    //TODO da implementare
                }

                if (menuAe.getActionCommand().equals("Show all reviews")){
                    //TODO da implementare
                }

                if (menuAe.getActionCommand().equals("Account")){
                    //TODO da implementare
                }

                if(menuAe.getActionCommand().equals("Change Password")) {
                    //TODO da implementare
                }

                if (menuAe.getActionCommand().equals("Info")){
                    GUIInfo info = new GUIInfo();
                    info.setVisible(true);
                }

                if (menuAe.getActionCommand().equals("Credits")){
                    GUIAwards credits = new GUIAwards();
                    credits.setVisible(true);
                }


            }
        };

        ActionListener todayAssignmentsAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent todayAssignmentAe) {
                if (!(todayAssignmentAe.getActionCommand().equals(""))){
                    JButton pressedButton = (JButton) todayAssignmentAe.getSource();
                    StringTokenizer cmdToken = new StringTokenizer(buttonTodayAssignment[0].getText(), " ");
                    String cmd = cmdToken.nextToken();
                    if (cmd.equals("Assignment")){
                        calendarState = CalendarState.NORMAL;
                        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
                        Date date = new Date();
                        String strMonthNumber = dateMonth.format(date);
                        int monthNumber = Integer.parseInt(strMonthNumber);
                        try {
                            updateCalendar(monthNumber, proxy);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
                        Assignment a = listAssignment.get(pressedButton.getDisplayedMnemonicIndex());
                        //TODO da implementare
                    }
                }
            }
        };

        //prepara il calendario
        startCalendar(cal, ctrlCal, proxy);

        menuItemExit.addActionListener(menuAl);
        menuItemLogout.addActionListener(menuAl);
        menuItemShowAssignments.addActionListener(menuAl);
        menuItemReplyToReview.addActionListener(menuAl);
        menuItemShowReviews.addActionListener(menuAl);
        menuItemAccount.addActionListener(menuAl);
        menuItemChangePassword.addActionListener(menuAl);
        menuItemInfo.addActionListener(menuAl);
        menuItemAwards.addActionListener(menuAl);
        buttonShowMoreTodayAssignments.addActionListener(ctrlCal);

        for (i = 0; i < nShownTodayAssignments; i++){
            buttonTodayAssignment[i].addActionListener(todayAssignmentsAl);
        }

    }

    private void loadTheFirstFiveAssignments(int nShownAssignments){
        if (nShownAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        int i = 0;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();

        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();

        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            String strDateStart = date.format(a.getDateStart());
            String strDateEnd = date.format(a.getDateEnd());
            String strTodayDate = date.format(todayDate);
            try {
                Date dayStart = date.parse(strDateStart);
                Date dayEnd = date.parse(strDateEnd);
                Date today = date.parse(strTodayDate);
                if (((today.after(dayStart) || today.equals(dayStart)) && (today.before(dayEnd)) || today.equals(dayEnd)) && (i < nShownAssignments)){
                    codeFirstFiveAssignmentsList.add(key);
                    i++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int n = 0;
        for (Integer key : codeFirstFiveAssignmentsList) {
            Assignment a = listAssignment.get(key);
            String nameDogSitter = proxy.getCustomerNameOfAssignment(a.getCode());
            String surnameDogSitter = proxy.getCustomerSurnameOfAssignment(a.getCode());
            buttonTodayAssignment[n].setText("Assignment with " + capitalizeFirstLetter(nameDogSitter) + " " + capitalizeFirstLetter(surnameDogSitter));
            buttonTodayAssignment[n].setDisplayedMnemonicIndex(key);
            n++;
        }
    }

    protected void disposeMenuBar(){
        //Crea il menù
        menuBar.add(menuFile);
        menuFile.add(menuItemLogout);
        menuFile.add(menuItemExit);
        menuAssignment.add(menuItemShowAssignments);
        menuBar.add(menuAssignment);
        menuReview.add(menuItemReplyToReview);
        menuReview.add(menuItemShowReviews);
        menuBar.add(menuReview);
        menuSettings.add(menuItemAccount);
        menuSettings.add(menuItemChangePassword);
        menuBar.add(menuSettings);
        menuExtra.add(menuItemInfo);
        menuExtra.add(menuItemAwards);
        menuBar.add(menuExtra);
        add(menuBar, BorderLayout.NORTH);
    }
}
