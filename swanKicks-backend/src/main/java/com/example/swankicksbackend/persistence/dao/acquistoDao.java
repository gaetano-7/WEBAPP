package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.acquisto;

import java.util.List;

public interface acquistoDao {
    List<acquisto> findAll(); //restituisce una lista di tutti gli annunci

    acquisto findByPrimaryKey(Integer id); //restituisce un annuncio data la chiave primaria

    List<acquisto> findByUtente(String utenteId); // Restituisce tutti gli acquisti dato l'ID dell' utente

    boolean saveOrUpdate(acquisto acquisto); //salva se non esiste o aggiorna se esiste

    void delete(acquisto acquisto);//cancella una scarpa

    boolean findCompletatoById(Integer id);
}
