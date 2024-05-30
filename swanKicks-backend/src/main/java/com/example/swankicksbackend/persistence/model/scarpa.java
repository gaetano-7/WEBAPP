package com.example.swankicksbackend.persistence.model;

public class scarpa {
    private Integer id;
    private String nome;
    private String tipo;
    private String marca;
    private Integer taglia;
    private Double prezzo_orig;
    private Double prezzo_attuale;
    private String descrizione;
    private Integer anno_uscita;
    private String colore;
    private String proprietario;
    private String tipo_annuncio;
    private Boolean venduta;

    public scarpa(Integer id, String nome, String tipo, String marca, Integer taglia, Double prezzo_orig, Double prezzo_attuale, String descrizione, Integer anno_uscita, String colore, String proprietario, String tipo_annuncio, Boolean venduta) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.marca = marca;
        this.taglia =  taglia;
        this.prezzo_orig = prezzo_orig;
        this.prezzo_attuale = prezzo_attuale;
        this.descrizione = descrizione;
        this.anno_uscita = anno_uscita;
        this.colore = colore;
        this.proprietario = proprietario;
        this.tipo_annuncio = tipo_annuncio;
        this.venduta = venduta;
    }

    public scarpa() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getTaglia() { return taglia; }

    public void setTaglia(Integer taglia) {
        this.taglia = taglia;
    }

    public Double getPrezzo_orig() {
        return prezzo_orig;
    }

    public void setPrezzo_orig(Double prezzo_orig) {
        this.prezzo_orig = prezzo_orig;
    }

    public Double getPrezzo_attuale() {
        return prezzo_attuale;
    }

    public void setPrezzo_attuale(Double prezzo_attuale) {
        this.prezzo_attuale = prezzo_attuale;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getAnno_uscita() {
        return anno_uscita;
    }

    public void setAnno_uscita(Integer anno_uscita) {
        this.anno_uscita = anno_uscita;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getTipo_annuncio() {
        return tipo_annuncio;
    }

    public void setTipo_annuncio(String tipo_annuncio) {
        this.tipo_annuncio = tipo_annuncio;
    }

    public Boolean getVenduta() { return venduta; }

    public void setVenduta(Boolean venduta) { this.venduta = venduta; }
}


