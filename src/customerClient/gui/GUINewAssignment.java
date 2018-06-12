package customerClient.gui;



import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class GUINewAssignment extends JFrame{

    final int WIDTH = 600;
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

    //Others

    private GridLayout gridLayout = new GridLayout(1,1);
    private JLabel labelMeetingPoint = new JLabel("Choose where you would like to meet the dogsitter: ");
    private JLabel labelDogs = new JLabel("Select your dogs: ");
    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");


    NewAssignmentBox newAssignmentBox = new NewAssignmentBox();
    NewAssignmentText address = new NewAssignmentText("Address:                ");           // Perchè non mi va lo SwingCostants? Dà errore "illecit position" qualsiasi posizione io inserisca dentro panelData
    NewAssignmentText city = new NewAssignmentText("City:                         ");
    NewAssignmentText code = new NewAssignmentText("Code:                      ");
    NewAssignmentText country = new NewAssignmentText("Country:                 ");


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //Costruttore

    public GUINewAssignment(Date date, String email) {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);             //TODO reset false
        setLayout(new BorderLayout());

        initComponents();

        }

//________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    private void initComponents() {

        //Setting layout dei panel

        panelOut.setLayout(new BorderLayout());
        panelNoButtons.setLayout(new BorderLayout());
        panelNoButtons.setBorder(BorderFactory.createTitledBorder("Complete each field to look for a dogsitter: "));
        panelBox.setLayout(new BoxLayout(panelBox, 1));
        panelCombo.setLayout(new GridLayout(1,1));
        panelAddress.setLayout(new GridLayout(4, 1, 30, 20));
        panelLabel.setLayout(new BorderLayout());
        panelLabel2.setLayout(new BorderLayout());
        panelDogs.setLayout(gridLayout);
        panelDogs.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        panelButtons.setLayout(new GridLayout(1,2,5,0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 170, 20, 170));

        // Aggiunta dei pannelli al pannello più esterno

        panelOut.add(panelNoButtons, BorderLayout.NORTH); // Primo
        panelOut.add(panelButtons, BorderLayout.SOUTH); //Secondo

        //Primo pannello

        panelNoButtons.add(panelBox, BorderLayout.NORTH);
        panelNoButtons.add(panelDogs, BorderLayout.CENTER);

        //PanelBox (fa parte del primo pannello)

        Dimension dimension = new Dimension(0,20);
        panelBox.add(panelCombo);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelAddress);
        panelBox.add(Box.createRigidArea(dimension));
        panelBox.add(panelLabel2);


        for (int i = 0; i < 30; i++) {
            NewAssignmentCheckBox dog = new NewAssignmentCheckBox("Cane " + i);
            panelDogs.add(dog);
            gridLayout.setRows(gridLayout.getRows() + 1);
        }


        // Secondo pannello

        panelButtons.add(buttonCancel);
        panelButtons.add(buttonSearch);

        // Pannelli minori

        panelAddress.add(address);
        panelAddress.add(city);
        panelAddress.add(code);
        panelAddress.add(country);


        panelCombo.add(newAssignmentBox);

        panelLabel.add(labelMeetingPoint, BorderLayout.LINE_START);
        panelLabel2.add(labelDogs, BorderLayout.LINE_START);

        // Scroll panel

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }
}

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


class NewAssignmentBox extends JPanel{

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



    String[] day = new String[]{};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Costruttore NewAssignmentBox

    public NewAssignmentBox(){

        fhourList = new JComboBox<>(hour);
        fminuteList = new JComboBox<>(minute);
        tdayList = new JComboBox<>(day);
        tmonthList = new JComboBox<>(month);
        tyearList = new JComboBox<>(year);
        thourList = new JComboBox<>(hour);
        tminuteList = new JComboBox<>(minute);

        fhourList.setLightWeightPopupEnabled(false);
        fminuteList.setLightWeightPopupEnabled(false);
        tdayList.setLightWeightPopupEnabled(false);
        tmonthList.setLightWeightPopupEnabled(false);
        tyearList.setLightWeightPopupEnabled(false);
        thourList.setLightWeightPopupEnabled(false);
        tminuteList.setLightWeightPopupEnabled(false);


        setLayout(new GridLayout(3, 6, 10, 10));

        //Label

        add(blanckLabel);
        add(daysLabel);
        add(monthsLabel);
        add(yearsLabel);
        add(hoursLabel);
        add(minutesLabel);

        //ComboBox

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
        label = new JLabel(text);
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
