package com.example.swankicksbackend.service;

import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.model.images;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class imagesService {
    public void createImage(images image) {
        DBManager.getInstance().getImagesDAO().save(image);
    }

    public ResponseEntity<images> getByID(Integer id) {
        images image = DBManager.getInstance().getImagesDAO().findByPrimaryKey(id);
        if (image == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(image);
    }

    public ResponseEntity<List<images>> getAllEntries() {
        List<images> images = DBManager.getInstance().getImagesDAO().findAll();
        if(images == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(images);
    }

    public ResponseEntity<List<images>> getImagesByScarpaID(Integer id) {
        List<images> images = DBManager.getInstance().getImagesDAO().findByScarpaID(id);
        if(images == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(images);
    }

    public ResponseEntity<Object> deleteByID(Integer id) {
        images image = DBManager.getInstance().getImagesDAO().findByPrimaryKey(id);
        if(image == null) return ResponseEntity.notFound().build();
        DBManager.getInstance().getImagesDAO().delete(image);
        return ResponseEntity.noContent().build();
    }
}