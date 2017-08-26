package com.database.util;

public class NotNullUtil {

    public static <T> T setNotNull(T object, T objectToUpdate) {
        return objectToUpdate == null ? object : objectToUpdate;
    }
}
