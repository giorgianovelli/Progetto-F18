package customerClient.gui;

import server.Customer;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;

public class GUIDailyAssignments extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel p = new JPanel();
    private JButton button[];
    private JLabel lb = new JLabel();
    private JScrollPane scroll = new JScrollPane(p);

    private Customer customer;


    public GUIDailyAssignments(CalendarState cs, Customer customer) {
        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        initComponents (cs);
        this.customer = customer;
    }

    private void initComponents(CalendarState cs){
        //metodo da implementare

        //inserire query per interrogare db
        int nAssignments = 30;

        button = new JButton[nAssignments];

        if (cs.equals(CalendarState.REMOVING)) {
            setTitle("Daily assignment");
            p.setLayout(new GridLayout(button.length, 2));
            for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Delete");
                p.add(lb);
                p.add(button[i]);
            }
        } else if (cs.equals(CalendarState.NORMAL)){
            setTitle("Daily assignment");
            p.setLayout(new GridLayout(nAssignments, 2));
            for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Info");
                p.add(lb);
                p.add(button[i]);
            }



        }
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll);


    }
}
