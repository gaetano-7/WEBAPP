package com.example.swankicksbackend.persistence.model;

public class acquisto {
    private Integer id;

    private String utente;

    private Integer scarpa;

    private String nome;

    private Integer prezzo;

    private Integer taglia;

    private String immagine;

    private Boolean completato;

    public acquisto(Integer id, String utente, Integer scarpa, String nome, Integer prezzo, Integer taglia, String immagine, Boolean completato){
        this.id = id;
        this.utente = utente;
        this.scarpa = scarpa;
        this.nome = nome;
        this.prezzo = prezzo;
        this.taglia = taglia;
        this.immagine = immagine;
        this.completato = completato;
    }

    public acquisto(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public Integer getScarpa() {
        return scarpa;
    }

    public void setScarpa(Integer scarpa) {
        this.scarpa = scarpa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getTaglia() {
        return taglia;
    }

    public void setTaglia(Integer taglia) {
        this.taglia = taglia;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public Boolean getCompletato() { return completato; }

    public void setCompletato(Boolean completato) { this.completato = completato; }


}
