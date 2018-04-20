import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.channels.CancelledKeyException;

public class GUIListAssignments extends JFrame{
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    public GUIListAssignments(CalendarState cs, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents(cs, guiCustomer);
    }

    private void initComponents(CalendarState cs, GUICustomer gc){
        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");
        }
        if ((cs.equals(CalendarState.DELETING_REVIEW)) || (cs.equals(CalendarState.SHOW_REVIEWS))){
            setTitle("Your reviews");
        }


        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                gc.setCalendarState(CalendarState.NORMAL);
            }
         });
    }
}
