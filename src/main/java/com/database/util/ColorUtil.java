package com.database.util;

import java.util.Random;

public class ColorUtil {

    public static String generateColor() {
        Random random = new Random();
        int nextInt = random.nextInt(256 * 256 * 256);
        return String.format("#%06x", nextInt);
    }
}
