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
    //private JPanel votePanel;
    private JPanel buttonPanel;

    private JLabel labetTitle;
    private JLabel labelVote;
    private JLabel labelDescription;

    private JTextArea titleField;
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

        contentPanel = new JPanel(); //contiene toPanel, bottomPanel
        topPanel = new JPanel(); //contiene il titolo
        //votePanel = new JPanel();

        bottomPanel = new JPanel(); //contiene  descrizione, votazione e bottoni

        buttonPanel = new JPanel(new GridLayout(1,2, 20, 20));

        labetTitle = new JLabel("Title: ");
        labelVote = new JLabel("Vote: ");
        labelDescription = new JLabel("Description: ");
        titleField = new JTextArea(2,1);
        descriptionField = new JTextArea( 50,1);
        enterButton = new JButton("Enter");
        cancelButton = new JButton("Cancel");

        voteBox = new JComboBox<>(grade);

        contentPanel.setLayout(new BorderLayout());

        topPanel.setLayout(new GridLayout(2,2,10,10));
        topPanel.add(labetTitle);
        topPanel.add(labelVote);
        titleField.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        topPanel.add(titleField);

        topPanel.add(voteBox);

        bottomPanel.setLayout(new GridLayout(3,1, 5, 5));
        descriptionField.setLineWrap(true);
        bottomPanel.add(labelDescription);
        descriptionField.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        bottomPanel.add(descriptionField);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));
        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.CENTER);

        add(contentPanel);


        /*topPanel.setLayout(new GridLayout(3,1,5,5));
        topPanel.add(labetTitle);
        topPanel.add(titleField);

        votePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 20, 60));
        votePanel.add(labelVote);
        votePanel.add(voteBox);
        topPanel.add(votePanel);


        bottomPanel.setLayout(new GridLayout(3,1, 10, 20));
        bottomPanel.add(labelDescription);
        bottomPanel.add(descriptionField);

        buttonPanel.setLayout(new GridLayout(1,2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel);

        contentPanel.add(topPanel);
        contentPanel.add(bottomPanel);*/

        add(contentPanel);

    }

}
