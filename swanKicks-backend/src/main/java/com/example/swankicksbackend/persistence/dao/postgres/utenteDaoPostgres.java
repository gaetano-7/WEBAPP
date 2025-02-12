package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.dao.utenteDao;
import com.example.swankicksbackend.persistence.model.utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class utenteDaoPostgres implements utenteDao {
    Connection connection;

    public utenteDaoPostgres(Connection connection) { this.connection = connection; }

    public utente createNewEntity(ResultSet rs) throws SQLException {
        utente u = new utente();
        u.setId(rs.getString("id"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setEmail(rs.getString("email"));
        u.setTelefono(rs.getLong("telefono"));
        u.setTipologia(rs.getString("tipologia"));
        u.setPassword(rs.getString("password"));
        u.setBannato(rs.getBoolean("bannato"));
        return u;
    }

    @Override
    public List<utente> findAll() {
        ArrayList<utente> utenti = new ArrayList<>();
        String query = "select * from utenti";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { utenti.add(createNewEntity(rs)); }
            return utenti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public utente findByPrimaryKey(String cf) {
        String query = "select * from utenti where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public utente findByEmail(String email) {
        String query = "select * from utenti where email=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(utente utente) {
        PreparedStatement st = null;
        try {
            utente u = findByPrimaryKey(utente.getId());
            if (u == null) {
                String insertQuery = "insert into utenti(nome, cognome, email, telefono, tipologia, password, bannato, id) values(?,?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
            } else {
                String updateQuery = "update utenti set nome = ?, cognome = ?, email = ?, telefono = ?, tipologia = ?, password = ?, bannato = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
            }

            st.setString(1, utente.getNome().substring(0, 1).toUpperCase() + utente.getNome().substring(1).toLowerCase());
            st.setString(2, utente.getCognome().substring(0, 1).toUpperCase() + utente.getCognome().substring(1).toLowerCase());
            st.setString(3, utente.getEmail().toLowerCase());
            st.setLong(4, utente.getTelefono());
            st.setString(5, utente.getTipologia().toLowerCase());
            st.setString(6, utente.getPassword());
            st.setBoolean(7, utente.getBannato());
            st.setString(8, utente.getId().toUpperCase());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(utente utente) {
        if (findByPrimaryKey(utente.getId()) == null) return;
        String query = "delete from utenti where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, utente.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
