package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.aste;
import com.example.swankicksbackend.service.asteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200") //utile per frontend in quanto Angular usa la porta 4200
@RequestMapping("/api/aste") //tutti i metodi avranno nell'URL questa path come path principale
@RequiredArgsConstructor //crea in automatico un'istanza di AsteService
public class asteController {
    private final asteService a;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAste(@RequestBody aste aste){
        a.createAste(aste);
    }


    @GetMapping("/{id}")
    public ResponseEntity<aste> findByID(@PathVariable Integer id){
        return a.getByID(id);
    }

    @GetMapping("/findByScarpa/{scarpaID}")
    public ResponseEntity<aste> findByScarpaID(@PathVariable Integer scarpaID) {
        return a.getAstabyScarpaID(scarpaID);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteAste(@PathVariable Integer id){
        return a.deleteByID(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<aste> updateAste(@PathVariable Integer id, @RequestBody aste aste){
        return a.updateAste(id, aste);
    }
}