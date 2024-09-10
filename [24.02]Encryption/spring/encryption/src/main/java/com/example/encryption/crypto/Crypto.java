package com.example.encryption.crypto;

import lombok.Getter;

@Getter
public enum Crypto {
    ENCRYPTION("암호화", "AES", "AES/CBC/PKCS5Padding"),
    DECRYPTION("복호화", "AES", "AES/CBC/PKCS5Padding"),
    HASHING("해싱", "SHA-256", "NULL");

    private final String type;
    private final String algorithm;
    private final String transformation;

    Crypto(String type, String algorithm, String transformation) {
        this.type = type;
        this.algorithm = algorithm;
        this.transformation = transformation;
    }

}
