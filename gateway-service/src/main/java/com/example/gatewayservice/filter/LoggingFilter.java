package com.example.gatewayservice.filter;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    // 필터 실행 순서
    // 1. GlobalFilter
    // 2. CustomFilter
    // 3. LoggingFilter

    // Gateway Client -> Gateway Handler -> Global Filter -> Custom Filter -> Logging Filter -> Proxied Service
    // Gateway Client <- Gateway Handler <- Global Filter <- Custom Filter <- Logging Filter <- Proxied Service

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        /*return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter baseMessage : " + config.getMessage());
            if (config.isPreLogger())
                log.info("Logging PRE filter : request uri -> {}", request.getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger())
                    log.info("Logging POST filter : response code -> {}", response.getStatusCode());
            }));
        };*/

        // ServerWebExchange : WebFlux를 활용해 request와 response 가져올 수 있다
        // GatewayFilterChain : 다양한 필터들을 연결해서 작업을 할 수 있게함
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter message : {}", config.getMessage());

            if(config.isPreLogger()){
                log.info("Logging filter Start : request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger())
                    log.info("Logging POST filter : response code -> {}", response.getStatusCode());
            }));
        }, Ordered.LOWEST_PRECEDENCE); // Ordered.HIGHEST_PRECEDENCE : 우선 순위 높게
        return filter;
    }

    @Data
    public static class Config {
        private String message;
        private boolean preLogger;
        private boolean postLogger;
    }
}
