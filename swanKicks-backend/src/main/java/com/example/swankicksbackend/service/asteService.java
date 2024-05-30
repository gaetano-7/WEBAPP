package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.aste;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class asteService {

    public void createAste(aste aste) {
        DBManager.getInstance().getAsteDAO().saveOrUpdate(aste);
    }

    public ResponseEntity<aste> getByID(Integer id) {
        aste aste = DBManager.getInstance().getAsteDAO().findByPrimaryKey(id);
        if(aste == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(aste); //genera un entità di risposta positiva
    }

    public ResponseEntity<Object> deleteByID(Integer id) {
        aste aste = DBManager.getInstance().getAsteDAO().findByPrimaryKey(id);
        if(aste == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getAsteDAO().delete(aste);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<aste> getAstabyScarpaID(Integer id) {
        aste aste = DBManager.getInstance().getAsteDAO().findByScarpa(id);
        if(aste == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(aste);
    }

    public ResponseEntity<aste> updateAste(Integer id, aste aste) {
        aste aste1 = DBManager.getInstance().getAsteDAO().findByPrimaryKey(id);
        if(aste == null)
            return ResponseEntity.notFound().build();
        else{
            aste1.setScarpa(aste.getScarpa());
            aste1.setAcquirente(aste.getAcquirente());
            aste1.setPrezzo_partenza(aste.getPrezzo_partenza());
            aste1.setPrezzo_corrente(aste.getPrezzo_corrente());
            aste1.setFine(aste.getFine());
            aste1.setNome(aste.getNome());
            aste1.setTaglia(aste.getTaglia());
            aste1.setImmagine(aste.getImmagine());
            if(DBManager.getInstance().getAsteDAO().saveOrUpdate(aste1))
                return ResponseEntity.ok(aste1);
            else return ResponseEntity.internalServerError().build();
        }
    }
}
