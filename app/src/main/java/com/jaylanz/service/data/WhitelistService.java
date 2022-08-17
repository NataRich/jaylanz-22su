package com.jaylanz.service.data;

public interface WhitelistService {
    boolean exists(String ipAddress);
    void loadAll();  // called once after startup
}
