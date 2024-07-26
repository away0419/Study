package com.example.encryption.encryption;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SaltUtil {
    private final SecureRandom secureRandom;
    private final int SALT_LENGTH = 16;

    public SaltUtil() {
        secureRandom = new SecureRandom();
    }

    /**
     * 암호화에 사용되는 무작위 바이트 배열 생성
     *
     * @return
     */
    public byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public int getSALT_LENGTH() {
        return SALT_LENGTH;
    }
}
