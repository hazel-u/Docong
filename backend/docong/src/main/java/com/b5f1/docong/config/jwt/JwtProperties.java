package com.b5f1.docong.config.jwt;

public interface JwtProperties {
    String SECRET = "docongdocong";
    int EXPIRATION_TIME = 1000*60*30; // 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}