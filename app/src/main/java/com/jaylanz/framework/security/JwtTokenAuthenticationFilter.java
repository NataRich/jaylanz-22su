package com.jaylanz.framework.security;

import com.jaylanz.common.util.IpUtil;
import com.jaylanz.common.util.JwtUtil;
import com.jaylanz.domain.dto.UserDTO;
import com.jaylanz.service.data.TokenService;
import com.jaylanz.service.data.UserService;
import com.jaylanz.service.data.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final UserService userService;
    private final WhitelistService whitelistService;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtUtil jwtUtil, TokenService tokenService, UserService userService,
                                        WhitelistService whitelistService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.userService = userService;
        this.whitelistService = whitelistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = "Bearer";

        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith(tokenHeader)) {
            String jwtToken = authorization.substring(tokenHeader.length()).trim();
            if (jwtUtil.isValid(jwtToken) && !tokenService.isExpired(jwtToken)
                    && whitelistService.exists(IpUtil.getIp(httpServletRequest))) {
                Long userId = jwtUtil.getUserId(jwtToken);
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDTO user = userService.get(userId);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
