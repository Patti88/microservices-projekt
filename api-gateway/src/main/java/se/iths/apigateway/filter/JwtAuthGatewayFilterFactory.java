package se.iths.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import se.iths.apigateway.service.JwtValidationService;

import java.util.Objects;

@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {

    private final JwtValidationService jwtValidationService;

    public JwtAuthGatewayFilterFactory(JwtValidationService jwtValidationService) {
        super(Config.class);
        this.jwtValidationService = jwtValidationService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isSecured(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (!authHeader.startsWith("Bearer ")) {
                    return onError(exchange, "Invalid authorization header format");
                }

                String token = authHeader.substring(7);
                if (!jwtValidationService.isTokenValid(token)) {
                    return onError(exchange, "Invalid JWT token");
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean isSecured(ServerHttpRequest request) {
        // Definiera vilka sökvägar som är skyddade, exkludera /api/auth
        String path = request.getURI().getPath();
        return path.startsWith("/api/jokes") ||
                path.startsWith("/api/quotes");
    }

    public static class Config {
        // Inga konfigurationsparametrar för tillfället
    }
}