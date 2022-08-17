package com.jaylanz.framework.security;

import com.jaylanz.common.property.AppCorsProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AppCorsProperty corsProperty;
    private final UserDetailsService userDetailsService;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(AppCorsProperty corsProperty, UserDetailsService userDetailsService,
                                 RestfulAccessDeniedHandler restfulAccessDeniedHandler,
                                 RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint,
                                 JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.corsProperty = corsProperty;
        this.userDetailsService = userDetailsService;
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restfulAuthenticationEntryPoint = restfulAuthenticationEntryPoint;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(corsProperty.getOrigins());
        cors.setAllowedHeaders(corsProperty.getHeaders());
        cors.setAllowedMethods(corsProperty.getMethods());
        cors.setAllowCredentials(false);

        http.cors().configurationSource(request -> cors)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/v1/auth/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/v1/resource/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .headers().cacheControl();

        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
            .and()
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
