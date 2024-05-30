package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.recensione;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class recensioneService {

    public void createRecensione(recensione recensione){
        DBManager.getInstance().getRecensioneDAO().saveOrUpdate(recensione);
    }

    public ResponseEntity<recensione> getByID(Integer id){
        recensione recensione = DBManager.getInstance().getRecensioneDAO().findByPrimaryKey(id);
        if(recensione == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(recensione); //genera un entità di risposta positiva
    }

    public ResponseEntity<List<recensione>> getByScarpaID(Integer scarpaID) {
        List<recensione> scarpe = DBManager.getInstance().getRecensioneDAO().findByScarpa(scarpaID);
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<Object> deleteByID(Integer id){
        recensione recensione = DBManager.getInstance().getRecensioneDAO().findByPrimaryKey(id);
        if (recensione == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getRecensioneDAO().delete(recensione);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<recensione> updateRecensione(Integer id, recensione recensione){
        recensione recensione1 = DBManager.getInstance().getRecensioneDAO().findByPrimaryKey(id);
        if(recensione == null)
            return ResponseEntity.notFound().build();
        else{
            recensione1.setTitolo(recensione.getTitolo());
            recensione1.setRating(recensione.getRating());
            recensione1.setAutore(recensione.getAutore());
            recensione1.setScarpa(recensione.getScarpa());
            if(DBManager.getInstance().getRecensioneDAO().saveOrUpdate(recensione1))
                return ResponseEntity.ok(recensione1);
            else return ResponseEntity.internalServerError().build();

        }
    }
}