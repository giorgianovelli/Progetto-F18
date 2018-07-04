package client.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import enumeration.CalendarState;
import server.dateTime.WeekDays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import static client.Calendar.getNDayofMonth;
import static client.Calendar.isLeap;
import static server.tools.StringManipulator.capitalizeFirstLetter;

public class GUIHome extends JFrame{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    final int NDAYMONTH = 31;
    final int NDAYWEEK = 7;
    final int NEMPTYLABEL = 11;
    final int NCALENDARCELLS = 49;
    final int MAXVISIBLETODAYASSIGNMENT = 5;

    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    protected JMenuBar menuBar = new JMenuBar();
    protected JMenu menuFile = new JMenu("Canibau");
    protected JMenuItem menuItemExit = new JMenuItem("Quit");
    protected JMenuItem menuItemLogout = new JMenuItem("Logout");
    protected JMenu menuAssignment = new JMenu("Assignment");
    protected JMenuItem menuItemShowAssignments = new JMenuItem("Show all assignments");
    protected JMenu menuReview = new JMenu("Review");
    protected JMenuItem menuItemShowReviews = new JMenuItem("Show all reviews");
    protected JMenu menuSettings = new JMenu("Settings");
    protected JMenuItem menuItemAccount = new JMenuItem("Account");
    protected JMenuItem menuItemChangePassword = new JMenuItem("Change Password");
    protected JMenu menuExtra = new JMenu("?");
    protected JMenuItem menuItemInfo = new JMenuItem("Info");
    protected JMenuItem menuItemAwards = new JMenuItem("Credits");
    protected JMenuItem menuItemCancel = new JMenuItem("Cancel");

    protected JPanel panelToday = new JPanel();
    protected JLabel labelTodayAssignments = new JLabel("Today's assignments");
    protected JButton buttonTodayAssignment[];
    protected JButton buttonShowMoreTodayAssignments = new JButton("Show more");
    protected JLabel labelEmptyTodayAssignments[];
    protected int nTodayAssignments = 0;

    protected JPanel calendar = new JPanel();
    protected JPanel panelDateCalendar = new JPanel();
    protected JPanel panelGridCalendar = new JPanel();
    protected JLabel labelDay[];
    protected JButton buttonDay[];
    protected JLabel labelEmpty[];
    protected JButton buttonPreviousYear = new JButton("<<");
    protected JButton buttonPreviousMonth = new JButton("<");
    protected JButton buttonNextYear = new JButton(">>");
    protected JButton buttonNextMonth = new JButton(">");
    protected JLabel labelDateMonthYear = new JLabel("08/2019", SwingConstants.CENTER);
    protected CalendarState calendarState = CalendarState.NORMAL;
    //public static GUINewAssignment guiNewAssignment;

    private CustomerProxy proxy;
    private String email;
    private HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<Integer>();


    public GUIHome(String email) throws ParseException {
        setTitle("CaniBau");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new CustomerProxy(email);
    }

