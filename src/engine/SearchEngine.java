package engine;

import database.DBConnector;
import engine.DogSitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class SearchEngine {
    private HashSet<DogSitter> dogSitterList;
    private final int NDAYOFWEEK = 7;

    public SearchEngine() {
        dogSitterList = new HashSet<DogSitter>();
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL FROM DOGSITTERS");
            while (rs.next()){
                DogSitter ds = createDogSitterFromDB(rs.getString("EMAIL"));
                dogSitterList.add(ds);
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashSet<DogSitter> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash){
        //funzione da completare
        //al termine, rimuovere le System.out utili per il debug
        SimpleDateFormat dateNumDayOfWeek = new SimpleDateFormat("u");
        int nStartDay = Integer.parseInt(dateNumDayOfWeek.format(dateStart));
        int nEndDay = Integer.parseInt(dateNumDayOfWeek.format(dateEnd));
        System.out.println("start: " + nStartDay);
        System.out.println("end: " + nEndDay);
        int i;

        System.out.println("zero");
        for (DogSitter ds : dogSitterList) {
            System.out.println(ds.getEmail());
        }

        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay - 1; i < nEndDay; i++){
                if ((availability[i].getStart() == null) && (availability[i].getEnd() == null)){
                    System.out.println("to remove: " + ds.getEmail());
                    //dogSitterList.remove(ds);
                    toRemove.add(ds);
                }
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterList.remove(ds);
        }

        toRemove.clear();

        System.out.println("first");
        for (DogSitter ds : dogSitterList) {
            System.out.println(ds.getEmail());
        }

        //esclude i dog sitter che non lavorano negli orari di lavoro impostati dal cliente
        Time timeStart = new Time(dateStart.getTime());
        Time timeEnd = new Time(dateEnd.getTime());
        for (DogSitter ds : dogSitterList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay - 1; i < nEndDay; i++){
                Time ts = availability[i].getStart();
                Time te = availability[i].getEnd();
                if (timeStart.after(ts) && timeEnd.before(te)){
                    System.out.println(timeStart + ">" + ts);
                    System.out.println(timeEnd + "<" + te);
                    System.out.println("to remove: " + ds.getEmail());
                    toRemove.add(ds);
                }
            }
        }

        for (DogSitter ds : toRemove) {
            dogSitterList.remove(ds);
        }

        toRemove.clear();

        System.out.println("second");
        for (DogSitter ds : dogSitterList) {
            System.out.println(ds.getEmail());
        }

        //implementare funzione che esclude dogsitter con assignment concorrenti

        //nel caso in cui il cliente vuole pagare in contanti,
        //esclude i dog sitter che accettano il pagamento solo con carta di credito
        if (cash){
            for (DogSitter ds : dogSitterList) {
                if(ds.isAcceptingCash() == false){
                    toRemove.add(ds);
                }
            }
            for (DogSitter ds : toRemove) {
                dogSitterList.remove(ds);
            }
        }

        return dogSitterList;
    }

}
