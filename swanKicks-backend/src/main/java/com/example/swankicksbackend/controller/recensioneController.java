package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.recensione;
import com.example.swankicksbackend.service.recensioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/recensioni")
@RequiredArgsConstructor
public class recensioneController {

    private final recensioneService r;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecensione(@RequestBody recensione recensione) { r.createRecensione(recensione); }

    @GetMapping("/{id}")
    public ResponseEntity<recensione> findByID(@PathVariable Integer id) { return r.getByID(id); }

    @GetMapping("/findByScarpa/{scarpaID}")
    public ResponseEntity<List<recensione>> findAllByScarpa(@PathVariable Integer scarpaID) { return r.getByScarpaID(scarpaID); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteRecensione(@PathVariable Integer id) { return r.deleteByID(id); }

    @PutMapping("/{id}")
    public ResponseEntity<recensione> updateRecensione(@PathVariable Integer id, recensione recensione){
        return r.updateRecensione(id, recensione);
    }
}