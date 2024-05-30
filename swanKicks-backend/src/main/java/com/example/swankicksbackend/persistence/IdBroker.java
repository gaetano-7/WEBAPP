package com.example.swankicksbackend.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdBroker {
    public static Integer getId(Connection connection, String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Integer getScarpaId(Connection connection){
        return getId(connection, "select nextval('scarpe_id') as id");
    }

    public static Integer getRecensioneId(Connection connection) {
        return getId(connection, "select nextval('recensioni_id') as id");
    }

    public static Integer getAsteId(Connection connection){
        return getId(connection, "select nextval('aste_id') as id");
    }

    public static Integer getImagesId(Connection connection){
        return getId(connection, "select nextval('images_id') as id");
    }

    public static Integer getAcquistoId(Connection connection) {
        return getId(connection, "select nextval('acquisti_id') as id");
    }

    public static Integer getOrdineId(Connection connection) {
        return getId(connection, "select nextval('ordini_id') as id");
    }
}
