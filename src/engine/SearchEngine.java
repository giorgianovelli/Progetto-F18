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

    public HashSet<DogSitter> search(Date dateStart, Date dateEnd, Address meetingPoint, HashMap<Integer, Dog> dogList, boolean cash){
        //implementare la funzione di ricerca
        SimpleDateFormat dateNumDayOfWeek = new SimpleDateFormat("u");
        int nStartDay = Integer.parseInt(dateNumDayOfWeek.format(dateStart));
        int nEndDay = Integer.parseInt(dateNumDayOfWeek.format(dateEnd));
        int i;
        //esclude i dog sitter che non lavorano nei giorni di lavoro impostati dal cliente
        for (DogSitter ds : dogSitterList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay; i < nEndDay; i++){
                if (availability == null){
                    dogList.remove(ds);
                }
            }
        }

        //esclude i dog sitter che non lavorano negli orari di lavoro impostati dal cliente
        Time timeStart = (Time)dateStart;
        Time timeEnd = (Time)dateEnd;
        for (DogSitter ds : dogSitterList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay; i < nEndDay; i++){
                Time ts = availability[i].getStart();
                Time te = availability[i].getEnd();
                if (timeStart.before(ts) || timeEnd.after(te)){
                    dogList.remove(ds);
                }
            }
        }

        /*for (DogSitter ds : dogSitterList) {
            HashMap<String, Assignment> listAssignment = ds.getListAssignment();
            for (String key : listAssignment.keySet()) {
                Assignment a = listAssignment.get(key);
                //implementare funzione che esclude dogsitter con assignment concorrenti
            }
        }*/

        //nel caso in cui il cliente vuole pagare in contanti,
        //esclude i dog sitter che accettano il pagamento solo con carta di credito
        if (cash){
            for (DogSitter ds : dogSitterList) {
                if(!(ds.isAcceptingCash())){
                    dogSitterList.remove(ds);
                }
            }
        }

        return dogSitterList;
    }


}
