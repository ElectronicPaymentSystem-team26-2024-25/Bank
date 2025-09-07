package com.example.BankSystem.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class CryptoConfig {
    static SecretKey DATA_KEY;

    @Value("${custom.property.key.base64}")
    private String base64Key;

    @PostConstruct
    void init() {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        if (keyBytes.length != 16 && keyBytes.length != 32) {
            throw new IllegalStateException("app.crypto.key.base64 must be 128 or 256-bit");
        }
        DATA_KEY = new SecretKeySpec(keyBytes, "AES");
    }
}