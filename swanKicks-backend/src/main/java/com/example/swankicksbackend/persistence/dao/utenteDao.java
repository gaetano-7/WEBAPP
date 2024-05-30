package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.utente;

import java.util.List;

public interface utenteDao {
    List<utente> findAll(); // Restituisce una lista di utenti

    utente findByPrimaryKey(String cf); // Restituisce un utente dato il codice fiscale (id)

    utente findByEmail(String email); // Restituisce un utente data l'email

    boolean saveOrUpdate(utente utente); // Salva un nuovo utente se non esiste, altrimenti lo aggiorna

    void delete(utente utente); // Cancella un utente
}
