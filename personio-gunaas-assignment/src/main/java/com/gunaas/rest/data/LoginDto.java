package com.gunaas.rest.data;

public class LoginDto {
    public String accessToken;

    public String message;

    public LoginDto(String accessToken, String message) {
        this.accessToken = accessToken;
        this.message = message;
    }

    public LoginDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
