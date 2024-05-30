package com.example.swankicksbackend.persistence.dao.postgres;


import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.asteDao;
import com.example.swankicksbackend.persistence.model.aste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class asteDaoPostgres implements asteDao {
    Connection connection;

    public asteDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public aste createNewEntity(ResultSet rs) throws SQLException {
        aste a = new aste();
        a.setId(rs.getInt("id"));
        a.setScarpa(rs.getInt("scarpa"));
        a.setAcquirente(rs.getString("acquirente"));
        a.setPrezzo_partenza(rs.getInt("prezzo_partenza"));
        a.setPrezzo_corrente(rs.getInt("prezzo_corrente"));
        a.setFine(rs.getLong("fine"));
        a.setNome(rs.getString("nome"));
        a.setTaglia(rs.getInt("taglia"));
        a.setImmagine(rs.getString("immagine"));
        return a;
    }

    @Override
    public List<aste> findAll() {
        ArrayList<aste> aste = new ArrayList<>();
        String query = "select * from aste";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { aste.add(createNewEntity(rs)); }
            return aste;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public aste findByPrimaryKey(Integer id) {
        String query = "select * from aste where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public aste findByScarpa(Integer id) {
        String query = "select * from aste where scarpa=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) { return createNewEntity(rs); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(aste aste) {
        PreparedStatement st = null;
        Integer id = null;
        try {
            if(aste.getId() == null) {
                String insertQuery = "insert into aste(scarpa, acquirente, prezzo_partenza, prezzo_corrente, fine, nome, taglia, immagine, id) values(?,?,?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
                id = IdBroker.getAsteId(connection);
            } else {
                String updateQuery = "update aste set scarpa = ?, acquirente = ?, prezzo_partenza = ?, prezzo_corrente = ?, fine = ?, nome = ?, taglia = ?, immagine = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
                id = aste.getId();
            }
            st.setInt(1, aste.getScarpa());
            st.setString(2, aste.getAcquirente());
            st.setInt(3, aste.getPrezzo_partenza());
            st.setInt(4, aste.getPrezzo_corrente());
            st.setLong(5, aste.getFine());
            st.setString(6, aste.getNome());
            st.setInt(7, aste.getTaglia());
            st.setString(8,aste.getImmagine());
            st.setInt(9, id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(aste aste) {
        if(findByPrimaryKey(aste.getId()) == null) return;
        String query = "delete from aste where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, aste.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
