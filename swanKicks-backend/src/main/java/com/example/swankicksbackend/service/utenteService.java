package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.utente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class utenteService {
    public void createUtente(utente utente) {
        DBManager.getInstance().getUtenteDAO().saveOrUpdate(utente);
    }

    public ResponseEntity<utente> getByID(String cf) {
        utente utente = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build(); // 404 page
        return ResponseEntity.ok(utente); // Genera un entità di risposta positiva
    }

    public ResponseEntity<utente> getByEmail(String email) {
        utente utente = DBManager.getInstance().getUtenteDAO().findByEmail(email);
        if (utente == null) return ResponseEntity.notFound().build(); // 404 page
        return ResponseEntity.ok(utente); // Genera un entità di risposta positiva
    }

    public ResponseEntity<Object> deleteByID(String cf) {
        utente utente = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build();
        DBManager.getInstance().getUtenteDAO().delete(utente);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<utente> updateUtente(String cf, utente utente) {
        utente utente1 = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build();
        else {
            utente1.setNome(utente.getNome());
            utente1.setCognome(utente.getCognome());
            utente1.setEmail(utente.getEmail());
            utente1.setTelefono(utente.getTelefono());
            utente1.setTipologia(utente.getTipologia());
            utente1.setPassword(utente.getPassword());
            utente1.setBannato(utente.getBannato());
            if(DBManager.getInstance().getUtenteDAO().saveOrUpdate(utente1))
                return ResponseEntity.ok(utente1);
            else return ResponseEntity.internalServerError().build();
        }
    }
}
