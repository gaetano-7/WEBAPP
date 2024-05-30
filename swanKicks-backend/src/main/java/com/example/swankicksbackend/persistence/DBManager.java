package com.example.swankicksbackend.persistence;



import com.example.swankicksbackend.persistence.dao.*;
import com.example.swankicksbackend.persistence.dao.postgres.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance = null;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    Connection conn = null;

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            }
            catch (SQLException e) {

            }
        }
        return conn;
    }

    public scarpaDao getScarpaDAO(){
        return new scarpaDaoPostgres(getConnection());
    }

    public recensioneDao getRecensioneDAO(){
        return new recensioneDaoPostgres(getConnection());
    }

    public acquistoDao getAcquistoDAO(){
        return new acquistoDaoPostgres(getConnection());
    }

    public ordineDao getOrdineDAO(){
        return new ordineDaoPostgres(getConnection());
    }

    public utenteDao getUtenteDAO() { return new utenteDaoPostgres(getConnection()); }

    public asteDao getAsteDAO() { return new asteDaoPostgres(getConnection()); }

    public imagesDao getImagesDAO() {
        return new imagesDaoPostgres(getConnection());
    }
}
