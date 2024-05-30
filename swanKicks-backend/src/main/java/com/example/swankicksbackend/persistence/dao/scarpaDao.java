package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.scarpa;

import java.util.List;

public interface scarpaDao {
    List<scarpa> findAll(); //restituisce una lista di tutti gli annunci

    scarpa findByPrimaryKey(Integer id); //restituisce un annuncio data la chiave primaria

    boolean saveOrUpdate(scarpa scarpa); //salva se non esiste o aggiorna se esiste

    void delete(scarpa scarpa);//cancella una scarpa

    List<scarpa> findByLowerPrice();

    List<scarpa> findByLowerPriceDESC();

    List<scarpa> findByLowerRelease();

    List<scarpa> findByLowerReleaseDESC();

    Integer getLastAddedByOwner(String cf);

    List<scarpa> findAllByOwner(String cf);

}
