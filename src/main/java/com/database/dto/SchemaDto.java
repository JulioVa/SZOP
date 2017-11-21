package com.database.dto;

import java.io.Serializable;
import java.util.Arrays;

public class SchemaDto implements Serializable {

    private String name;
    private byte[] img;
    private int userId;

    public SchemaDto() {}

    public SchemaDto(String name, byte[] img, int userId) {
        this.name = name;
        this.img = img;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchemaDto schemaDto = (SchemaDto) o;

        if (name != null ? !name.equals(schemaDto.name) : schemaDto.name != null) return false;
        if (userId != (schemaDto.userId)) return false;
        return Arrays.equals(img, schemaDto.img);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(img);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "SchemaDto{" +
                "name='" + name + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }
}
