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
    private JPanel panelDogsitter = new JPanel();
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonInfo = new JButton("Info");
    private JButton buttonSelect = new JButton("Select");
    private JLabel labelDogsitter = new JLabel();

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
        labelDogsitter.setText("test molto lungo di prova miseriaccia blalalalalalaalalalalaalalalalalaalalalaalalalaalalaal");
        initComponents();
    }

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    public void initComponents() {
        panelOut.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        panelDogsitter.setLayout(new BorderLayout());

        panelOut.add(panelDogsitter);

        panelDogsitter.add(labelDogsitter, BorderLayout.LINE_START);
        panelDogsitter.add(buttonInfo, BorderLayout.CENTER);
        panelDogsitter.add(buttonSelect, BorderLayout.LINE_END);

        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);

    }
}
