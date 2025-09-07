package com.example.BankSystem.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CryptoStringConverter implements AttributeConverter<String, String> {
    private static final byte[] AAD = "KeyVersion:1".getBytes();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return AesGcm.encryptToBase64(attribute, CryptoConfig.DATA_KEY, AAD);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return AesGcm.decryptFromBase64(dbData, CryptoConfig.DATA_KEY, AAD);
    }
}