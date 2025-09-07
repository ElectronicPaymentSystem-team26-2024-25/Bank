package com.example.BankSystem.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CryptoIntegerConverter implements AttributeConverter<Integer, String> {
    private static final byte[] AAD = "KeyVersion:1".getBytes();

    @Override
    public String convertToDatabaseColumn(Integer attribute) {
        if (attribute == null) return null;
        return AesGcm.encryptToBase64(String.valueOf(attribute), CryptoConfig.DATA_KEY, AAD);
    }

    @Override
    public Integer convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String plain = AesGcm.decryptFromBase64(dbData, CryptoConfig.DATA_KEY, AAD);
        return Integer.valueOf(plain);
    }
}