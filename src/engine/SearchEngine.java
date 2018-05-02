package engine;

import database.DBConnector;
import engine.DogSitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static staticClasses.ObjectCreator.createDogSitterFromDB;

public class SearchEngine {
    private HashSet<DogSitter> dogSitterList;

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

    public HashSet<DogSitter> search(Date dateStart, Date dateEnd, Address meetingPoint, HashMap<Integer, Dog> dogList){
        //implementare la funzione di ricerca
        return null;
    }


}
