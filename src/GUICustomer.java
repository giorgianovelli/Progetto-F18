import javax.swing.*;
import java.awt.*;

public class GUICustomer extends JFrame{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    public GUICustomer() {
        setTitle("CaniBau (Customer)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        //initComponents();
    }
}
