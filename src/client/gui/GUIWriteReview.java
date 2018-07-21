package client.gui;

import client.proxy.CustomerProxy;
//import org.omg.CORBA.CustomMarshal;
import server.Assignment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * This class allow the Customer to write a review.
 */
public class GUIWriteReview extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 600;

    /**
     * Frame height.
     */
    final int HEIGHT = 580;

    /**
     * Max number of char for the title of the review.
     */
    final int MAX_CHAR_TITLE = 150;

    /**
     * Max number of char for the comment of the review.
     */
    final int MAX_CHAR_COMMENT = 65535;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );


    /**
     * Out panel.
     */
    private JPanel outPanel;

    /**
     * The panel contains topPanel, bottomPanel and descriptionPanel.
     */
    private JPanel contentPanel;

    /**
     * The panel contains the title of the review.
     */
    private JPanel topPanel;

    /**
     * The panel contains votePanel and the label of the comment.
     */
    private JPanel bottomPanel;

    /**
     * The panel contains the vote of the review.
     */
    private JPanel votePanel;

    /**
     * The panel contains sendButton and cancelButton
     */
    private JPanel buttonPanel;

    /**
     * The panel contains the comment of the review.
     */
    private JPanel descriptionPanel;

    /**
     * The Scroll pane allows to show the comment.
     */
    private JScrollPane descriptionFieldScroll;

    /**
     * The Scroll pane allows to show the title.
     */
    private JScrollPane titleFieldScroll;

    /**
     * Label for the title of the review.
     */
    private JLabel labelTitle;

    /**
     * Label for the vote.
     */
    private JLabel labelVote;

    /**
     * Label for the comment of the review.
     */
    private JLabel labelDescription;

    /**
     * Text area for the title.
     */
    private JTextArea titleField;

    /**
     * Text area for the description.
     */
    private JTextArea descriptionField;

    /**
     * Buttons allow to add a new review or to close the frame.
     */
    private JButton sendButton, cancelButton;


    /**
     * List of the grade.
     */
    private JComboBox<String> voteBox;

    /**
     * Array of the grade.
     */
    private String[] grade = new String[]{"★", "★★", "★★★", "★★★★", "★★★★★"};

    /**
     * Reference to the object Assignment.
     */
    private Assignment assignmentToReview;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The object allows to count the char in the title.
     */
    private DefaultStyledDocument docTitle;

    /**
     * The object allows to count the char in the comment.
     */
    private DefaultStyledDocument docComment;

    /**
     * This GUI.
     */
    private GUIWriteReview guiWriteReview;

    /**
     * GUI from where GUIWriteReview is invoked.
     */
    private GUIListAssignments guiListAssignments;

    /**
     * Frame for the message of added review.
     */
    private JFrame success = new JFrame();


    /**
     * Constructor.
     * @param a assignment to review
     * @param email email the user's email.
     * @param guiListAssignments GUI from where GUIWriteReview is invoked.
     */
    public GUIWriteReview(Assignment a, String email, GUIListAssignments guiListAssignments)
    {
        assignmentToReview = a;
        guiWriteReview = this;
        this.guiListAssignments = guiListAssignments;
        guiListAssignments.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiListAssignments.setEnabled(true);
            }
        });

        this.email = email;
        proxy = new CustomerProxy(this.email);
        docTitle = new DefaultStyledDocument();
        docComment = new DefaultStyledDocument();

        docTitle.setDocumentFilter(new DocumentSizeFilter(MAX_CHAR_TITLE));
        docTitle.addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCountTitle();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCountTitle();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCountTitle();
            }
        });

        docComment.setDocumentFilter(new DocumentSizeFilter(MAX_CHAR_COMMENT));
        docComment.addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCountComment();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCountComment();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCountComment();
            }

        });

        initComponent();
    }


    /**
     * Initialize the GUI components.
     */
    private void initComponent(){
        setTitle("Write a review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        outPanel = new JPanel();
        contentPanel = new JPanel();

        topPanel = new JPanel();
        votePanel = new JPanel();
        bottomPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(1,2));
        descriptionPanel = new JPanel(new GridLayout(1,1));


        labelTitle = new JLabel("Title: ");
        labelVote = new JLabel("Vote: ");
        labelDescription = new JLabel("<html><br/><br/><br/><br/><br/>Comment: </html>");

        titleField = new JTextArea(2,1);
        titleField.setDocument(docTitle);


        if(updateCountTitle() > MAX_CHAR_TITLE){
            Toolkit.getDefaultToolkit().beep();
        }


        descriptionField = new JTextArea( 7,1);
        descriptionField.setDocument(docComment);

        if(updateCountComment()> MAX_CHAR_COMMENT){
            Toolkit.getDefaultToolkit().beep();
        }

        sendButton = new JButton("Send");
        cancelButton = new JButton("Cancel");

        descriptionFieldScroll = new JScrollPane(descriptionField);
        titleFieldScroll = new JScrollPane(titleField);
        voteBox = new JComboBox<>(grade);


        topPanel.setLayout(new GridLayout(2,1,5,5));
        labelTitle.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        topPanel.add(labelTitle);
        titleField.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        titleField.setLineWrap(true);
        titleField.setWrapStyleWord(true);

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
                    boolean strError = false;
                    String error = "";

                    int rating = starsRating(voteBox.getSelectedItem().toString());
                    String title = titleField.getText();
                    String comment = descriptionField.getText();
                    int code = assignmentToReview.getCode();
                    Date date = assignmentToReview.getDateEnd();

                    if(title.equals("")|| comment.equals("")){
                            strError = true;
                            error = "Please fill in all fields!";
                    }
                    if(title.contains("#")|| comment.contains("#")){
                            strError = true;
                            error = "# invalid character";
                    }



                    if(strError){
                        JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
                        titleField.setText("");
                        descriptionField.setText("");
                    } else if(proxy.addReview(code, email, rating ,title.toUpperCase() ,comment.toUpperCase())){
                        JOptionPane.showMessageDialog(success = new JFrame(), "Review added!", "Review", JOptionPane.INFORMATION_MESSAGE);
                        if (!success.isActive()) {
                            guiWriteReview.dispatchEvent(new WindowEvent(guiWriteReview, WindowEvent.WINDOW_CLOSING));
                            guiListAssignments.dispatchEvent(new WindowEvent(guiListAssignments, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                }
            }
        };
        sendButton.addActionListener(post);
        cancelButton.addActionListener(e -> guiWriteReview.dispatchEvent(new WindowEvent(guiWriteReview, WindowEvent.WINDOW_CLOSING)));
        buttonPanel.setLayout(new GridLayout(1, 2,5,5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 90, 10, 90));
        buttonPanel.add(cancelButton, BorderLayout.SOUTH);
        buttonPanel.add(sendButton, BorderLayout.SOUTH);


        contentPanel.setLayout(new GridLayout(3,1, 5, 5));

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


    /**
     * Convert the stars rating to an int.
     * @param rating grade of the review(stars).
     * @return vote of the review.
     */
    private int starsRating(String rating){
        switch (rating){
            case "★":
                return 1;
            case "★★":
                return 2;
            case "★★★":
                return 3;
            case "★★★★":
                return 4;
            case "★★★★★":
                return 5;
            default:
                return 0;
        }
    }



    /**
     * update the character count in the title
     * @return number of characters still allowed
     */
    private int updateCountTitle()
    {
        return MAX_CHAR_TITLE - docTitle.getLength();
    }

    /**
     * update the character count in the comment
     * @return number of characters still allowed
     */
    private int updateCountComment(){
        return MAX_CHAR_COMMENT - docComment.getLength();
    }

    
}


/**
 * Default class for counting characters in a document
 */

class DocumentSizeFilter extends DocumentFilter {

    /**
     * Number of max char.
     */
    int maxCharacters;

    //boolean DEBUG = false;

    /**
     * Constructor.
     * @param maxChars Number of max char.
     */
    public DocumentSizeFilter(int maxChars) {
        maxCharacters = maxChars;
    }


    /**
     * Rejects the entire insertion if it would make the contents too long.
     * @param fb filter bypass
     * @param offs number
     * @param str string to insert
     * @param a Attribute Set
     * @throws BadLocationException
     */
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {

        /* if (DEBUG) {
            System.out.println("in DocumentSizeFilter's insertString method");
        }*/

        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters)
            super.insertString(fb, offs, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }

    /**
     * Rejects the entire replacement if it would make the contents too long.
     * @param fb filter bypass
     * @param offs number
     * @param length length
     * @param str string to replace
     * @param a Attribute set
     * @throws BadLocationException
     */
   public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {

        /*if (DEBUG) {
           System.out.println("in DocumentSizeFilter's replace method");
       }*/

        if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters)
            super.replace(fb, offs, length, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }

}
