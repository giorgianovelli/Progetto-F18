/**
 * The base class for the home of customer and dog sitter.
 */

package client.gui;

import client.clientEnumerations.CalendarAction;
import client.clientEnumerations.MenuBarAction;
import client.proxy.Proxy;
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

import static client.Calendar.getNDayOfMonth;

public abstract class GUIHome extends JFrame{

    /**
     * The max number of days in a month.
     */
    final int NDAYMONTH = 31;

    /**
     * The number of days in a week.
     */
    final int NDAYWEEK = 7;

    /**
     * The number of empty label to add to the calendar.
     */
    final int NEMPTYLABEL = 11;

    /**
     * The number of empty label to add to the calendar.
     */
    final int NCALENDARCELLS = 49;

    /**
     * The max number of assignment that can be shown in the today's assignments panel.
     */
    final int MAXVISIBLETODAYASSIGNMENT = 5;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * The Menu bar.
     */
    protected JMenuBar menuBar = new JMenuBar();

    /**
     * The menu item "Canibau".
     */
    protected JMenu menuFile = new JMenu("Canibau");

    /**
     * The menu item "Quit".
     */
    protected JMenuItem menuItemExit = new JMenuItem("Quit");

    /**
     * The menu item "Logout".
     */
    protected JMenuItem menuItemLogout = new JMenuItem("Logout");

    /**
     * The menu item "Assignment".
     */
    protected JMenu menuAssignment = new JMenu("Assignment");

    /**
     * The menu item "Show all assignments".
     */
    protected JMenuItem menuItemShowAssignments = new JMenuItem("Show all assignments");

    /**
     * The menu item "Review".
     */
    protected JMenu menuReview = new JMenu("Review");

    /**
     * The menu item "Show all reviews".
     */
    protected JMenuItem menuItemShowReviews = new JMenuItem("Show all reviews");

    /**
     * The menu item "Settings".
     */
    protected JMenu menuSettings = new JMenu("Settings");

    /**
     * The menu item "Account".
     */
    protected JMenuItem menuItemAccount = new JMenuItem("Account");

    /**
     * The menu item "Change Password".
     */
    protected JMenuItem menuItemChangePassword = new JMenuItem("Change Password");

    /**
     * The menu item "?"
     */
    protected JMenu menuExtra = new JMenu("?");

    /**
     * The menu item "Info".
     */
    protected JMenuItem menuItemInfo = new JMenuItem("Info");

    /**
     * The menu item "Credits".
     */
    protected JMenuItem menuItemAwards = new JMenuItem("Credits");

    /**
     * The panel that contains the list of today's assignments.
     */
    protected JPanel panelToday = new JPanel();

    /**
     * The label that show the caption "Today's assignments".
     */
    protected JLabel labelTodayAssignments = new JLabel("Today's assignments");

    /**
     * The array of buttons for each assignment.
     */
    protected JButton buttonTodayAssignment[];

    /**
     * The button that shows the complete list of today's assignments.
     */
    protected JButton buttonShowMoreTodayAssignments = new JButton("Show more");

    /**
     * The array of empty label to add to the calendar.
     */
    protected JLabel labelEmptyTodayAssignments[];

    /**
     * The number of today's assignments.
     */
    protected int nTodayAssignments = 0;

    /**
     * The panel that contains panelDateCalendar and panelGridCalendar.
     */
    protected JPanel calendar = new JPanel();

    /**
     * The panel that contains the buttons to control calendar and the selected month.
     */
    protected JPanel panelDateCalendar = new JPanel();

    /**
     * The panel that contains all the buttons for each days of the month.
     */
    protected JPanel panelGridCalendar = new JPanel();

    /**
     * The array of labels that show the days of the week.
     */
    protected JLabel labelDay[];

    /**
     * The array of buttons for each day of the month.
     */
    protected JButton buttonDay[];

    /**
     * The array of empty labels.
     */
    protected JLabel labelEmpty[];

    /**
     * The button to go to the previous year.
     */
    protected JButton buttonPreviousYear = new JButton("Previous year");

    /**
     * The button to go to the previous month.
     */
    protected JButton buttonPreviousMonth = new JButton("Previous month");

    /**
     * The button to go to the next year.
     */
    protected JButton buttonNextYear = new JButton("Next year");

