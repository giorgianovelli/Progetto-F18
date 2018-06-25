package customerClient.gui;

import customerClient.CustomerProxy;
//import org.omg.CORBA.CustomMarshal;   //TODO da problemi!!
import server.Assignment;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class GUIWriteReview extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 580;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    //TODO limitare il numero di righe o caratteri!!

    private JPanel outPanel;
    private JPanel contentPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel votePanel;
    private JPanel buttonPanel;
    private JPanel descriptionPanel;

    private JScrollPane descriptionFieldScroll;
    private JScrollPane titleFieldScroll;

    private JLabel labelTitle;
    private JLabel labelVote;
    private JLabel labelDescription;

    private JTextArea titleField;
    private JTextArea descriptionField;
    private JButton sendButton, cancelButton;


    private JComboBox<String> voteBox;
    private String[] grade = new String[]{"1", "2", "3", "4", "5"};

    private Assignment assignmentToReview;
    private String email;
    private CustomerProxy proxy;


    /**
     *
     * @param a appuntamento riferito alla recensione che devo scrivere
     * @param email riferimento all'utente
     */

    public GUIWriteReview(Assignment a, String email)
    { //devo farmi passare l'appuntamento per aggiungere la recensione (per il codice)?
        assignmentToReview = a;
        this.email = email;
        proxy = new CustomerProxy(this.email);
        initComponent();
    }

    private void initComponent(){
        setTitle("Write a review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        outPanel = new JPanel();
        contentPanel = new JPanel(); //contiene toPanel, bottomPanel

        topPanel = new JPanel(); //contiene il titolo
        votePanel = new JPanel();
        bottomPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(1,2));
        descriptionPanel = new JPanel(new GridLayout(1,1));


        labelTitle = new JLabel("Title: ");
        labelVote = new JLabel("Vote: ");
        labelDescription = new JLabel("<html><br/><br/><br/><br/><br/>Comment: </html>");

        titleField = new JTextArea(2,1);
        descriptionField = new JTextArea( 7,1);
        sendButton = new JButton("Send");
        cancelButton = new JButton("Cancel");

        descriptionFieldScroll = new JScrollPane(descriptionField);
        titleFieldScroll = new JScrollPane(titleField);
        voteBox = new JComboBox<>(grade);


        topPanel.setLayout(new GridLayout(2,1,5,5));
        labelTitle.setFont(new Font("TimesRoman", Font.BOLD, 16));
        topPanel.add(labelTitle);
        titleField.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        titleField.setLineWrap(true);
        titleField.setWrapStyleWord(true);
        titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5,5, 5,5));
        topPanel.add(titleFieldScroll);

        votePanel.setLayout(new GridLayout(2,1));
        votePanel.add(labelVote);
        votePanel.add(voteBox);
        votePanel.setBorder(BorderFactory.createEmptyBorder(40,10,20,70));

        bottomPanel.setLayout(new GridLayout(1,2));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        bottomPanel.add(labelDescription);
        bottomPanel.add(votePanel);

        descriptionField.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
        descriptionPanel.add(descriptionFieldScroll);

        ActionListener post = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent registrationAe) {

                if (registrationAe.getActionCommand().equals("Send")) {


                    int rating = Integer.parseInt((String) voteBox.getSelectedItem());
                    String title = titleField.getText();
                    String comment = descriptionField.getText();
                    int code = assignmentToReview.getCode();
                    Date date = assignmentToReview.getDateEnd();

                    //errore: quando chiudo il JOptionPane si blocca
                    if(title.equals("")|| comment.equals("")){
                        JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        titleField.setText("");
                        descriptionField.setText("");
                    }
                    if(title.contains("#")|| comment.contains("#")){
                        JOptionPane.showMessageDialog(new JFrame(), "# invalid character", "Error", JOptionPane.ERROR_MESSAGE);
                        titleField.setText("");
                        descriptionField.setText("");
                    }

                    /*
                    if(proxy.addReview(code, email, rating ,title ,comment)){ //TODO controllare l'aggiunta della recensione nel db
                        JOptionPane.showMessageDialog(new JFrame(), "Review added", "Review", JOptionPane.INFORMATION_MESSAGE);
                    }*/
                }
                if (registrationAe.getActionCommand().equals("Cancel")) {
                    //System.exit(0);
                    dispose();
                }

            }
        };
        sendButton.addActionListener(post);
        cancelButton.addActionListener(post);
        buttonPanel.setLayout(new GridLayout(1, 2,5,5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 90, 10, 90));
        buttonPanel.add(cancelButton, BorderLayout.SOUTH);
        buttonPanel.add(sendButton, BorderLayout.SOUTH);


        contentPanel.setLayout(new GridLayout(3,1, 5, 5));
        //contentPanel.setBorder(BorderFactory.createEmptyBorder(5,20,20,20));
        contentPanel.setBorder(BorderFactory.createTitledBorder("Review fields: "));

        contentPanel.add(topPanel);
        contentPanel.add(bottomPanel);
        contentPanel.add(descriptionPanel);


        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.Y_AXIS));
        outPanel.setBorder(BorderFactory.createEmptyBorder(15,20,5,20));
        outPanel.add(contentPanel);
        outPanel.add(buttonPanel);

        add(outPanel);



    }

}
