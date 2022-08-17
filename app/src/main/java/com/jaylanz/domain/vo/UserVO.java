package com.jaylanz.domain.vo;

import java.io.Serializable;

public class UserVO implements Serializable {
    private Long id;
    private String username;
    private String emailAddress;

    public UserVO() {
    }

    public UserVO(Long id, String username, String emailAddress) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
