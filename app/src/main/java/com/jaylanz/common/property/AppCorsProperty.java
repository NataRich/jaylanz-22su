package com.jaylanz.common.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppCorsProperty {
    @Value("${app.cors.origins}")
    private List<String> origins;

    @Value("${app.cors.headers}")
    private List<String> headers;

    @Value("${app.cors.methods}")
    private List<String> methods;

    public List<String> getOrigins() {
        return origins;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getMethods() {
        return methods;
    }
}
