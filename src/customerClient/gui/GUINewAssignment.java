package customerClient.gui;



import customerClient.CustomerProxy;
import server.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class GUINewAssignment extends JFrame{

    final int WIDTH = 800;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Panels

    private JPanel panelOut = new JPanel();
    private JPanel panelNoButtons = new JPanel();
    private JPanel panelBox = new JPanel();
    private JPanel panelCombo = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelLabel = new JPanel();
    private JPanel panelLabel2 = new JPanel();
    private JPanel panelButtons = new JPanel();
    private JPanel panelDogs = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(panelOut);
    private Date date;
    private String strDate;
    private String email;

    //Others

    private GridLayout gridLayout = new GridLayout(1,1);
    private JLabel labelMeetingPoint = new JLabel("Choose where you would like to meet the dogsitter: ");
    private JLabel labelDogs = new JLabel("Select your dogs: ");
    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");



    NewAssignmentText country = new NewAssignmentText("Country:                                               ");
    NewAssignmentText city = new NewAssignmentText("City:                                                       ");
    NewAssignmentText cap = new NewAssignmentText("Postal Code:                                        ");
    NewAssignmentText address = new NewAssignmentText("Address:                                              ");           // Perchè non mi va lo SwingCostants? Dà errore "illecit position" qualsiasi posizione io inserisca dentro panelData
    NewAssignmentText number = new NewAssignmentText("Number:                                               ");

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //Costruttore

    public GUINewAssignment(Date date, String email) {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.date = date;
        this.email = email;
        strDate = dateToString(date);
        NewAssignmentBox newAssignmentBox = new NewAssignmentBox(strDate);

        initComponents(newAssignmentBox);

        }

//________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    private void initComponents(NewAssignmentBox newAssignmentBox) {

        CustomerProxy customerProxy = new CustomerProxy(email);

        //Setting layout dei panel

        panelOut.setLayout(new BorderLayout());
        panelNoButtons.setLayout(new BorderLayout());
        panelNoButtons.setBorder(BorderFactory.createTitledBorder("Complete each field to look for a dogsitter: "));
        panelBox.setLayout(new BoxLayout(panelBox, 1));
        panelCombo.setLayout(new GridLayout(1, 1));
        panelAddress.setLayout(new GridLayout(5, 1, 30, 20));
        panelLabel.setLayout(new BorderLayout());
        panelLabel2.setLayout(new BorderLayout());
        panelDogs.setLayout(gridLayout);
        panelDogs.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelButtons.setLayout(new GridLayout(1, 2, 5, 0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        // Aggiunta dei pannelli al pannello più esterno

        panelOut.add(panelNoButtons, BorderLayout.NORTH); // Primo
        panelOut.add(panelButtons, BorderLayout.SOUTH); //Secondo

        //panelNoButtons: primo pannello

        panelNoButtons.add(panelBox, BorderLayout.NORTH);
        panelNoButtons.add(panelDogs, BorderLayout.CENTER);

        //PanelBox (fa parte del panelNoButtons)

        Dimension dimension = new Dimension(0, 20);
        panelBox.add(panelCombo);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelAddress);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel2);

        // panelDogs (fa parte del panelNoButtons)

        HashSet<Dog> hashDogs = customerProxy.getDogList();

        for (Dog dog: hashDogs) {
            NewAssignmentCheckBox dogCheckBox = new NewAssignmentCheckBox(dog.getName());
            panelDogs.add(dogCheckBox);
            gridLayout.setRows(gridLayout.getRows() + 1);
        }


        // Secondo pannello

        panelButtons.add(buttonCancel);
        panelButtons.add(buttonSearch);

        // Pannello ComboBox

        panelCombo.add(newAssignmentBox);



        // Pannello barre testuali

        panelAddress.add(country);
        panelAddress.add(city);
        panelAddress.add(cap);
        panelAddress.add(address);
        panelAddress.add(number);

        // Labels


        panelLabel.add(labelMeetingPoint, BorderLayout.LINE_START);
        panelLabel2.add(labelDogs, BorderLayout.LINE_START);

        // Scroll panel

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    public String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String string = simpleDateFormat.format(date);
        return string;
    }
}

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


class NewAssignmentBox extends JPanel{


