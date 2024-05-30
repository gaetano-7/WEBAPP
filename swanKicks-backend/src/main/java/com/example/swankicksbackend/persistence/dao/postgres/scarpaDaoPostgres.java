package com.example.swankicksbackend.persistence.dao.postgres;

import com.example.swankicksbackend.persistence.IdBroker;
import com.example.swankicksbackend.persistence.dao.scarpaDao;
import com.example.swankicksbackend.persistence.model.scarpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class scarpaDaoPostgres implements scarpaDao {
    Connection connection;
    public scarpaDaoPostgres(Connection connection) { //crea la connessione con il DB
        this.connection = connection;
    }

    public scarpa createNewEntity(ResultSet rs) throws SQLException {
        scarpa s = new scarpaProxy(connection);
        s.setId(rs.getInt("id"));
        s.setNome(rs.getString("nome"));
        s.setTipo(rs.getString("tipo"));
        s.setMarca(rs.getString("marca"));
        s.setTaglia(rs.getInt("taglia"));
        s.setPrezzo_orig(rs.getDouble("prezzo_orig"));
        s.setPrezzo_attuale(rs.getDouble("prezzo_attuale"));
        s.setAnno_uscita(rs.getInt("anno_uscita"));
        s.setColore(rs.getString("colore"));
        s.setProprietario(rs.getString("proprietario"));
        s.setTipo_annuncio(rs.getString("tipo_annuncio"));
        s.setVenduta(rs.getBoolean("venduta"));
        return s;
    }

    public List<scarpa> genericFind(String query) {
        ArrayList<scarpa> scarpe = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { scarpe.add(createNewEntity(rs)); }
            return scarpe;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<scarpa> findAll() {
        return genericFind("select * from scarpe");
    }

    @Override
    public List<scarpa> findByLowerPrice() {
        return genericFind("select * from scarpe order by prezzo");
    }

    @Override
    public List<scarpa> findByLowerPriceDESC() {
        return genericFind("select * from scarpe order by prezzo DESC");
    }

    @Override
    public List<scarpa> findByLowerRelease() {
        return genericFind("select * from scarpe order by anno_uscita");
    }

    @Override
    public List<scarpa> findByLowerReleaseDESC() {
        return genericFind("select * from scarpe order by anno_uscita DESC");
    }

    @Override
    public Integer getLastAddedByOwner(String cf) {
        String query = "select MAX(id) AS max_id from scarpe where proprietario=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<scarpa> findAllByOwner(String cf) {
        ArrayList<scarpa> scarpe = new ArrayList<>();
        String query = "select * from scarpe where proprietario=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            while(rs.next()) { scarpe.add(createNewEntity(rs)); }
            return scarpe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public scarpa findByPrimaryKey(Integer id) {
        String query = "select * from scarpe where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return createNewEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(scarpa scarpa) {
        PreparedStatement st = null;
        Integer id = null;
        try {
            if(scarpa.getId() == null) {
                String insertQuery = "insert into scarpe(nome, tipo, marca, taglia, prezzo_orig, prezzo_attuale, descrizione, anno_uscita, colore, proprietario, tipo_annuncio, venduta, id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                st = connection.prepareStatement(insertQuery);
                id = IdBroker.getScarpaId(connection);
            } else {
                String updateQuery = "update scarpe set nome = ?, tipo = ?, marca = ?, taglia = ?, prezzo_orig = ?, prezzo_attuale = ?, descrizione = ?, anno_uscita = ?, colore = ?, proprietario = ?, tipo_annuncio = ?, venduta = ? where id = ?";
                st = connection.prepareStatement(updateQuery);
                id = scarpa.getId();
            }

            st.setString(1, scarpa.getNome());
            st.setString(2, scarpa.getTipo());
            st.setString(3, scarpa.getMarca());
            st.setInt(4, scarpa.getTaglia());
            st.setDouble(5,scarpa.getPrezzo_orig());
            st.setDouble(6, scarpa.getPrezzo_attuale());
            st.setString(7,scarpa.getDescrizione());
            st.setInt(8, scarpa.getAnno_uscita());
            st.setString(9,scarpa.getColore());
            st.setString(10, scarpa.getProprietario());
            st.setString(11, scarpa.getTipo_annuncio());
            st.setBoolean(12, scarpa.getVenduta());
            st.setInt(13, id);


            st.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(scarpa scarpa) {
        if (findByPrimaryKey(scarpa.getId()) == null) return;
        String query = "delete from scarpe where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, scarpa.getId());
            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
