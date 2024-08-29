package com.example.encryption.encryption;

import java.lang.reflect.Proxy;

import org.springframework.aop.framework.AopProxyUtils;

public class AopUtils {
    public static boolean isProxy(Object object) {
        if (object == null) {
            return false;
        }
        if (Proxy.isProxyClass(object.getClass())) {
            return true;
        }
        return AopProxyUtils.ultimateTargetClass(object) != null;
    }
}
