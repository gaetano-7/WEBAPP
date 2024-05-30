package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.acquisto;
import com.example.swankicksbackend.persistence.model.ordine;

import java.util.List;

public interface ordineDao {
    List<ordine> findAll(); //restituisce una lista di tutti gli annunci

    ordine findByPrimaryKey(Integer id); //restituisce un annuncio data la chiave primaria

    List<ordine> findByUtente(String utenteId); // Restituisce tutti gli acquisti dato l'ID dell' utente

    boolean saveOrUpdate(ordine ordine); //salva se non esiste o aggiorna se esiste

    void delete(ordine ordine);//cancella una scarpa

    boolean findCompletatoById(Integer id);
}
