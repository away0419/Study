package com.example.encryption.encryption;

import lombok.Getter;

@Getter
public enum CryptoMode {
    ENCRYPTION("암호화"),
    DECRYPTION("복호화");

    private final String mdoe;

    private CryptoMode(String mode) {
        this.mdoe = mode;
    }

}
