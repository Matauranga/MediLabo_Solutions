package com.mediLaboSolutions.gatewaymanagement.filters;

import com.mediLaboSolutions.gatewaymanagement.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    //TODO : Chercher pour afficher dans les filtre le nom des requetes
    private final RouteValidator validator;

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("authorization header not valid");
                }

                authHeader = authHeader.substring(7);

                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}