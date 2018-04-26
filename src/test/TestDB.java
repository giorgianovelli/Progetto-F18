package test;

import database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) throws SQLException {
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.askDB("SELECT * FROM CUSTOMERS WHERE EMAIL = 'gianninorvegese@gmail.com' AND PASSWORD = 'chimiaiuta'");
        System.out.println(rs.getRow());
        while (rs.next()){
            System.out.println(rs.getString("email"));
            System.out.println(rs.getString("name"));
            System.out.println(rs.getString("surname"));
            System.out.println(rs.getString("password"));
            System.out.println(rs.getString("phone_numb"));
            System.out.println(rs.getDate("birthdate"));
            System.out.println(rs.getRow());
        }
        dbConnector.closeConnection();
    }
}
