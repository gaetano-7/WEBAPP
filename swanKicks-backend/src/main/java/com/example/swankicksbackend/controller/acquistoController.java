package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.acquisto;
import com.example.swankicksbackend.service.acquistoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/acquisti")
@RequiredArgsConstructor
public class acquistoController {

    private final acquistoService a;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAcquisto(@RequestBody acquisto acquisto) { a.createAcquisto(acquisto); }

    @GetMapping("/{id}")
    public ResponseEntity<acquisto> findByID(@PathVariable Integer id) { return a.getByID(id); }

    @GetMapping("/findByUtente/{utenteID}")
    public ResponseEntity<List<acquisto>> findAllByUtente(@PathVariable String utenteID) { return a.getByUtenteID(utenteID); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteAcquisto(@PathVariable Integer id) { return a.deleteByID(id); }

    @PutMapping("/{id}")
    public ResponseEntity<acquisto> updateAcquisto(@PathVariable Integer id, acquisto acquisto){
        return a.updateAcquisto(id, acquisto);
    }

}
