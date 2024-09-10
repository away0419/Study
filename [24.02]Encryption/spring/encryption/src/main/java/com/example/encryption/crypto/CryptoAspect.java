package com.example.encryption.crypto;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * DB 접근 시, 해당 객체의 필드에 @EncryptedField 어노테이션 확인 후 암복화 실행.
 */
@Slf4j
@Aspect
@Component
public class CryptoAspect {

    private final CryptoUtil encryptionUtil;

    public CryptoAspect(CryptoUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    @Pointcut("execution(* com.example.encryption..*Mapper.insert*(..))")
    private void insert() {
    }

    @Pointcut("execution(* com.example.encryption..*Mapper.update*(..))")
    private void update() {
    }

    @Pointcut("execution(* com.example.encryption..*Mapper.select*(..))"
        + "|| execution(* com.example.encryption..*Mapper.get*(..))"
        + "|| execution(* com.example.encryption..*Mapper.findBy*(..))")
    private void select() {
    }

    /**
     * SQL 호출 전 매개변수를 암호화 할 경우, SQL 호출 이후 해당 매개변수는 암호화가 되어 있으므로 이를 재사용하기 힘듬. 따라서 사용 후 복호화 해야함.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("insert() || update() || select()")
    public Object encryptEntity(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            encryptionUtil.validateAndCrypto(arg, Crypto.ENCRYPTION);
        }

        Object result = joinPoint.proceed();

        for (Object arg : args) {
            encryptionUtil.validateAndCrypto(arg, Crypto.DECRYPTION);
        }

        encryptionUtil.validateAndCrypto(result, Crypto.DECRYPTION);

        return result;
    }

    /**
     * select, get, findBy 등등 메소드 실행 완료 후 결과 값이 있다면 받아와 AOP 실행.
     * @param result
     */
    // @AfterReturning(pointcut = "select()", returning = "result")
    // public void decryptEntity(JoinPoint joinPoint, Object result) {
    //     log.info("sadfs {}", joinPoint);
    //     log.info("sadfs {}", AopProxyUtils.ultimateTargetClass(joinPoint));
    //     log.info("sadfs {}", joinPoint.getTarget());
    //     log.info("sadfs {}", joinPoint.getThis());
    //     encryptionCacheUtil.ifAbsent(AopProxyUtils.getSingletonTarget(result), result, t -> {
    //         encryptionUtil.validateAndCrypto(t, Crypto.DECRYPTION);
    //     });
    // }

}
