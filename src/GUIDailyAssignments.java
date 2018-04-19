import javax.swing.*;
import java.awt.*;

public class GUIDailyAssignments extends JFrame{
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    public GUIDailyAssignments() {
        setTitle("Daily Assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        //initComponents();
    }
}
