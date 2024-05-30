package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.recensione;

import java.util.List;

public interface recensioneDao {
    List<recensione> findAll(); //restituisce una lista di tutti gli annunci

    recensione findByPrimaryKey(Integer id); //restituisce un annuncio data la chiave primaria

    List<recensione> findByScarpa(Integer scarpaId); // Restituisce tutte le recensioni dato l'ID della scarpa

    boolean saveOrUpdate(recensione recensione); //salva se non esiste o aggiorna se esiste

    void delete(recensione recensione);//cancella una scarpa
}
