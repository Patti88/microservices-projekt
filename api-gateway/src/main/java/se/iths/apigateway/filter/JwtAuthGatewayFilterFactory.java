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

            System.out.println("JwtAuthGatewayFilterFactory: Anrop mottaget för " + request.getURI());

            if (isSecured(request)) {
                System.out.println("JwtAuthGatewayFilterFactory: Sökväg är skyddad.");

                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    System.out.println("JwtAuthGatewayFilterFactory: Saknar Authorization header.");
                    return onError(exchange, "Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                System.out.println("JwtAuthGatewayFilterFactory: Authorization header: " + authHeader);

                if (!authHeader.startsWith("Bearer ")) {
                    System.out.println("JwtAuthGatewayFilterFactory: Ogiltigt format på Authorization header.");
                    return onError(exchange, "Invalid authorization header format");
                }

                String token = authHeader.substring(7);
                System.out.println("JwtAuthGatewayFilterFactory: Extraherad token: " + token);

                boolean isTokenValid = jwtValidationService.isTokenValid(token);
                System.out.println("JwtAuthGatewayFilterFactory: Token är " + (isTokenValid ? "giltig" : "ogiltig"));

                if (!isTokenValid) {
                    System.out.println("JwtAuthGatewayFilterFactory: Ogiltig JWT token.");
                    return onError(exchange, "Invalid JWT token");
                }
            } else {
                System.out.println("JwtAuthGatewayFilterFactory: Sökväg är inte skyddad.");
            }

            // Logga alla headers precis innan anropet kedjas vidare
            System.out.println("JwtAuthGatewayFilterFactory: Headers innan kedjan anropas:");
            request.getHeaders().forEach((name, values) -> System.out.println("  " + name + ": " + values));

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean isSecured(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        System.out.println("Securing path: " + path);

        // Tillåt auth-endpoints utan JWT
        if (path.startsWith("/api/auth") || path.startsWith("/auth")) {
            return false;
        }

        return path.startsWith("/api/jokes") ||
                path.startsWith("/jokes") ||
                path.startsWith("/api/quotes") ||
                path.startsWith("/quotes");
    }
    public static class Config {
        // Inga konfigurationsparametrar för tillfället
    }
}