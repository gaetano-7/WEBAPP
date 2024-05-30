package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.ordineDao;
import com.example.swankicksbackend.persistence.model.ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ordineDaoPostgres implements ordineDao{
    Connection connection;

    public ordineDaoPostgres(Connection connection) {this.connection = connection;}

    public ordine createNewEntity(ResultSet rs) throws SQLException {
        ordine o = new ordine();
        o.setId(rs.getInt("id"));
        o.setUtente(rs.getString("utente"));
        o.setScarpa(rs.getInt("scarpa"));
        return o;
    }

    @Override
    public List<ordine> findAll() {
        ArrayList<ordine> ordini = new ArrayList<>();
        String query = "select * from ordini";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { ordini.add(createNewEntity(rs)); }
            return ordini;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ordine findByPrimaryKey(Integer id) {
        String query = "select * from ordini where id = ?";
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
    public List<ordine> findByUtente(String utenteId) {
        List<ordine> ordini = new ArrayList<>();
        String query = "select * from ordini where utente = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, utenteId);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { ordini.add(createNewEntity(rs)); }
            return ordini;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(ordine ordine) {
        PreparedStatement st = null;
        Integer id = null;
        try {
            if (ordine.getId() == null) {
                String insertQuery = "insert into ordini(utente, scarpa, nome, prezzo, taglia, immagine, id) values(?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
                id = IdBroker.getOrdineId(connection);
            } else {
                String updateQuery = "update ordini set utente = ?, scarpa = ?, nome = ?, prezzo = ?, taglia = ?, immagine = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
                id = ordine.getId();
            }

            st.setString(1, ordine.getUtente());
            st.setInt(2, ordine.getScarpa());
            st.setString(3, ordine.getNome());
            st.setInt(4, ordine.getPrezzo());
            st.setInt(5, ordine.getTaglia());
            st.setString(6, ordine.getImmagine());
            st.setInt(7, id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(ordine ordine) {
        if (findByPrimaryKey(ordine.getId()) == null) return;
        String query = "delete from ordini where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, ordine.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findCompletatoById(Integer id) {
        String query = "SELECT completato FROM ordini WHERE id = ?";
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
