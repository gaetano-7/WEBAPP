package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.ordine;
import com.example.swankicksbackend.service.ordineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/ordini")
@RequiredArgsConstructor
public class ordineController {
    private final ordineService o;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrdine(@RequestBody ordine ordine) { o.createOrdine(ordine); }

    @GetMapping("/{id}")
    public ResponseEntity<ordine> findByID(@PathVariable Integer id) { return o.getByID(id); }

    @GetMapping("/findByUtente/{utenteID}")
    public ResponseEntity<List<ordine>> findAllByUtente(@PathVariable String utenteID) { return o.getByUtenteID(utenteID); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteOrdine(@PathVariable Integer id) { return o.deleteByID(id); }

    @PutMapping("/{id}")
    public ResponseEntity<ordine> updateOrdine(@PathVariable Integer id, ordine ordine){
        return o.updateOrdine(id, ordine);
    }
}
