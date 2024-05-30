package com.example.swankicksbackend.persistence.model;

public class aste {
    private Integer id;

    private Integer scarpa; //id della scarpa messo in asta

    private String acquirente; //l'ultimo che ha fatto la proposta (ovviamente più alta di quella corrente)

    private Integer prezzo_partenza;

    private Integer prezzo_corrente; //sempre maggiore del prezzo di partenza

    private Long fine;

    private String nome;

    private Integer taglia;

    private String immagine;


    public aste(Integer id, Integer scarpa, String acquirente, Integer prezzo_partenza, Integer prezzo_corrente, Long fine, String nome, Integer taglia, String immagine)
    {
        this.id = id;
        this.scarpa = scarpa;
        this.acquirente = acquirente;
        this.prezzo_partenza = prezzo_partenza;
        this.prezzo_corrente = prezzo_corrente;
        this.fine = fine;
        this.nome = nome;
        this.taglia = taglia;
        this.immagine = immagine;
    }

    public aste() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScarpa() {
        return scarpa;
    }

    public void setScarpa(Integer scarpa) {
        this.scarpa = scarpa;
    }

    public String getAcquirente() {
        return acquirente;
    }

    public void setAcquirente(String acquirente) {
        this.acquirente = acquirente;
    }

    public Integer getPrezzo_partenza() {
        return prezzo_partenza;
    }

    public void setPrezzo_partenza(Integer prezzo_partenza) {
        this.prezzo_partenza = prezzo_partenza;
    }

    public Integer getPrezzo_corrente() {
        return prezzo_corrente;
    }

    public void setPrezzo_corrente(Integer prezzo_corrente) {
        this.prezzo_corrente = prezzo_corrente;
    }

    public Long getFine() {
        return fine;
    }

    public void setFine(Long fine) {
        this.fine = fine;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Integer getTaglia() { return taglia; }

    public void setTaglia(Integer taglia) { this.taglia = taglia; }

    public String getImmagine() { return immagine; }

    public void setImmagine(String immagine) { this.immagine = immagine; }
}
