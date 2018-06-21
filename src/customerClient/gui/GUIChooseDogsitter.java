package customerClient.gui;

import jdk.dynalink.linker.GuardedInvocation;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GUIChooseDogsitter extends JFrame {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private JPanel panelOut = new JPanel();
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonInfo = new JButton("Info");
    private JButton buttonSelect = new JButton("Select");
    private JLabel labelDogsitter;

    HashSet<String> dogsitterList;


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    // Costruttore

    public GUIChooseDogsitter(HashSet<String> dogsitterList) {
        setTitle("Choose the dogsitter");       // TODO Da cambiare??
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.dogsitterList = dogsitterList;
        labelDogsitter.setText("test");
        initComponents();
    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    public void initComponents() {
        panelOut.setLayout(new GridLayout(1,3));

        panelOut.add(labelDogsitter);
        panelOut.add(buttonInfo);
        panelOut.add(buttonSelect);

        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);


    }
}
