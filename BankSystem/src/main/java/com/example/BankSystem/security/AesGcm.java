package com.example.BankSystem.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.ByteBuffer;

public final class AesGcm {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LEN = 12;      // 96-bit recommended
    private static final int TAG_BITS = 128;   // 16 bytes

    private static final SecureRandom RNG = new SecureRandom();

    public static String encryptToBase64(String plaintext, SecretKey key, byte[] aad) {
        if (plaintext == null) return null;
        try {
            byte[] iv = new byte[IV_LEN];
            RNG.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            if (aad != null) cipher.updateAAD(aad);
            byte[] ct = cipher.doFinal(plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // pack iv || ct
            ByteBuffer bb = ByteBuffer.allocate(iv.length + ct.length);
            bb.put(iv).put(ct);
            return Base64.getEncoder().encodeToString(bb.array());
        } catch (Exception e) {
            throw new IllegalStateException("Encrypt failed", e);
        }
    }

    public static String decryptFromBase64(String b64, SecretKey key, byte[] aad) {
        if (b64 == null) return null;
        try {
            byte[] all = Base64.getDecoder().decode(b64);
            byte[] iv = java.util.Arrays.copyOfRange(all, 0, IV_LEN);
            byte[] ct = java.util.Arrays.copyOfRange(all, IV_LEN, all.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_BITS, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            if (aad != null) cipher.updateAAD(aad);
            byte[] pt = cipher.doFinal(ct);
            return new String(pt, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Decrypt failed (wrong key/format?)", e);
        }
    }
}