    Date dateEnd;
    JComboBox<String> fdayList, tdayList;
    JComboBox<String> fmonthList, tmonthList;
    JComboBox<String> fyearList, tyearList;
    JComboBox<String> fhourList, thourList;
    JComboBox<String> fminuteList, tminuteList;
    JLabel fromLabel = new JLabel("From:");
    JLabel toLabel = new JLabel("To:");
    JLabel blanckLabel = new JLabel("");
    JLabel daysLabel = new JLabel("Day:");
    JLabel monthsLabel = new JLabel("Month:");
    JLabel yearsLabel = new JLabel("Year:");
    JLabel hoursLabel = new JLabel("Hour:");
    JLabel minutesLabel = new JLabel("Minute:");


    JLabel fromDayLabel = new JLabel("");
    JLabel fromMonthLabel = new JLabel("");
    JLabel fromYearLabel = new JLabel("");


    String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24","25","26","27","28","29","30","31"};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Costruttore NewAssignmentBox

    public NewAssignmentBox(String selectedDay){





        fhourList = new JComboBox<>(hour);
        fminuteList = new JComboBox<>(minute);
        tdayList = new JComboBox<>(day);
        tmonthList = new JComboBox<>(month);
        tyearList = new JComboBox<>(year);
        thourList = new JComboBox<>(hour);
        tminuteList = new JComboBox<>(minute);


        String[] selectedDaySplitted = selectedDay.split("/");

        fromDayLabel.setText(selectedDaySplitted[0]);
        fromMonthLabel.setText(selectedDaySplitted[1]);
        fromYearLabel.setText(selectedDaySplitted[2]);



        /*


        tmonthList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monthSeleted = String.valueOf(tmonthList.getSelectedItem());
                if (monthSeleted.equals("02")) {
                    day[28] = null;
                    day[29] = null;
                    day[30] = null;
                }



            }
        });

        */

        dateEnd = new Date();


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String day = String.valueOf(tdayList.getSelectedItem());
                String month = String.valueOf(tmonthList.getSelectedItem());
                String year = String.valueOf(tyearList.getSelectedItem());
                String toHour = String.valueOf(thourList.getSelectedItem());
                String toMinute = String.valueOf(tminuteList.getSelectedItem());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                dateEnd = new Date();
                try {
                    dateEnd = simpleDateFormat.parse(day + "/" + month + "/" + year + " " + toHour + ":" + toMinute);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        };

        tdayList.addActionListener(actionListener);
        tmonthList.addActionListener(actionListener);
        tyearList.addActionListener(actionListener);
        thourList.addActionListener(actionListener);
        tminuteList.addActionListener(actionListener);

        //System.out.println(dateEnd.toString());






        fhourList.setLightWeightPopupEnabled(false);
        fminuteList.setLightWeightPopupEnabled(false);
        tdayList.setLightWeightPopupEnabled(false);
        tmonthList.setLightWeightPopupEnabled(false);
        tyearList.setLightWeightPopupEnabled(false);
        thourList.setLightWeightPopupEnabled(false);
        tminuteList.setLightWeightPopupEnabled(false);







        setLayout(new GridLayout(3, 6, 10, 10));

        add(blanckLabel);
        add(daysLabel);
        add(monthsLabel);
        add(yearsLabel);
        add(hoursLabel);
        add(minutesLabel);

        add(fromLabel);
        add(fromDayLabel);
        add(fromMonthLabel);
        add(fromYearLabel);
        add(fhourList);
        add(fminuteList);

        add(toLabel);
        add(tdayList);
        add(tmonthList);
        add(tyearList);
        add(thourList);
        add(tminuteList);

    }

}



//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


class NewAssignmentText extends JPanel{
    JLabel label;
    TextField field = new TextField("",48);


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    // Costruttore

    public NewAssignmentText(String text){

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        label = new JLabel(text, SwingConstants.LEFT);
        add(label, gridBagConstraints);
        add(field, gridBagConstraints);
    }
}

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

class NewAssignmentCheckBox extends JPanel {

    JLabel labelName;
    JCheckBox checkBox;


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //Costruttore

    public NewAssignmentCheckBox(String text) {

        setLayout(new GridLayout(1,2));
        labelName = new JLabel(text);
        checkBox = new JCheckBox();
        add(labelName);
        add(checkBox);
    }
}
