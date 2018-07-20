package client.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIShowReview extends JFrame {

    /**
     * Frame width.
     */
    final int WIDTH = 512;

    /**
     * Frame height.
     */
    final int HEIGHT = 512;

    /**
     * The screen's dimension.
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    /**
     * Reference to the object Review, which contains the information to show.
     */
    protected Review review;

    /**
     * Out panel
     */
    private JPanel outPanel;

    /**
     * The panel contains panelReviewTop and panelReviewDescription.
     */
    private JPanel contentPanel;

    /**
     * The panel contains the title of the review and the vote.
     */
    private JPanel panelReviewTop;

    /**
     * The panel contains the comment of the review.
     */
    private JPanel panelReviewDescription;

    /**
     * The panel contains the reply of the dogsitter.
     */
    protected JPanel panelReply;

    /**
     * The panel contains the closeButton.
     */
    protected JPanel closePanel;

    /**
     * Label for the title of the review.
     */
    private JLabel labelTitle;

    /**
     * Label for the description of the review.
     */
    private JLabel labelDescription;

    /**
     * Label for the vote of the review.
     */
    private JLabel labelVote;

    /**
     * Label for the grade with stars of the review.
     */
    private JLabel labelGrade;

    /**
     * Text area for the title.
     */
    private JTextArea textTitle;

    /**
     * Text area for the description.
     */
    private JTextArea textDescription;

    /**
     * Text area for the reply.
     */
    protected JTextArea textReply;

    /**
     * The button allows to close tha frame.
     */
    protected JButton closeButton;

    /**
     * GUI from where GUIShowReview is invoked.
     */
    private GUIListAssignments guiListAssignments;

    /**
     * This GUI.
     */
    private GUIShowReview guiShowReview;


    /**
     * Constructor.
     * @param review review.
     */
    public GUIShowReview(Review review){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        guiShowReview = this;

        this.review = review;

        initComponent();

    }

    /**
     * Constructor using guiAssignmentInformationCustomer.
     * @param review review
     * @param guiAssignmentInformationCustomer GUI from where GUIShowReview is invoked.
     */
    public GUIShowReview(Review review, GUIAssignmentInformationCustomer guiAssignmentInformationCustomer){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        guiShowReview = this;
        guiAssignmentInformationCustomer.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiAssignmentInformationCustomer.setEnabled(true);
            }
        });


        this.review = review;

        initComponent();

    }


    /**
     * Constructor using guiListAssignments.
     * @param review review.
     * @param guiListAssignments GUI from where GUIShowReview is invoked.
     */
    public GUIShowReview(Review review, GUIListAssignments guiListAssignments){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        this.review = review;
        this.guiListAssignments = guiListAssignments;
        guiShowReview = this;
        guiListAssignments.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiListAssignments.setEnabled(true);
            }
        });

        initComponent();

    }

    /**
     * Initialize the GUI components.
     */
    private void initComponent(){

        outPanel = new JPanel();
        contentPanel = new JPanel();
        panelReviewTop = new JPanel();
        panelReviewDescription = new JPanel();
        panelReply = new JPanel();
        closePanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createTitledBorder("Review fields: "));

        labelTitle = new JLabel("Title: ", SwingConstants.LEFT);
        labelVote = new JLabel("Vote: " , SwingConstants.LEFT);
        labelDescription = new JLabel("Comment: ");

        labelGrade = new JLabel(review.starsRating());


        textTitle = new JTextArea(review.getTitle());
        textTitle.setEditable(false);
        textTitle.setLineWrap(true);
        textTitle.setWrapStyleWord(true);

        textDescription = new JTextArea(review.getComment(),7,1);
        textDescription.setEditable(false);
        textDescription.setLineWrap(true);
        textDescription.setWrapStyleWord(true);
        textReply = new JTextArea();

        panelReviewTop.setLayout(new GridLayout(2,2));
        panelReviewTop.add(labelTitle);
        panelReviewTop.add(textTitle);
        panelReviewTop.add(labelVote);
        panelReviewTop.add(labelGrade);



        panelReviewDescription.setLayout(new GridLayout(2,1));
        panelReviewDescription.add(labelDescription);
        panelReviewDescription.add(textDescription);

        
        contentPanel.setLayout(new GridLayout(2, 1));
        contentPanel.add(panelReviewTop);
        contentPanel.add(panelReviewDescription);

        showReply();


        outPanel.setLayout(new GridLayout(3,1));

        outPanel.add(contentPanel);
        outPanel.add(panelReply);
        outPanel.add(closePanel);
        outPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(outPanel);


    }

    /**
     * Allows to show the dog sitter reply.
     */
    protected void showReply(){
        if(!(review.getReply().equals("null"))){
            panelReply.setBorder(BorderFactory.createTitledBorder("Dogsitter Reply: "));
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.setLayout(new GridLayout(2,1));
            panelReply.add(textReply);



        }

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> guiShowReview.dispatchEvent(new WindowEvent(guiShowReview, WindowEvent.WINDOW_CLOSING)));

        closePanel.add(closeButton);
    }
}
