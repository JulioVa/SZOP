package com.database.dto;

public class SchemaIdLevelDto extends SchemaDto {

    private int id;

    public SchemaIdLevelDto() {}

    public SchemaIdLevelDto(String name, byte[] img, int id, int userId) {
        super(name, img, userId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SchemaIdLevelDto that = (SchemaIdLevelDto) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
