package engine;

import database.DBConnector;
import engine.DogSitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class SearchEngine {
    private HashSet<DogSitter> dogSitterList;

    public SearchEngine() {
        //inserire la funzione che scarica la lista di dogsitter dal database
        //per costruire l'HashSet con tutti i dog sitter registrati alla piattaforma
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM DOGSITTERS");
            while (rs.next()){
                System.out.println(rs.getString("email"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("surname"));
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("phone_number"));
                System.out.println(rs.getDate("birthdate"));
                System.out.println(rs.getString("payment"));
                System.out.println(rs.getBoolean("cash_flag"));
                System.out.println(rs.getString("area"));
                System.out.println(rs.getString("biography"));
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
