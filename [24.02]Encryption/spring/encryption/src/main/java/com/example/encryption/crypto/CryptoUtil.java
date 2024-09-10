package com.example.encryption.crypto;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.encryption.user.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CryptoUtil {
    private final SaltUtil saltUtil;
    private final SecretKeySpec secretKeySpec;
    private final String salt;
    private final HashingCache hashingCache;
    private final EncryptionCache encryptionCache;
    private Base64.Encoder base64Encoder;
    private Base64.Decoder base64Decoder;
    private MessageDigest md;

    /**
     * 생성자 주입
     * @param cryptoKey
     * @param salt
     * @param saltUtil
     */
    public CryptoUtil(@Value("secretKeysecretk") String cryptoKey, @Value("salt") String salt, SaltUtil saltUtil,
        HashingCache hashingCache, EncryptionCache encryptionCache) {
        this.saltUtil = saltUtil;
        this.secretKeySpec = new SecretKeySpec(cryptoKey.getBytes(StandardCharsets.UTF_8),
            Crypto.ENCRYPTION.getAlgorithm());
        this.salt = salt;
        this.hashingCache = hashingCache;
        this.encryptionCache = encryptionCache;
        this.base64Encoder = Base64.getEncoder();
        this.base64Decoder = Base64.getDecoder();
        try {
            this.md = MessageDigest.getInstance(Crypto.HASHING.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 데이터를 AES 알고리즘으로 암호화
     * @param data 암호화할 데이터
     * @return 암호화된 데이터 (Base64 인코딩된 문자열)
     */
    public String encrypt(String data) {
        log.info("encrypt: data = {}", data);

        return Optional.ofNullable(data)
            .map(d -> {
                try {
                    byte[] salt = saltUtil.generateSalt();
                    IvParameterSpec iv = new IvParameterSpec(salt);

                    Cipher cipher = Cipher.getInstance(Crypto.ENCRYPTION.getTransformation());
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

                    byte[] encrypted = cipher.doFinal(d.getBytes(StandardCharsets.UTF_8));
                    byte[] combined = new byte[salt.length + encrypted.length];

                    System.arraycopy(salt, 0, combined, 0, salt.length);
                    System.arraycopy(encrypted, 0, combined, salt.length, encrypted.length);

                    return base64Encoder.encodeToString(combined);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).orElse(null);

    }

    /**
     * 데이터를 AES 알고리즘으로 복호화
     * @param data 복호화할 데이터 (Base64 인코딩된 문자열)
     * @return 복호화된 데이터
     */
    public String decrypt(String data) {
        log.info("decrypt: data = {}", data);

        return Optional.ofNullable(data)
            .map(d -> {
                try {
                    byte[] dataBytes = base64Decoder.decode(d);
                    byte[] salt = new byte[saltUtil.getSALT_LENGTH()];
                    byte[] encrypted = new byte[dataBytes.length - salt.length];

                    System.arraycopy(dataBytes, 0, salt, 0, saltUtil.getSALT_LENGTH());
                    System.arraycopy(dataBytes, saltUtil.getSALT_LENGTH(), encrypted, 0, encrypted.length);

                    IvParameterSpec iv = new IvParameterSpec(salt);
                    Cipher cipher = Cipher.getInstance(Crypto.DECRYPTION.getTransformation());
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

                    return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).orElse(null);
    }

    /**
     * 데이터를 SHA-256 알고리즘으로 해싱.
     * @param data
     * @return
     */

    public String hashing(String data) {
        log.info("Hashing: data = {}", data);

        return Optional.ofNullable(data)
            .map(d -> {
                String dataAddSalt = d + salt;
                byte[] dataBytes = md.digest(dataAddSalt.getBytes(StandardCharsets.UTF_8));

                return base64Encoder.encodeToString(dataBytes);
            }).orElse(null);

    }

    /**
     * 객체의 클래스 확인 후 재귀 또는 cryptoDataFields 메소드 실행 또는 아무것도 안함.
     * @param cryptoData 암호화 또는 복호화할 객체
     * @param crypto 암호화, 복호화 중 하나
     */
    public void validateAndCrypto(Object cryptoData, Crypto crypto) {
        if (cryptoData == null) {
            return;
        }

        if (cryptoData instanceof Optional<?>) {
            Optional<?> optionalResult = (Optional<?>)cryptoData;
            if (optionalResult.isPresent()) {
                validateAndCrypto(optionalResult.get(), crypto);
            }
        } else if (cryptoData instanceof List<?>) {
            List<?> listResult = (List<?>)cryptoData;
            listResult.forEach(v -> {
                validateAndCrypto(v, crypto);
            });
        } else if (cryptoData instanceof Set<?>) {
            Set<?> setResult = (Set<?>)cryptoData;
            setResult.forEach(v -> {
                validateAndCrypto(v, crypto);
            });
        } else if (cryptoData instanceof Map<?, ?>) {
            Map<?, ?> mapResult = (Map<?, ?>)cryptoData;
            mapResult.forEach((k, v) -> {
                validateAndCrypto(v, crypto);
            });
        } else if (isClassCryptoPossible(cryptoData)) {
            cryptoDataFields(cryptoData, crypto);
        }
    }

    /**
     * 객체의 필드를 탐색하여 암호화, 해싱 어노테이션 있는 필드인지 확인 후 해당 기능 실행.
     * @EncryptedField 없는 경우 validateAndCrypto 메소드 실행
     * @param cryptoData 대상 객체
     * @param crypto 해당 타입
     */
    public void cryptoDataFields(Object cryptoData, Crypto crypto) {
        Field[] fields = cryptoData.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(cryptoData);

                if (field.isAnnotationPresent(EncryptedField.class) && field.isAnnotationPresent(HashingField.class)) {
                    throw new RuntimeException("Hashing field annotated with @EncryptedField is not supported");
                }

                if (field.isAnnotationPresent(EncryptedField.class) || field.isAnnotationPresent(HashingField.class)) {
                    if (!(value instanceof String)) {
                        log.info("cryptoDataFields: value is not String");
                        continue;
                    }

                    String text = (String)value;
                    String processedValue = text;

                    if (field.isAnnotationPresent(EncryptedField.class)) {
                        if(crypto == Crypto.ENCRYPTION){
                            processedValue = encrypt(text);
                        }else if(!encryptionCache.getCache(text)){
                            processedValue = decrypt(text);
                            encryptionCache.setCache(processedValue);
                        }
                    }
                    else if (crypto == Crypto.ENCRYPTION && hashingCache.getCache(text).isEmpty()) {
                        processedValue = hashing(text);
                        hashingCache.setCache(processedValue, text);
                    } else if (crypto == Crypto.DECRYPTION && !hashingCache.getCache(text).isEmpty()) {
                        processedValue = hashingCache.getCache(text);
                        hashingCache.evictCache(text);
                    }

                    field.set(cryptoData, processedValue);
                } else {
                    validateAndCrypto(value, crypto);
                }
            } catch (IllegalAccessException e) {
                log.info(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 객체가 원시 타입 혹은 래퍼 타입인지 확인
     * @param object 확인할 객체
     * @return 원시 타입 혹은 래퍼 타입이면 true, 아니면 false
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
     * 객체 타입 확인
     * @param object 확인할 객체
     * @return 암호화 가능한 타입이면 true, 아니면 false
     */
    private boolean isClassCryptoPossible(Object object) {
        return object instanceof UserDTO;
    }
}
