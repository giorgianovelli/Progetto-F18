import javax.swing.*;
import java.awt.*;

public class GUILogin extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 150;
    Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    JPanel panelLoginData = new JPanel();
    JPanel panelBottom = new JPanel();
    JLabel labelUser = new JLabel("Email", SwingConstants.CENTER);
    JLabel labelPwd = new JLabel("Password", SwingConstants.CENTER);
    JTextField textUser = new JTextField();
    JPasswordField textPwd = new JPasswordField();
    JButton buttonLogin = new JButton("Login");
    JButton buttonNewAccount = new JButton("Create a new account");

    public GUILogin() {
        setTitle("Login");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents(){
        panelLoginData.setLayout(new GridLayout(2,2));
        panelLoginData.add(labelUser);
        panelLoginData.add(textUser);
        panelLoginData.add(labelPwd);
        panelLoginData.add(textPwd);
        add(panelLoginData, BorderLayout.CENTER);
        panelBottom.setLayout(new GridLayout(2,1));
        panelBottom.add(buttonLogin);
        panelBottom.add(buttonNewAccount);
        add(panelBottom, BorderLayout.SOUTH);
    }

}
