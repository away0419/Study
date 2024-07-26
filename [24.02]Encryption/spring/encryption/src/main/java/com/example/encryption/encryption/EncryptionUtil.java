package com.example.encryption.encryption;

import com.example.encryption.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Component
public class EncryptionUtil {
    private final SaltUtil saltUtil;
    private final SecretKeySpec secretKeySpec;
    private final String ALGORITHM = "AES";
    private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private final String EMPTY = "";

    public EncryptionUtil(@Value("secretKeysecretk") String encryptionKey, SaltUtil saltUtil) {
        this.saltUtil = saltUtil;
        this.secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    /**
     * 암호화
     * @param data
     * @return
     */
    public String encrypt(String data) {
        log.info("encrypt: data = {}", data);

        return Optional.ofNullable(data)
            .map(d -> {
                try {
                    byte[] salt = saltUtil.generateSalt();
                    IvParameterSpec iv = new IvParameterSpec(salt);

                    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

                    byte[] encrypted = cipher.doFinal(d.getBytes(StandardCharsets.UTF_8));
                    byte[] combined = new byte[salt.length + encrypted.length];

                    System.arraycopy(salt, 0, combined, 0, salt.length);
                    System.arraycopy(encrypted, 0, combined, salt.length, encrypted.length);

                    return Base64.getEncoder().encodeToString(combined);
                } catch (BadPaddingException |
                         IllegalBlockSizeException |
                         InvalidAlgorithmParameterException |
                         InvalidKeyException |
                         NoSuchPaddingException |
                         NoSuchAlgorithmException e) {
                    log.info(e.getLocalizedMessage());
                    return EMPTY;
                }
            }).orElseGet(() -> {
                log.info("encrypt: encryptData is NULL");
                return EMPTY;
            });

    }

    /**
     * 복호화
     * @param data
     * @return
     */
    public String decrypt(String data) {
        log.info("decrypt: data = {}", data);

        return Optional.ofNullable(data)
            .map(d -> {
                try {
                    byte[] dataBytes = Base64.getDecoder().decode(data);
                    byte[] salt = new byte[saltUtil.getSALT_LENGTH()];
                    byte[] encrypted = new byte[dataBytes.length - salt.length];

                    System.arraycopy(dataBytes, 0, salt, 0, saltUtil.getSALT_LENGTH());
                    System.arraycopy(dataBytes, saltUtil.getSALT_LENGTH(), encrypted, 0, encrypted.length);

                    IvParameterSpec iv = new IvParameterSpec(salt);
                    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

                    return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
                } catch (InvalidAlgorithmParameterException |
                         NoSuchPaddingException |
                         NoSuchAlgorithmException |
                         InvalidKeyException |
                         IllegalBlockSizeException |
                         BadPaddingException e) {
                    return EMPTY;
                }
            }).orElseGet(() -> {
                log.info("decrypt: decryptData is NULL");
                return EMPTY;
            });
    }

    /**
     * 객체 클래스 검증 후 암복호화 실행
     * @param cryptoData
     * @param isCryptoMode
     */
    public void validateAndCrypto(Object cryptoData, CryptoMode cryptoMode) {
        if (cryptoData == null) {
            return;
        }

        if (cryptoData instanceof Optional<?>) {
            Optional<?> optionalResult = (Optional<?>) cryptoData;
            if (optionalResult.isPresent()) {
                validateAndCrypto(optionalResult.get(), cryptoMode);
            }
        } else if (cryptoData instanceof List<?>) {
            List<?> listResult = (List<?>) cryptoData;
            listResult.forEach(v -> {
                validateAndCrypto(v, cryptoMode);
            });
        } else if (cryptoData instanceof Set<?>) {
            Set<?> setResult = (Set<?>) cryptoData;
            setResult.forEach(v -> {
                validateAndCrypto(v, cryptoMode);
            });
        } else if (cryptoData instanceof Map<?, ?>) {
            Map<?, ?> mapResult = (Map<?, ?>) cryptoData;
            mapResult.forEach((k, v) -> {
                validateAndCrypto(v, cryptoMode);
            });
        } else if (isClassCryptoPossible(cryptoData)) {
            cryptoDataFields(cryptoData, cryptoMode);
        }
    }

    /**
     * 객체 필드들 확인
     * 필드 어노테이션 확인 후 @EncryptedField 있으면 암복호화
     * 없으면 객체 암복호화 검증
     * @param cryptoData
     * @param cryptoMode
     */
    public void cryptoDataFields(Object cryptoData, CryptoMode cryptoMode) {
        Field[] fields = cryptoData.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(cryptoData);
                if (field.isAnnotationPresent(EncryptedField.class)) {

                    if (!(value instanceof String)) {
                        log.info("cryptoDataFields: value is not String");
                        continue;
                    }

                    String text = (String) value;
                    String processedValue = cryptoMode == CryptoMode.ENCRYPTION ? encrypt(text) : decrypt(text);
                    field.set(cryptoData, processedValue);
                } else {
                    validateAndCrypto(value, cryptoMode);
                }
            } catch (IllegalAccessException e) {
                log.info(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 객체가 원시 타입 혹은 래퍼 타입인지 확인
     * @param object
     * @return
     */
    private boolean isPrimitiveOrWrapper(Object object) {
        Class<?> type = object.getClass();

        return
            type.isPrimitive() ||
                type == String.class ||
                type == Boolean.class ||
                type == Byte.class ||
                type == Character.class ||
                type == Double.class ||
                type == Float.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Short.class;
    }

    /**
     * 객체가 암호화 가능한 타입인지 확인
     * @param object
     * @return
     */
    private boolean isClassCryptoPossible(Object object) {
        return object instanceof UserDTO;
    }
}
