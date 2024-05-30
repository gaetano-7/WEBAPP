package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.ordine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ordineService {

    public void createOrdine(ordine ordine){
        DBManager.getInstance().getOrdineDAO().saveOrUpdate(ordine);
    }

    public ResponseEntity<ordine> getByID(Integer id){
        ordine ordine = DBManager.getInstance().getOrdineDAO().findByPrimaryKey(id);
        if(ordine == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(ordine); //genera un entità di risposta positiva
    }

    public ResponseEntity<List<ordine>> getByUtenteID(String utenteID) {
        List<ordine> utenti = DBManager.getInstance().getOrdineDAO().findByUtente(utenteID);
        if(utenti == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(utenti);
    }

    public ResponseEntity<Object> deleteByID(Integer id){
        ordine ordine = DBManager.getInstance().getOrdineDAO().findByPrimaryKey(id);
        if (ordine == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getOrdineDAO().delete(ordine);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ordine> updateOrdine(Integer id, ordine ordine){
        ordine ordine1 = DBManager.getInstance().getOrdineDAO().findByPrimaryKey(id);
        if(ordine == null)
            return ResponseEntity.notFound().build();
        else{
            ordine1.setUtente(ordine.getUtente());
            ordine1.setScarpa(ordine.getScarpa());
            if(DBManager.getInstance().getOrdineDAO().saveOrUpdate(ordine1))
                return ResponseEntity.ok(ordine1);
            else return ResponseEntity.internalServerError().build();
        }
    }
}
