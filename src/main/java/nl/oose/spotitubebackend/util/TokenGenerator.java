package nl.oose.spotitubebackend.util;

import java.util.UUID;

public class TokenGenerator {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
