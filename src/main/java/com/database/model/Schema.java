package com.database.model;

import javax.persistence.*;

@Entity
@Table(name = "schema")
public class Schema {

    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private byte[] img;

    public Schema() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
