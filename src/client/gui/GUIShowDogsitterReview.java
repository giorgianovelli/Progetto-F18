package client.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIShowDogsitterReview extends GUIShowReview {

    /**
     * The button allows the dogsitter to add a reply
     */
    private JButton replyButton;

    /**
     * The user's email.
     */
    private String email;

    /**
     * GUI from where GUIShowDogsitterReview is invoked.
     */
    private GUIShowDogsitterAssignment guiShowDogsitterAssignment;

    /**
     * This GUI.
     */
    private GUIShowDogsitterReview guiShowDogsitterReview;

    /**
     * Constructor.
     * @param review review to show.
     * @param email The user's email.
     */
    public GUIShowDogsitterReview(Review review, String email){
        super(review);
        this.email = email;
        guiShowDogsitterReview = this;

    }

    /**
     * Constructor using GUIShowDogsitterAssignment.
     * @param review review to show.
     * @param email The user's email.
     * @param guiShowDogsitterAssignment GUI from where GUIShowDogsitterReview is invoked.
     */
    public GUIShowDogsitterReview(Review review, String email, GUIShowDogsitterAssignment guiShowDogsitterAssignment){
        super(review);
        this.email = email;
        this.guiShowDogsitterAssignment = guiShowDogsitterAssignment;
        guiShowDogsitterReview = this;
        guiShowDogsitterAssignment.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiShowDogsitterAssignment.setEnabled(true);
            }
        });



    }

    /**
     * Allows to show the dogsitter reply, or to add a reply.
     */
    @Override
    protected void showReply() {
        GridLayout gridLayout = new GridLayout(1,1, 5, 5);
        closeButton = new JButton("Close");
        replyButton = new JButton("Reply");

        ActionListener reply = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIDogsitterReply guiDogsitterReply = new GUIDogsitterReply(review, email, guiShowDogsitterReview);
                guiDogsitterReply.setVisible(true);
            }
        };


        if(!(review.getReply().equals("null"))){
            panelReply.setBorder(BorderFactory.createTitledBorder("Your Reply: "));
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.setLayout(new GridLayout(2,1));
            panelReply.add(textReply);
            closeButton.addActionListener(e -> guiShowDogsitterReview.dispatchEvent(new WindowEvent(guiShowDogsitterReview, WindowEvent.WINDOW_CLOSING)));
            closePanel.setBorder(BorderFactory.createEmptyBorder(70,20,20,20));
            closePanel.add(closeButton);

        }
        else {

            gridLayout.setColumns(2);
            closePanel.setLayout(gridLayout);
            closePanel.setBorder(BorderFactory.createEmptyBorder(100,20,20,20));
            closeButton.addActionListener(e -> guiShowDogsitterReview.dispatchEvent(new WindowEvent(guiShowDogsitterReview, WindowEvent.WINDOW_CLOSING)));
            replyButton.addActionListener(reply);
            closePanel.add(replyButton);
            closePanel.add(closeButton);
        }

    }

    /**
     * Getter of the GUI from where GUIShowDogsitterReview is invoked.
     * @return GUIShowDogsitterAssignment object
     */
    public GUIShowDogsitterAssignment getGuiShowDogsitterAssignment() {
        return guiShowDogsitterAssignment;
    }
}
