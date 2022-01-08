package org.eco.mubisoft.generator.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
