package customerClient.gui;

import server.Customer;
import enumeration.CalendarState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

public class GUIDailyAssignments extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel p = new JPanel();
    private JButton button[];
    private JLabel lb = new JLabel();
    private JScrollPane scroll = new JScrollPane(p);
    private JFrame frame = new JFrame();

    private Customer customer;


    public GUIDailyAssignments(CalendarState cs, Customer customer) {
        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        initComponents(cs);
        this.customer = customer;
    }

    private void initComponents(CalendarState cs) {
        //inserire query per interrogare db
        int nAssignments = 6;

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
                p.add(frame);
               // lb.setText("Delete");
                button[nAssignments].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog d = new JDialog(frame, "Conferm Actions", true);
                        d.setLocationRelativeTo(frame);
                        d.setVisible(true);
                    }
                });

                frame.getContentPane().setLayout(new FlowLayout());
                frame.add(button[nAssignments]);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setPreferredSize(new Dimension(512, 512));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }

          else if(cs.equals(CalendarState.NORMAL)) {
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


