package com.jaylanz.service.auth.impl;

import com.jaylanz.common.exception.AuthenticationFailureException;
import com.jaylanz.common.exception.TokenCreationFailureException;
import com.jaylanz.domain.dto.TokenDTO;
import com.jaylanz.domain.dto.UserDTO;
import com.jaylanz.service.auth.AuthenticationService;
import com.jaylanz.service.data.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(String username, String password, String ipAddress) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new AuthenticationFailureException();
        }

        // Create a new login token
        UserDTO user = (UserDTO) authentication.getPrincipal();
        TokenDTO token = tokenService.create(user.getId(), user.getUsername(), ipAddress);
        if (token == null)
            throw new TokenCreationFailureException();
        return token.getToken();
    }

    @Override
    public void logout(String ipAddress) {
        UserDTO user = null;
        try {
            user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AccessDeniedException("null context or principal in logout");
        }

        TokenDTO dto = tokenService.get(user.getId(), ipAddress);
        if (dto != null)
            tokenService.delete(dto.getId());
    }
}
