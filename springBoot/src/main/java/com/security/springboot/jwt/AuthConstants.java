package com.security.springboot.jwt;

public interface AuthConstants {
    String AUTH_HEADER = "Authorization";
    String TOKEN_TYPE = "Bearer "; // 띄어 쓰기가 있어야 한다.
    String COOKIE_HEADER = "Set-Cookie";
    String REFRESH_TOKEN_PREFIX = "refresh_token";
}
