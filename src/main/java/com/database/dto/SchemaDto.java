package com.database.dto;

import java.io.Serializable;
import java.util.Arrays;

public class SchemaDto implements Serializable {

    private String name;
    private byte[] img;

    public SchemaDto() {}

    public SchemaDto(String name, byte[] img) {
        this.name = name;
        this.img = img;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchemaDto schemaDto = (SchemaDto) o;

        if (name != null ? !name.equals(schemaDto.name) : schemaDto.name != null) return false;
        return Arrays.equals(img, schemaDto.img);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(img);
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
