package com.jkorgol.javapostssimpleapp;


public class Session {
    private String token;
    private String userLogin;

    public Session(String token, String userLogin) {
        this.token = token;
        this.userLogin = userLogin;
    }

    public String getToken() {
        return token;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
