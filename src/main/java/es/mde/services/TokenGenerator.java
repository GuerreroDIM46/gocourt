package es.mde.services;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    
    public static String generarToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
