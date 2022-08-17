package com.jaylanz.web.controller;

import com.jaylanz.common.util.IpUtil;
import com.jaylanz.common.validation.constraint.Username;
import com.jaylanz.domain.vo.BaseResponse;
import com.jaylanz.service.auth.AuthenticationService;
import com.jaylanz.service.data.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final WhitelistService whitelistService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(WhitelistService whitelistService, AuthenticationService authenticationService) {
        this.whitelistService = whitelistService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginToken>> login(@Valid @RequestBody LoginForm form, HttpServletRequest request) {
        try {
            String ipAddress = IpUtil.getIp(request);
            if (whitelistService.exists(ipAddress)) {
                String token = authenticationService.login(form.username, form.password, ipAddress);
                return new ResponseEntity<>(BaseResponse.OK(new LoginToken(token)), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        try {
            authenticationService.logout(IpUtil.getIp(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private static class LoginForm {
        @Username
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class LoginToken {
        private String token;

        public LoginToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
