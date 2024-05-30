package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.imagesDao;
import com.example.swankicksbackend.persistence.model.images;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class imagesDaoPostgres implements imagesDao {
    Connection connection;
    public imagesDaoPostgres(Connection connection) { //crea la connessione con il DB
        this.connection = connection;
    }

    public images createNewEntity(ResultSet rs) throws SQLException {
        images i = new images();
        i.setId(rs.getInt("id"));
        i.setScarpa(rs.getInt("scarpa"));
        i.setImg(rs.getString("img"));
        return i;
    }

    @Override
    public List<images> findAll() {
        ArrayList<images> images = new ArrayList<>();
        String query = "select * from images";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { images.add(createNewEntity(rs)); }
            return images;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public images findByPrimaryKey(Integer id) {
        String query = "select * from images where id=?";
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
    public List<images> findByScarpaID(Integer id) {
        ArrayList<images> images = new ArrayList<>();
        String query = "select * from images where scarpa=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { images.add(createNewEntity(rs)); }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(images image) {
        try {
            Integer id = IdBroker.getImagesId(connection);
            String insertQuery = "insert into images(id, scarpa, img) values(?,?,?)";
            PreparedStatement st = connection.prepareStatement(insertQuery);
            st.setInt(1, id);
            st.setInt(2, image.getScarpa());
            st.setString(3, image.getImg());

            st.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(images image) {
        if (findByPrimaryKey(image.getId()) == null) return;
        String query = "delete from images where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, image.getId());
            st.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
