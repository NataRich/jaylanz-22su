package com.jaylanz.service.auth;

public interface AuthenticationService {
    String login(String username, String password, String ipAddress);
    void logout(String ipAddress);
}
