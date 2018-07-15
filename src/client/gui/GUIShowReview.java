package client.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIShowReview extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    protected Review review;

    private JPanel outPanel;
    private JPanel contentPanel;
    private JPanel panelReviewTop;
    private JPanel panelReviewDescription;
    protected JPanel panelReply;
    protected JPanel closePanel;

    private JLabel labelTitle;
    private JLabel labelDescription;
    private JLabel labelVote;
    private JLabel labelGrade;
    //private JLabel labelReply;

    private JTextArea textTitle;
    private JTextArea textDescription;
    protected JTextArea textReply;

    protected JButton closeButton;
    private GUIListAssignments guiListAssignments;
    //private int numberRow;

    /**
     * costruttore
     * @param review recensione da visualizzare
     */
    public GUIShowReview(Review review){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        this.review = review;

        initComponent();

    }

    /**
     * costruttore
     * @param review recensione da visualizzare
     * @param guiListAssignments interfaccia da cui è stata richiamata
     */
    public GUIShowReview(Review review, GUIListAssignments guiListAssignments){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        this.review = review;
        this.guiListAssignments = guiListAssignments;
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
     * inizializza le componenti dell'interfaccia e ne permette la visualizzazione
     */
    private void initComponent(){
        //TODO si potrebbe provare a usare GridBagLayout
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
        //labelReply = new JLabel("Dogsitter reply: ");

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

        //outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.Y_AXIS));
        outPanel.setLayout(new GridLayout(3,1));
        //contentPanel.setPreferredSize(new Dimension(WIDTH-10, (HEIGHT/2)-30));
        //closePanel.setBorder(BorderFactory.createEmptyBorder(20,40,5,30));
        outPanel.add(contentPanel);
        outPanel.add(panelReply);
        outPanel.add(closePanel);
        outPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(outPanel);






    }

    /**
     * permette di visualizzare l'eventuale risposta del dogsitter alla recensione
     */
    protected void showReply(){
        if(!(review.getReply().equals("null"))){
            panelReply.setBorder(BorderFactory.createTitledBorder("Dogsitter Reply: "));
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.setLayout(new GridLayout(2,1));
            //panelReply.add(labelReply);
            panelReply.add(textReply);



        }
        ActionListener close = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        };

        closeButton = new JButton("Close");
        closeButton.addActionListener(close);

        closePanel.add(closeButton);


    }




}
