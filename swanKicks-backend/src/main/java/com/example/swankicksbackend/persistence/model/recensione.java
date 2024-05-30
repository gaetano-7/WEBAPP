package com.example.swankicksbackend.persistence.model;

public class recensione {
    private Integer id;

    private String titolo;

    private Short rating;

    private String autore;

    private Integer scarpa;

    public recensione(Integer id, String titolo, Short rating, String autore, Integer scarpa) {
        this.id = id;
        this.titolo = titolo;
        this.rating = rating;
        this.autore = autore;
        this.scarpa = scarpa;
    }

    public recensione() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Integer getScarpa() {
        return scarpa;
    }

    public void setScarpa(Integer scarpa) {
        this.scarpa = scarpa;
    }
}
