package customerClient.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIWriteReview extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    private JPanel contentPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel votePanel;
    private JPanel buttonPanel;
    private JPanel descriptionPanel;

    private JScrollPane descriptionFieldScroll;

    private JLabel labelTitle;
    private JLabel labelVote;
    private JLabel labelDescription;

    private JTextArea titleField;
    private JTextArea descriptionField;
    private JButton enterButton, cancelButton;


    private JComboBox<String> voteBox;
    private String[] grade = new String[]{"1", "2", "3", "4", "5"};

    public GUIWriteReview(){ //devo farmi passare l'appuntamento per aggiungere la recensione (per il codice)?
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
        votePanel = new JPanel();
        bottomPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(1,2, 20, 20));
        descriptionPanel = new JPanel(new GridLayout(1,1));


        labelTitle = new JLabel("Title: ");
        labelVote = new JLabel("Vote: ");
        labelDescription = new JLabel("Description: ");
        titleField = new JTextArea(2,1);
        descriptionField = new JTextArea( 10,1);
        enterButton = new JButton("Enter");
        cancelButton = new JButton("Cancel");

        descriptionFieldScroll = new JScrollPane(descriptionField);

        voteBox = new JComboBox<>(grade);

        contentPanel.setLayout(new BorderLayout());

        topPanel.setLayout(new GridLayout(2,1,5,5));
        labelTitle.setFont(new Font("TimesRoman", Font.BOLD, 20));
        topPanel.add(labelTitle);
        titleField.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        topPanel.add(titleField);

        votePanel.setLayout(new GridLayout(2,1));
        votePanel.add(labelVote);
        votePanel.add(voteBox);
        votePanel.setBorder(BorderFactory.createEmptyBorder(20,10,30,20));

        bottomPanel.setLayout(new GridLayout(1,2));

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(2,5,2,10));
        bottomPanel.add(labelDescription);
        bottomPanel.add(votePanel);

        descriptionField.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        descriptionField.setLineWrap(true);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        descriptionPanel.add(descriptionFieldScroll);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 90, 20, 90));
        ActionListener post = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Enter")) {
                    //TODO controlli sul testo + aggiungere la recensione nel db

                    int rating = Integer.parseInt((String) voteBox.getSelectedItem());
                    String title = titleField.getText();
                    String comment = descriptionField.getText();

                    //System.out.println(rating + title + comment);

                    /*Review reviewToAdd = new Review();
                    int code  code dell'appuntamento??
                    Date date  data dell'appuntamento???
                    int rating
                    String title
                    String comment
                    String reply ??????????
                     */

                }
                if (registrationAe.getActionCommand().equals("Cancel")) {
                    dispose();
                }

            }
        };
        enterButton.addActionListener(post);
        cancelButton.addActionListener(post);

        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);

        contentPanel.setLayout(new GridLayout(4,1));
        contentPanel.add(topPanel);
        contentPanel.add(bottomPanel);
        contentPanel.add(descriptionPanel);
        contentPanel.add(buttonPanel);


        add(contentPanel);


    }

}
