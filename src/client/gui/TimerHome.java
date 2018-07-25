package client.gui;

import client.proxy.Proxy;

import java.text.ParseException;
import java.util.TimerTask;

/**
 * This class  contains methods that are executed periodically for updating home.
 */
public class TimerHome extends TimerTask {

    /**
     * The GUI Home object
     */
    private GUIHome guiHome;

    /**
     * The proxy object.
     */
    private Proxy proxy;

    /**
     * The attribute "the number of months".
     */
    private int monthNumber;


    /**
     * Method "run" that keeps the calendar updated.
     */
    @Override
    public void run() {
        try {
            guiHome.updateCalendar(proxy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     * @param guiHome interface from where TimerHome is invoked
     * @param proxy of the user
     * @param monthNumber
     */
    public TimerHome(GUIHome guiHome, Proxy proxy, int monthNumber) {
        this.guiHome = guiHome;
        this.proxy = proxy;
        this.monthNumber = monthNumber;
    }
}
