package com.wompi.api.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class IntegritySigner {
    public static String generate(String reference, int amountInCents, String currency, String integrityKey) {
        String chain = reference + amountInCents + currency + integrityKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(chain.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generando firma de integridad", e);
        }
    }
}