package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.persistence.model.utente;
import com.example.swankicksbackend.service.utenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200") // Porta di angular
@RequestMapping("/api/utenti")
@RequiredArgsConstructor // Crea in automatico un'istanza di UtenteService
public class utenteController {
    private final utenteService i;
    private final HttpServletRequest request;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUtente(@RequestBody utente utente) {
        i.createUtente(utente);
    }

    @GetMapping("/{string}")
    public ResponseEntity<utente> findByID(@PathVariable String string) {
        if (string.contains("@")) return i.getByEmail(string);
        return i.getByID(string);
    }

    @DeleteMapping("/{cf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUtente(@PathVariable String cf) {
        return i.deleteByID(cf);
    }

    @PutMapping("/{cf}")
    public ResponseEntity<utente> updateUtente(@PathVariable String cf, @RequestBody utente utente) {
        return i.updateUtente(cf, utente);
    }

    @GetMapping("/user-details")
    public ResponseEntity<utente> getUserDetails(@RequestParam String sessionId) {
        HttpSession session = (HttpSession) request.getServletContext().getAttribute(sessionId);
        if(session != null) {
            utente utente = (utente) session.getAttribute("utente");
            return ResponseEntity.ok(utente);
        }
        return ResponseEntity.notFound().build();
    }
}