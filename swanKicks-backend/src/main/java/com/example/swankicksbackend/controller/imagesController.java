package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.images;
import com.example.swankicksbackend.service.imagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class imagesController {
    private final imagesService i;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createImage(@RequestBody images image) {
        i.createImage(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<images> findByID(@PathVariable Integer id) {
        return i.getByID(id);
    }

    @GetMapping("/findByScarpa/{scarpaID}")
    public ResponseEntity<List<images>> findByScarpaID(@PathVariable Integer scarpaID) {
        return i.getImagesByScarpaID(scarpaID);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteImage(@PathVariable Integer id) {
        return i.deleteByID(id);
    }
}