package com.vocation.travel.dto;

public class AuthDTO {
    public record LoginRequest(String username, String password){};

    public record Response(String message, String token, String rf, String uid) {}
}
