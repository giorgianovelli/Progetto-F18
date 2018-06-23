package customerClient.gui;

import dogSitterClient.DogSitterProxy;
import jdk.dynalink.linker.GuardedInvocation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashSet;

public class GUIChooseDogsitter extends JFrame {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private JPanel panelOut = new JPanel();
    private JPanel panelDogsitter;
    private JPanel panelLabel;
    private JPanel panelButtons;
    private JPanel panelContainer;
    private GridLayout gridLayout = new GridLayout(1,1);
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonInfo;
    private JButton buttonSelect;
    private JLabel labelDogsitter;

    HashSet<String> dogsitterList;


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    // Costruttore

    public GUIChooseDogsitter(HashSet<String> dogsitterList) {
        setTitle("Choose the dogsitter");       // TODO Da cambiare??
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.dogsitterList = dogsitterList;
        initComponents();
    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    public void initComponents() {
        panelOut.setLayout(new BorderLayout());
        panelContainer = new JPanel(gridLayout);


        for (String mailDogsitter: dogsitterList){
            DogSitterProxy dogSitterProxy = new DogSitterProxy(mailDogsitter);
            labelDogsitter = new JLabel("<html><br>" + dogSitterProxy.getName() + " " + dogSitterProxy.getSurname() + "<br/>" + mailDogsitter, SwingConstants.LEFT);
            panelLabel = new JPanel();
            panelLabel.setBorder(BorderFactory.createEmptyBorder(0,40,0, 0));
            panelLabel.add(labelDogsitter);
            panelButtons = new JPanel();
            panelButtons.setLayout(new GridLayout(1,2,10,0));
            panelButtons.setBorder(BorderFactory.createEmptyBorder(15,0,15, 40));
            buttonInfo = new JButton("Info");
            buttonSelect = new JButton("Select");
            panelButtons.add(buttonInfo);
            panelButtons.add(buttonSelect);
            panelDogsitter = new JPanel();
            panelDogsitter.setLayout(new BorderLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            panelDogsitter.add(panelLabel, BorderLayout.WEST);
            panelDogsitter.add(panelButtons, BorderLayout.EAST);
            panelContainer.add(panelDogsitter);
            gridLayout.setRows(gridLayout.getRows() + 1);

        }

        panelOut.add(panelContainer, BorderLayout.NORTH);
        System.out.println(dogsitterList.toString());



        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);

    }
}
