package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.scarpa;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class scarpaService {

    public void createScarpa(scarpa scarpa) {
        DBManager.getInstance().getScarpaDAO().saveOrUpdate(scarpa);
    }

    public ResponseEntity<scarpa> getByID(Integer id) {
        scarpa scarpa = DBManager.getInstance().getScarpaDAO().findByPrimaryKey(id);
        if (scarpa == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(scarpa); //genera un entità di risposta positiva
    }

    public ResponseEntity<List<scarpa>> getAllEntries() {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findAll();
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<List<scarpa>> getAllEntriesLP() {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findByLowerPrice();
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<List<scarpa>> getAllEntriesLPDESC() {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findByLowerPriceDESC();
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<List<scarpa>> getAllEntriesLA() {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findByLowerRelease();
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<List<scarpa>> getAllEntriesLADESC() {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findByLowerReleaseDESC();
        if(scarpe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(scarpe);
    }

    public ResponseEntity<Integer> getLastAddedByOwner(String cf) {
        Integer scarpa_id = DBManager.getInstance().getScarpaDAO().getLastAddedByOwner(cf);
        if (scarpa_id == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(scarpa_id); //genera un entità di risposta positiva
    }

    public ResponseEntity<List<scarpa>> findAllByOwner(String cf) {
        List<scarpa> scarpe = DBManager.getInstance().getScarpaDAO().findAllByOwner(cf);
        if (scarpe == null)
            return ResponseEntity.notFound().build(); //se non esiste restituisce il '404:file not found'
        return ResponseEntity.ok(scarpe); //genera un entità di risposta positiva
    }

    public ResponseEntity<Object> deleteByID(Integer id) {
        scarpa scarpa = DBManager.getInstance().getScarpaDAO().findByPrimaryKey(id);
        if(scarpa == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getScarpaDAO().delete(scarpa);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<scarpa> updateScarpa(Integer id, scarpa scarpa) {
        scarpa scarpa1 = DBManager.getInstance().getScarpaDAO().findByPrimaryKey(id);
        if(scarpa == null)
            return ResponseEntity.notFound().build();
        else{
            scarpa1.setNome(scarpa.getNome());
            scarpa1.setTipo(scarpa.getTipo());
            scarpa1.setMarca(scarpa.getMarca());
            scarpa1.setTaglia(scarpa.getTaglia());
            scarpa1.setPrezzo_orig(scarpa.getPrezzo_orig());
            scarpa1.setPrezzo_attuale(scarpa.getPrezzo_attuale());
            scarpa1.setDescrizione(scarpa.getDescrizione());
            scarpa1.setAnno_uscita(scarpa.getAnno_uscita());
            scarpa1.setColore(scarpa.getColore());
            scarpa1.setTipo_annuncio(scarpa.getTipo_annuncio());
            scarpa1.setVenduta(scarpa.getVenduta());
            if(DBManager.getInstance().getScarpaDAO().saveOrUpdate(scarpa1))
                return ResponseEntity.ok(scarpa1);
            else return ResponseEntity.internalServerError().build();
        }
    }
}