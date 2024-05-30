package com.example.swankicksbackend.persistence.dao.postgres;


import com.example.swankicksbackend.persistence.model.scarpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class scarpaProxy extends scarpa {
    Connection connection;

    public scarpaProxy(Connection connection){ this.connection = connection;}

    @Override
    public String getDescrizione() {
        if (super.getDescrizione() == null) {
            String query = "SELECT descrizione FROM scarpe WHERE id = ?";
            try (
                    PreparedStatement st = this.connection.prepareStatement(query)) {
                st.setInt(1, getId());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        super.setDescrizione(rs.getString("descrizione"));
                        return rs.getString("descrizione");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return super.getDescrizione();
    }
}
