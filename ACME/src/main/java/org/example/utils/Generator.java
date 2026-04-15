package org.example.utils;

import org.example.Database.DBinterface;

import java.security.SecureRandom;
import java.util.Random;



public class Generator {

private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateTemporaryPassword() {
        StringBuilder id = new StringBuilder(4);
        for (int i = 0; i < 5; i++) {
            id.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return id.toString();
    }
}