    protected void startCalendar(ActionListener cal, ActionListener ctrlCal) throws ParseException {
        calendar.setLayout(new BorderLayout());
        panelDateCalendar.setLayout(new GridLayout(1, 5));
        panelDateCalendar.add(buttonPreviousYear);
        panelDateCalendar.add(buttonPreviousMonth);
        panelDateCalendar.add(labelDateMonthYear);
        panelDateCalendar.add(buttonNextMonth);
        panelDateCalendar.add(buttonNextYear);
        calendar.add(panelDateCalendar, BorderLayout.NORTH);
        panelGridCalendar.setLayout((new GridLayout(7, 7, 5, 5)));
        calendar.add(panelGridCalendar, BorderLayout.CENTER);
        labelDay = new JLabel[NDAYWEEK];
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        //ottiene informazioni sul sistema operativo utilizzato
        String os = System.getProperty("os.name");

        buttonDay = new JButton[NDAYMONTH];
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i] = new JButton(Integer.toString(i + 1));
            buttonDay[i].setBackground(new Color(204, 230, 255));
            buttonDay[i].addActionListener(cal);
            if (os.equals("Mac OS X")){
                buttonDay[i].setOpaque(true);
                buttonDay[i].setBorderPainted(false);
            }
        }

        add(calendar, BorderLayout.CENTER);
        initializeCalendar();

        buttonPreviousMonth.addActionListener(ctrlCal);
        buttonNextMonth.addActionListener(ctrlCal);
        buttonPreviousYear.addActionListener(ctrlCal);
        buttonNextYear.addActionListener(ctrlCal);
    }

    protected void initializeCalendar() throws ParseException {
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        Date currentMonth = new Date();
        int monthNumber = Integer.parseInt(dateMonth.format(currentMonth));
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date currentYear = new Date();
        labelDateMonthYear.setText(dateMonth.format(currentMonth) + "/" + dateYear.format(currentYear));
        updateCalendar(monthNumber);
    }

    protected void goBackMonthCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber) - 1;
        if (monthNumber == 0){
            monthNumber = 12;
            Integer newYear = Integer.parseInt(strYear) - 1;
            strYear = newYear.toString();
        }
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    protected void goBackYearCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) - 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    protected void goForwardMonthCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber) + 1;
        if (monthNumber == 13){
            monthNumber = 1;
            Integer newYear = Integer.parseInt(strYear) + 1;
            strYear = newYear.toString();
        }
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    protected void goForwardYearCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) + 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    protected void updateCalendar(int monthNumber) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "01/" + labelDateMonthYear.getText();
        Date currentDate = date.parse(strDate);
        SimpleDateFormat dateDayNumber = new SimpleDateFormat("u");
        dateDayNumber.setLenient(false);
        String stringDayNumber = dateDayNumber.format(currentDate);
        int dayNumber = Integer.parseInt(stringDayNumber);
        int i;
        int ie;
        int ne = 0;
        int cc = NDAYWEEK;
        labelEmpty= new JLabel[NEMPTYLABEL];
        switch (dayNumber){
            case 1:
                ne = 0;
                break;
            case 2:
                ne = 1;
                break;
            case 3:
                ne = 2;
                break;
            case 4:
                ne = 3;
                break;
            case 5:
                ne = 4;
                break;
            case 6:
                ne = 5;
                break;
            case 7:
                ne = 6;
                break;
        }
        for (ie = 0; ie < ne; ie++){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
        }

        i = 0;
        int nd = getNDayofMonth(monthNumber, currentDate);

        for (i = 0; i < nd; i++){
            panelGridCalendar.add(buttonDay[i]);
            cc++;
        }

        while (cc < NCALENDARCELLS){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
        }

        if ((calendarState.equals(CalendarState.ADDING)) || (calendarState.equals(CalendarState.REMOVING))){
            enableDisableDateButtonAssignment();
        }

        for (i = 0; i < NDAYMONTH; i++){
            if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
                buttonDay[i].setBackground(new Color(204, 230, 255));
            }

            String strCurrentDate = date.format(new Date());
            String dateButton;
            int nDay = Integer.parseInt(buttonDay[i].getText());
            if (nDay < 10){
                dateButton = "0" + buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
            } else {
                dateButton = buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
            }
            if (dateButton.equals(strCurrentDate)){
                buttonDay[i].setForeground(new Color(255, 0, 0));
            } else {
                buttonDay[i].setForeground(new Color(0, 0, 0));
            }
        }

        if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
            showAssignmentOnCalendar(email);
        }
    }

    /*protected void cancel(){
        calendarState = CalendarState.NORMAL;
        int i;
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i].setBackground(new Color(204, 230, 255));
            buttonDay[i].setEnabled(true);
        }
        menuAssignment.setVisible(true);
        menuReview.setVisible(true);
        menuSettings.setVisible(true);
        menuExtra.setVisible(true);
        menuItemCancel.setVisible(false);

        if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
            showAssignmentOnCalendar(email);
        }
    }*/

    protected void openListAssignment(){
        GUIListAssignments guiListAssignments = new GUIListAssignments(calendarState, proxy.getAssignmentList(), email, this);
        guiListAssignments.setVisible(true);

    }

    public void setCalendarState(CalendarState cs){
        calendarState = cs;
    }

    protected void enableDisableDateButtonAssignment() throws ParseException {
        int i;
        String strDay;
        String strSelectedDate;
        Date selectedDate;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        for (i = 0; i < NDAYMONTH; i++){
            Integer day = Integer.parseInt(buttonDay[i].getText());
            if (day < 10){
                strDay = "0" + day;
            } else {
                strDay = day.toString();
            }
            strSelectedDate = strDay + "/" + labelDateMonthYear.getText();
            selectedDate = formatDate.parse(strSelectedDate);
            if (selectedDate.before(currentDate)){
                buttonDay[i].setEnabled(false);
            } else {
                buttonDay[i].setEnabled(true);
            }
        }
    }

    protected void showAssignmentOnCalendar(String email){
        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        boolean included = false;
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            Date dateStart = a.getDateStart();
            Date dateEnd = a.getDateEnd();
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            String strDateStart = date.format(dateStart);
            String strDateEnd = date.format(dateEnd);
            int i;
            String strButtonDate;
            for (i = 0; i < NDAYMONTH; i++){
                int day = i + 1;
                if (day < 10){
                    strButtonDate = "0" + buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
                } else {
                    strButtonDate = buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
                }
                if (strButtonDate.equals(strDateStart) ){
                    buttonDay[i].setBackground(Color.ORANGE);
                    included = true;
                }
                if (strButtonDate.equals(strDateEnd) ){
                    buttonDay[i].setBackground(Color.ORANGE);
                    included = false;
                }
                if (included){
                    buttonDay[i].setBackground(Color.ORANGE);
                }
            }
        }
    }

    protected int getNDailyAssignments(){
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        int nAssignments = 0;
        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            String strDateStart = date.format(a.getDateStart());
            String strTodayDate = date.format(todayDate);
            String strDateEnd = date.format(a.getDateEnd());
            try {
                Date dayStart = date.parse(strDateStart);
                Date dayEnd = date.parse(strDateEnd);
                Date today = date.parse(strTodayDate);

                if ((today.after(dayStart) || today.equals(dayStart)) && (today.before(dayEnd)) || today.equals(dayEnd)){
                    nAssignments++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return nAssignments;
    }

    protected void loadTheFirstFiveAssignments(int nShownAssignments){
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
            String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
            String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
            buttonTodayAssignment[n].setText("Assignment with " + capitalizeFirstLetter(nameDogSitter) + " " + capitalizeFirstLetter(surnameDogSitter));
            buttonTodayAssignment[n].setDisplayedMnemonicIndex(key);
            n++;
        }
    }


}


