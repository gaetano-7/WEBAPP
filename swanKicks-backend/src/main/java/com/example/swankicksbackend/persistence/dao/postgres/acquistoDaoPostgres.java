package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.acquistoDao;
import com.example.swankicksbackend.persistence.model.acquisto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class acquistoDaoPostgres implements acquistoDao{
    Connection connection;

    public acquistoDaoPostgres(Connection connection) {this.connection = connection;}

    public acquisto createNewEntity(ResultSet rs) throws SQLException {
        acquisto a = new acquisto();
        a.setId(rs.getInt("id"));
        a.setUtente(rs.getString("utente"));
        a.setScarpa(rs.getInt("scarpa"));
        return a;
    }

    @Override
    public List<acquisto> findAll() {
        ArrayList<acquisto> acquisti = new ArrayList<>();
        String query = "select * from acquisti";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { acquisti.add(createNewEntity(rs)); }
            return acquisti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public acquisto findByPrimaryKey(Integer id) {
        String query = "select * from acquisti where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewEntity(rs); }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<acquisto> findByUtente(String utenteId) {
        List<acquisto> acquisti = new ArrayList<>();
        String query = "select * from acquisti where utente = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, utenteId);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { acquisti.add(createNewEntity(rs)); }
            return acquisti;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(acquisto acquisto) {
        PreparedStatement st = null;
        Integer id = null;
        try {
            if (acquisto.getId() == null) {
                String insertQuery = "insert into acquisti(utente, scarpa, nome, prezzo, taglia, immagine, completato, id) values(?,?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
                id = IdBroker.getAcquistoId(connection);
            } else {
                String updateQuery = "update acquisti set utente = ?, scarpa = ?, nome = ?, prezzo = ?, taglia = ?, immagine = ?, completato = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
                id = acquisto.getId();
            }

            st.setString(1, acquisto.getUtente());
            st.setInt(2, acquisto.getScarpa());
            st.setString(3, acquisto.getNome());
            st.setInt(4, acquisto.getPrezzo());
            st.setInt(5, acquisto.getTaglia());
            st.setString(6, acquisto.getImmagine());
            st.setBoolean(7, acquisto.getCompletato());
            st.setInt(8, id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(acquisto acquisto) {
        if (findByPrimaryKey(acquisto.getId()) == null) return;
        String query = "delete from acquisti where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, acquisto.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findCompletatoById(Integer id) {
        String query = "SELECT completato FROM acquisti WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("completato");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
