package com.igwines.helper;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpCodeGenerator {

    private static final String CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SMALL_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String VALUES = CAPITAL_CHARS + SMALL_CHARS + NUMBERS;

    public String generateCode(Integer codeLength) {
        Random random = new Random();
        char[] code = new char[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = VALUES.charAt(random.nextInt(VALUES.length()));
        }
        return new String(code);
    }
}
