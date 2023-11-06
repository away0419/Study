package com.example.kotlin.security.exception

import org.springframework.http.HttpStatus

enum class SecurityCustomErrorCode (
    val httpStatus: HttpStatus,
    val code: String,
    val msg: String
) {
    /* oauth2 */
    OAUTH2_SERVICE_IS_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,"O001","OAuth2 Service is not found"),
    OAUTH2_CONTENT_IS_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,"O002","OAuth2 Content is not found"),
    OAUTH2_CONTENT_TYPE_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR,"O003","OAuth2 Content Type Mismatch"),
    OAUTH2_USER_INFO_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"O004","OAuth2 User Info is null"),
    OAUTH2_USER_INFO_KEY_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"O005","OAuth2 User Info Key is null"),
    OAUTH2_USER_INFO_EMAIL_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"O006","OAuth2 User Info Email is null"),
    OAUTH2_USER_INFO_NAME_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"O007","OAuth2 User Info Name is null"),

    /* security */
    SECURITY_PRINCIPAL_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"S008","Security Principal is null"),

    /* jwt */
    JWT_TOKEN_TYPE_MISMATCH(HttpStatus.UNAUTHORIZED,"J001","JWT Token Type Mismatch"),
    JWT_COOKIE_IS_NOT_FOUND(HttpStatus.UNAUTHORIZED,"J002","JWT Cookie is not found"),
    JWT_AUTH_HEADER_IS_NOT_FOUND(HttpStatus.UNAUTHORIZED,"J003","JWT Auth Header is not found"),
    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"J004","JWT Token Expired"),
    JWT_TOKEN_IS_NULL(HttpStatus.UNAUTHORIZED,"J005","JWT Token is null"),
    JWT_TAMPERED_INVALID(HttpStatus.UNAUTHORIZED,"J006","JWT Token Tampered or Invalid"),
    JWT_TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED,"J007","JWT Token Malformed"),
    JWT_TOKEN_ILLEGAL_ARGUMENT(HttpStatus.UNAUTHORIZED,"J008","JWT Token illegal argument"),
    JWT_TOKEN_ACCESS_DENIED(HttpStatus.FORBIDDEN,"J009","JWT Token access denied"),
}