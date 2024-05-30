package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.aste;

import java.util.List;

public interface asteDao {
    List<aste> findAll();

    aste findByPrimaryKey(Integer id);

    aste findByScarpa(Integer id);

    boolean saveOrUpdate(aste aste);

    void delete(aste aste);
}
