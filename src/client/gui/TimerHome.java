package client.gui;

import client.proxy.Proxy;

import java.text.ParseException;
import java.util.TimerTask;

public class TimerHome extends TimerTask {

    private GUIHome guiHome;
    private Proxy proxy;
    private int monthNumber;

    @Override
    public void run() {
        try {
            guiHome.updateCalendar(monthNumber, proxy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TimerHome(GUIHome guiHome, Proxy proxy, int monthNumber) {
        this.guiHome = guiHome;
        this.proxy = proxy;
        this.monthNumber = monthNumber;
    }
}
