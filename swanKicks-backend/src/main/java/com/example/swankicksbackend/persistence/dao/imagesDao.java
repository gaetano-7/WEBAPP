package com.example.swankicksbackend.persistence.dao;

import com.example.swankicksbackend.persistence.model.images;

import java.util.List;

public interface imagesDao {
    List<images> findAll();

    images findByPrimaryKey(Integer id);

    List<images> findByScarpaID(Integer id);

    boolean save(images image);

    void delete(images image);
}