    /**
     * The button to go to the next month.
     */
    protected JButton buttonNextMonth = new JButton("Next month");

    /**
     * The label that shows the selected month and year.
     */
    protected JLabel labelDateMonthYear = new JLabel("08/2019", SwingConstants.CENTER);

    /**
     * The calendar's state.
     */
    protected CalendarState calendarState = CalendarState.NORMAL;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The HashSet that contains the code for the first five assignments of today.
     */
    protected HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<>();



    /**
     * Create a new home for the user specified by the email.
     * @param email the user's email.
     * @throws ParseException
     */
    public GUIHome(String email) throws ParseException {
        setTitle("CaniBau");
        int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.9);
        int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.9);
        setSize(width, height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        panelToday.setBackground(new Color(224, 224, 235));
        panelGridCalendar.setBackground(new Color(236, 242, 249));
        panelDateCalendar.setBackground(new Color(236, 242, 249));
        panelGridCalendar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.email = email;
    }


    /**
     * Start the calendar.
     * @param cal the calendar's ActionListener.
     * @param ctrlCal the calendar control buttons' ActionListener.
     * @param proxy the client proxy.
     * @throws ParseException
     */
    protected void startCalendar(ActionListener cal, ActionListener ctrlCal, Proxy proxy) throws ParseException {
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
        initializeCalendar(proxy);

        buttonPreviousMonth.addActionListener(ctrlCal);
        buttonNextMonth.addActionListener(ctrlCal);
        buttonPreviousYear.addActionListener(ctrlCal);
        buttonNextYear.addActionListener(ctrlCal);
    }


    /**
     * Initialize the calendar with the current month and year.
     * @param proxy the client proxy
     * @throws ParseException
     */
    protected void initializeCalendar(Proxy proxy) throws ParseException {
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        Date currentMonth = new Date();
        int monthNumber = Integer.parseInt(dateMonth.format(currentMonth));
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date currentYear = new Date();
        labelDateMonthYear.setText(dateMonth.format(currentMonth) + "/" + dateYear.format(currentYear));
        updateCalendar(monthNumber, proxy);
    }


    /**
     * Go to the previous month
     * @param proxy the client proxy
     * @throws ParseException
     */
    protected void goBackMonthCalendar(Proxy proxy) throws ParseException {
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

        updateCalendar(monthNumber, proxy);
    }


    /**
     * Go to the previous year.
     * @param proxy the client proxy.
     * @throws ParseException
     */
    protected void goBackYearCalendar(Proxy proxy) throws ParseException {
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

        updateCalendar(monthNumber, proxy);
    }


    /**
     * Go to the next month.
     * @param proxy the client proxy.
     * @throws ParseException
     */
    protected void goForwardMonthCalendar(Proxy proxy) throws ParseException {
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

        updateCalendar(monthNumber, proxy);
    }


    /**
     * Go to the next year.
     * @param proxy the client proxy
     * @throws ParseException
     */
    protected void goForwardYearCalendar(Proxy proxy) throws ParseException {
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

        updateCalendar(monthNumber, proxy);
    }


    //TODO refactor!
    protected void updateCalendar(int monthNumber, Proxy proxy) throws ParseException {
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
        int nd = getNDayOfMonth(monthNumber, currentDate);

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
            showAssignmentsOnCalendar(email, proxy);
        }
    }


    /**
     * Open the list of assignments.
     * @param proxy the client proxy.
     */
    protected void openListAssignment(Proxy proxy){
        GUIListAssignments guiListAssignments = new GUIListAssignments(calendarState, proxy.getAssignmentList(), email, this);
        guiListAssignments.setVisible(true);

    }


    /**
     * Set the calendar's state.
     * @param cs the calendar's state.
     */
    public void setCalendarState(CalendarState cs){
        calendarState = cs;
    }


    /**
     * Enable and disable buttons related to the days in the past.
     * @throws ParseException
     */
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


    /**
     * Show the assignments of the month on the calendar coloring the day's button where there is at least an assignment.
     * @param email the user's email.
     * @param proxy the client proxy.
     */
    protected void showAssignmentsOnCalendar(String email, Proxy proxy){
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


    /**
     * Get the number of the today's assignments.
     * @param proxy the client proxy.
     * @return the number of today's assignments.
     */
    protected int getNDailyAssignments(Proxy proxy){
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


    /**
     * Dispose the today's assignments in the list.
     * @return the number of today's assignments shown.
     */
    protected int disposeTodayAssignmentList(){
        int i;
        int nShownTodayAssignments = 0;
        if (nTodayAssignments <= MAXVISIBLETODAYASSIGNMENT){
            buttonTodayAssignment = new JButton[nTodayAssignments];
            for (i = 0; i < nTodayAssignments; i++){
                disposeButtonsTodayAssignment(i);
            }
        } else {
            buttonTodayAssignment = new JButton[MAXVISIBLETODAYASSIGNMENT];
            for (i = 0; i < MAXVISIBLETODAYASSIGNMENT; i++){
                disposeButtonsTodayAssignment(i);
            }
        }

        if ((nTodayAssignments > 0) && (nTodayAssignments <= MAXVISIBLETODAYASSIGNMENT)){
            labelEmptyTodayAssignments = new JLabel[MAXVISIBLETODAYASSIGNMENT - nTodayAssignments + 1];
            for (i = 0; i < MAXVISIBLETODAYASSIGNMENT - nTodayAssignments + 1; i++){
                labelEmptyTodayAssignments[i] = new JLabel();
                panelToday.add(labelEmptyTodayAssignments[i]);
                nShownTodayAssignments = nTodayAssignments;
            }
        } else if (nTodayAssignments > MAXVISIBLETODAYASSIGNMENT){
            panelToday.add(buttonShowMoreTodayAssignments);
            nShownTodayAssignments = MAXVISIBLETODAYASSIGNMENT;
        } else {
            labelEmptyTodayAssignments = new JLabel[1];
            labelEmptyTodayAssignments[0] = new JLabel("No assignment to show", SwingConstants.CENTER);
            panelToday.add(labelEmptyTodayAssignments[0]);
            nShownTodayAssignments = 0;
        }
        return nShownTodayAssignments;
    }


    /**
     * Dispose each button in the list of today's assignments.
     * @param i the position of the today's assignment to be shown.
     */
    private void disposeButtonsTodayAssignment(int i){
        buttonTodayAssignment[i] = new JButton(" ");
        buttonTodayAssignment[i].setBackground(new Color(179, 237, 255));
        buttonTodayAssignment[i].setOpaque(true);
        buttonTodayAssignment[i].setBorderPainted(false);
        //buttonTodayAssignment[i].setDisplayedMnemonicIndex(i);
        panelToday.add(buttonTodayAssignment[i]);
    }


    /**
     * Execute the action of the calendar control button pressed.
     * @param cmd the caption of the button pressed.
     * @param proxy the client proxy.
     */
    protected void execCalendarAction(String cmd, Proxy proxy){
        try {
            cmd = cmd.toUpperCase();
            cmd = cmd.replace(" ", "");
            CalendarAction calendarAction = CalendarAction.valueOf(cmd);
            switch (calendarAction){
                case NEXTYEAR:
                    goForwardYearCalendar(proxy);
                    break;
                case NEXTMONTH:
                    goForwardMonthCalendar(proxy);
                    break;
                case PREVIOUSYEAR:
                    goBackYearCalendar(proxy);
                    break;
                case PREVIOUSMONTH:
                    goBackMonthCalendar(proxy);
                    break;
                case SHOWMORE:
                    String strTodayDate;
                    Date todayDate = new Date();
                    GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
                    guiDailyAssignments.setVisible(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * Dispose the menu bar.
     */
    protected abstract void disposeMenuBar();


    /**
     * Dispose the panel of today's assignments.
     * @param proxy the client proxy
     * @return the number of today's assigments shown.
     */
    protected int disposeTodayPanel(Proxy proxy){
        panelToday.setLayout(new GridLayout(7, 1, 5, 5));
        panelToday.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        labelTodayAssignments.setForeground(new Color(0,0, 255));
        labelTodayAssignments.setFont(new Font("Serif", Font.BOLD, 24));
        panelToday.add(labelTodayAssignments);

        //restituisce il numero di appuntamenti del giorno
        nTodayAssignments = getNDailyAssignments(proxy);

        int nShownTodayAssignments = disposeTodayAssignmentList();

        //carica i primi 5 appuntamenti del giorno
        loadTheFirstFiveAssignments(nShownTodayAssignments);


        add(panelToday, BorderLayout.EAST);
        return nShownTodayAssignments;
    }


    /**
     * Load in an HashMap the first five today's assignments to be shown.
     * @param nShownTodayAssignments the number of today's assignments to be shown.
     */
    protected abstract void loadTheFirstFiveAssignments(int nShownTodayAssignments);


    /**
     * Perform an action when a day's button is pressed.
     * @param cae the day's button ActionEvent.
     */
    protected abstract void clickOnCalendarButton(ActionEvent cae);


    /**
     * Perform an action when a calendar control button is pressed.
     * @param ctrlAe the calendar control button's ActionEvent.
     * @param proxy the client proxy.
     */
    protected void clickOnCtrlCalendarButton(ActionEvent ctrlAe, Proxy proxy){
        if (!(ctrlAe.getActionCommand().equals(""))){
            JButton pressedButton = (JButton) ctrlAe.getSource();
            execCalendarAction(pressedButton.getText(), proxy);
        }
    }


    /**
     * Open a window that shows all user's assignments.
     */
    public abstract void showAllAssignments();


    /**
     * Open a window that shows all user's reviews.
     */
    public abstract void showAllReviews();


    /**
     * Open the account settings.
     */
    public abstract void accountSettings();


    /**
     * Open a window that permits to the user to change his password.
     */
    public abstract void changePassword();


    /**
     * Open a windows that shows the informations about the software.
     */
    public void info(){
        GUIInfo info = new GUIInfo();
        info.setVisible(true);
    }


    /**
     * Open a windows that shows the software developer team.
     */
    public void credits(){
        GUIAwards credits = new GUIAwards();
        credits.setVisible(true);
    }


    /**
     * Perform an action when the user click on a menu bar item.
     * @param menuAe the menu items' ActionEvent.
     */
    protected void clickOnMenuBarItem(ActionEvent menuAe){
        JMenuItem pressedItem = (JMenuItem) menuAe.getSource();
        String cmd = pressedItem.getText().toUpperCase();
        cmd = cmd.replace(" ", "");
        MenuBarAction execMenuBarAction = MenuBarAction.valueOf(cmd);
        execMenuBarAction.execute(this);
    }


    /**
     * Get the HashSet of the codes of the first five today's assignments to be shown.
     * @param nShownAssignments the number of today's assignments to be shown.
     * @param assignmentList the user's list of assignment's.
     * @return
     */
    protected HashSet<Integer> getCodeFirstFiveAssignments(int nShownAssignments, HashMap<Integer, Assignment> assignmentList){
        int i = 0;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();

        HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<>();

        for (Integer key : assignmentList.keySet()) {
            Assignment a = assignmentList.get(key);
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
        return codeFirstFiveAssignmentsList;
    }


    /**
     * Perform an action when the user's click on a today's assignment.
     * @param todayAssignmentAe the today's assignments ActionEvent.
     * @param proxy the client proxy.
     * @param guiHome the GUIHome in use.
     */
    protected void clickOnTodayAssignment(ActionEvent todayAssignmentAe, Proxy proxy, GUIHome guiHome){
        if (!(todayAssignmentAe.getActionCommand().equals(""))){
            JButton pressedButton = (JButton) todayAssignmentAe.getSource();
            StringTokenizer cmdToken = new StringTokenizer(buttonTodayAssignment[0].getText(), " ");
            String cmd = cmdToken.nextToken();
            if (cmd.equals("Assignment")){
                calendarState = CalendarState.NORMAL;
                //cancel();
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
                if (guiHome instanceof GUICustomer){
                    GUIAssignmentInformationCustomer guiAssignment = new GUIAssignmentInformationCustomer(a, email, (GUICustomer) guiHome);
                    guiAssignment.setVisible(true);
                } else {
                    GUIAssignmentInformationDogsitter guiAssignment = new GUIAssignmentInformationDogsitter(a, email, (GUIDogSitter) guiHome);
                    guiAssignment.setVisible(true);
                }
            }
        }
    }

}


