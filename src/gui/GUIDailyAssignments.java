package gui;

import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;

public class GUIDailyAssignments extends JFrame{
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    public GUIDailyAssignments(CalendarState cs) {
        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents(cs);
    }

    private void initComponents(CalendarState cs){
        //metodo da implementare
    }
}
