package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.recensioneDao;
import com.example.swankicksbackend.persistence.model.recensione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class recensioneDaoPostgres implements recensioneDao {
    Connection connection;

    public recensioneDaoPostgres(Connection connection) {
        this.connection = connection;
    }

    public recensione createNewEntity(ResultSet rs) throws SQLException {
        recensione r = new recensione();
        r.setId(rs.getInt("id"));
        r.setTitolo(rs.getString("titolo"));
        r.setRating(rs.getShort("rating"));
        r.setAutore(rs.getString("autore"));
        r.setScarpa(rs.getInt("scarpa"));
        return r;
    }

    @Override
    public List<recensione> findAll() {
        ArrayList<recensione> recensioni = new ArrayList<>();
        String query = "select * from recensioni";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { recensioni.add(createNewEntity(rs)); }
            return recensioni;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public recensione findByPrimaryKey(Integer id) {
        String query = "select * from recensioni where id = ?";
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
    public List<recensione> findByScarpa(Integer scarpaId) {
        List<recensione> recensioni = new ArrayList<>();
        String query = "select * from recensioni where scarpa = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, scarpaId);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { recensioni.add(createNewEntity(rs)); }
            return recensioni;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(recensione recensione) {
        PreparedStatement st = null;
        Integer id = null;
        try{
            if(recensione.getId() == null){
                String insertQuery = "insert into recensioni(titolo, rating, autore, scarpa, id) values(?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
                id = IdBroker.getRecensioneId(connection);
            } else {
                String updateQuery = "update recensioni set titolo = ?, rating = ?, autore = ?, scarpa = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
                id = recensione.getId();
            }
            st.setString(1, recensione.getTitolo());
            st.setShort(2, recensione.getRating());
            st.setString(3, recensione.getAutore());
            st.setInt(4, recensione.getScarpa());
            st.setInt(5, id);

            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(recensione recensione) {
        if (findByPrimaryKey(recensione.getId()) == null) return;
        String query = "delete from recensioni where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, recensione.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
