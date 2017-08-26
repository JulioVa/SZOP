package com.database.dto;

import java.io.Serializable;

public class SystemDto implements Serializable {

    private Integer userId;
    private String name;

    public SystemDto() {}

    public SystemDto(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemDto systemDto = (SystemDto) o;

        if (userId != null ? !userId.equals(systemDto.userId) : systemDto.userId != null) return false;
        return name != null ? name.equals(systemDto.name) : systemDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SystemDto{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
