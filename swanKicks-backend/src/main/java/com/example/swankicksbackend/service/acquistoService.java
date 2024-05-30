package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.acquisto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class acquistoService {

    public void createAcquisto(acquisto acquisto){
        DBManager.getInstance().getAcquistoDAO().saveOrUpdate(acquisto);
    }

    public ResponseEntity<acquisto> getByID(Integer id){
        acquisto acquisto = DBManager.getInstance().getAcquistoDAO().findByPrimaryKey(id);
        if(acquisto == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(acquisto); //genera un entità di risposta positiva
    }

    public ResponseEntity<List<acquisto>> getByUtenteID(String utenteID) {
        List<acquisto> utenti = DBManager.getInstance().getAcquistoDAO().findByUtente(utenteID);
        if(utenti == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(utenti);
    }

    public ResponseEntity<Object> deleteByID(Integer id){
        acquisto acquisto = DBManager.getInstance().getAcquistoDAO().findByPrimaryKey(id);
        if (acquisto == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getAcquistoDAO().delete(acquisto);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<acquisto> updateAcquisto(Integer id, acquisto acquisto){
        acquisto acquisto1 = DBManager.getInstance().getAcquistoDAO().findByPrimaryKey(id);
        if(acquisto == null)
            return ResponseEntity.notFound().build();
        else{
            acquisto1.setUtente(acquisto.getUtente());
            acquisto1.setScarpa(acquisto.getScarpa());
            if(DBManager.getInstance().getAcquistoDAO().saveOrUpdate(acquisto1))
                return ResponseEntity.ok(acquisto1);
            else return ResponseEntity.internalServerError().build();
        }
    }
}
