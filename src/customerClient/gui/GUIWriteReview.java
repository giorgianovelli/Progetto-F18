package customerClient.gui;

import javax.swing.*;
import java.awt.*;

public class GUIWriteReview extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel contentPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JLabel labetTitle;
    private JLabel labelVote;
    private JLabel labelDescription;
    private JTextField titleField;
    private JTextArea descriptionField;
    private JButton enterButton, cancelButton;


    private JComboBox<String> voteBox;
    private String[] grade = new String[]{"1", "2", "3", "4", "5"};

    public GUIWriteReview(){
        initComponent();
    }

    private void initComponent(){
        setTitle("Write a review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        contentPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();

        buttonPanel = new JPanel(new GridLayout(1,2, 10, 10));
        labetTitle = new JLabel("Title: ");
        labelVote = new JLabel("Vote: ");
        labelDescription = new JLabel("Description: ");
        titleField = new JTextField();
        descriptionField = new JTextArea();
        enterButton = new JButton("Enter");
        cancelButton = new JButton("Cancel");

        voteBox = new JComboBox<>(grade);

        contentPanel.setLayout(new BorderLayout());

        topPanel.setLayout(new GridLayout(2,2,10,10));
        topPanel.add(labetTitle);
        topPanel.add(labelVote);
        topPanel.add(titleField);
        topPanel.add(voteBox);

        bottomPanel.setLayout(new GridLayout(3,1, 5, 5));
        bottomPanel.add(labelDescription);
        bottomPanel.add(descriptionField);
        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.CENTER);

        add(contentPanel);

    }

}
