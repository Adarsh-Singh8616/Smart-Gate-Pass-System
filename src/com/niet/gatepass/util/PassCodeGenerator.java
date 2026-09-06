package com.niet.gatepass.util;

import java.util.UUID;

/** Generates a unique, gate-pass verification code used for QR / lookup. */
public class PassCodeGenerator {

    private PassCodeGenerator() {}

    public static String generate() {
        // e.g. GP-3F2A9C11
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        return "GP-" + uuidPart;
    }
}
