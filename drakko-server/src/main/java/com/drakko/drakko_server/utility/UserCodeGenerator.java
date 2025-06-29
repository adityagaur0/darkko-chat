package com.drakko.drakko_server.utility;

import org.springframework.security.crypto.bcrypt.BCrypt;
import java.time.LocalDate;
import java.util.Base64;

public class UserCodeGenerator {

    public static String generateCode(String firstName, String lastName, LocalDate dob, String rawPassword) {
        String base = firstName + lastName + dob.toString() + rawPassword;
        String hash = Base64.getEncoder().encodeToString(base.getBytes());
        String shortHash = hash.replaceAll("[^a-zA-Z0-9]", "").substring(0, 6);
        return firstName + lastName.charAt(0) + "#" + shortHash;
    }

    public static String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }
}
