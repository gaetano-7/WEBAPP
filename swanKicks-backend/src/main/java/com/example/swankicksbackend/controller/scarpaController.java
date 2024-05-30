package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.scarpa;
import com.example.swankicksbackend.service.scarpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200") //utile per frontend in quanto Angular usa la porta 4200
@RequestMapping("/api/scarpe") //tutti i metodi avranno nell'URL questa path come path principale
@RequiredArgsConstructor //crea in automatico un'istanza di ImmobileService
public class scarpaController {
    private final scarpaService s;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createScarpa(@RequestBody scarpa scarpa){
        s.createScarpa(scarpa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<scarpa> findByID(@PathVariable Integer id){
        return s.getByID(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<scarpa>> findAll(){
        return s.getAllEntries();
    }

    @GetMapping("/findAllLP")
    public ResponseEntity<List<scarpa>> findByLowerPrice() {
        return s.getAllEntriesLP();
    }

    @GetMapping("/findAllLPD")
    public ResponseEntity<List<scarpa>> findByLowerPriceDESC() {
        return s.getAllEntriesLPDESC();
    }

    @GetMapping("/findAllLA")
    public ResponseEntity<List<scarpa>> findByLowerRelease() {
        return s.getAllEntriesLA();
    }

    @GetMapping("/findAllLAD")
    public ResponseEntity<List<scarpa>> findByLowerReleaseDESC() {
        return s.getAllEntriesLADESC();
    }

    @GetMapping("/getLastAddedByOwner/{cf}")
    public ResponseEntity<Integer> getLastAddedByOwner(@PathVariable String cf) {
        return s.getLastAddedByOwner(cf);
    }

    @GetMapping("/findAllByOwner/{cf}")
    public ResponseEntity<List<scarpa>> findAllByOwner(@PathVariable String cf) {
        return s.findAllByOwner(cf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteScarpa(@PathVariable Integer id){
        return s.deleteByID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<scarpa> updateScarpa(@PathVariable Integer id, @RequestBody scarpa scarpa){
        return s.updateScarpa(id, scarpa);
    }


}