package customerClient.gui;



import javax.swing.*;
import java.awt.*;

public class GUINewAssignment extends JFrame{

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Panels

    private JPanel panelOut = new JPanel();
    private JPanel panelBox = new JPanel();
    private JPanel panelCombo = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelLabel = new JPanel();
    private JPanel panelButtons = new JPanel();
    private JPanel panelDogs = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    //Others


    private JLabel labelMeetingPoint = new JLabel("Choose where you would like to meet the dogsitter: ");
    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");


    NewAssignmentBox newAssignmentBox = new NewAssignmentBox();
    NewAssignmentText address = new NewAssignmentText("Address:                ");           // Perchè non mi va lo SwingCostants? Dà errore "illecit position" qualsiasi posizione io inserisca dentro panelData
    NewAssignmentText city = new NewAssignmentText("City:                         ");
    NewAssignmentText code = new NewAssignmentText("Code:                      ");
    NewAssignmentText country = new NewAssignmentText("Country:                ");

    NewAssignmentCheckBox dog = new NewAssignmentCheckBox("Fido");



//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //Costruttore

    public GUINewAssignment() {
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

        panelOut.setLayout(new BorderLayout());
        panelBox.setLayout(new BoxLayout(panelBox, 1));
        panelBox.setBorder(BorderFactory.createTitledBorder("Complete each field to look for a dogsitter: "));
        panelCombo.setLayout(new GridLayout(1,1));
        panelAddress.setLayout(new GridLayout(4, 1, 30, 20));
        panelLabel.setLayout(new BorderLayout());

        panelOut.add(panelBox, BorderLayout.NORTH);

        panelBox.add(panelCombo);
        panelBox.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBox.add(panelLabel);
        panelBox.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBox.add(panelAddress);


        panelOut.add(panelDogs, BorderLayout.CENTER);
        panelOut.add(panelButtons, BorderLayout.SOUTH);

        panelCombo.add(newAssignmentBox);

        panelDogs.add(dog);


        panelAddress.add(address);
        panelAddress.add(city);
        panelAddress.add(code);
        panelAddress.add(country);

        panelLabel.add(labelMeetingPoint, BorderLayout.LINE_START);

        panelButtons.setLayout(new GridLayout(1,2,5,0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
        panelButtons.add(buttonCancel);
        panelButtons.add(buttonSearch);



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



    String[] day = new String[]{};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Costruttore NewAssignmentBox

    public NewAssignmentBox(){
        fdayList = new JComboBox<>(day);
        fmonthList = new JComboBox(month);
        fyearList = new JComboBox<>(year);
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
        add(fdayList);
        add(fmonthList);
        add(fyearList);
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

        setLayout(new GridBagLayout());
        labelName = new JLabel(text);
        checkBox = new JCheckBox();
    }
}
