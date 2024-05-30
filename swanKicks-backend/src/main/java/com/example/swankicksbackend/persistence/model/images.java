package com.example.swankicksbackend.persistence.model;

public class images {
    private Integer id;
    private Integer scarpa;
    private String img;

    public images(Integer id, Integer scarpa, String img) {
        this.id = id;
        this.scarpa = scarpa;
        this.img = img;
    }

    public images() {}

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
