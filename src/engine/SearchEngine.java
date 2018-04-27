package engine;

import database.DBConnector;
import engine.DogSitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

public class SearchEngine {
    private HashSet<DogSitter> dogSitterList;

    public SearchEngine() {
        //inserire la funzione che scarica la lista di dogsitter dal database
        //per costruire l'HashSet con tutti i dog sitter registrati alla piattaforma
        dogSitterList = new HashSet<DogSitter>();
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM DOGSITTERS");
            while (rs.next()){
                String email = rs.getString("EMAIL");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String password = rs.getString("PASSWORD");
                String phone = rs.getString("PHONE_NUMB");
                Date birthdate = rs.getDate("BIRTHDATE");
                String payment = rs.getString("PAYMENT");
                boolean cashFlag = rs.getBoolean("CASH_FLAG");
                String area = rs.getString("AREA");
                String biography = rs.getString("BIOGRAPHY");

                //da completare all'introduzione della table ADDRESS nel DB

            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
