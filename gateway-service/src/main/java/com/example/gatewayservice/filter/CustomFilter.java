package com.example.gatewayservice.filter;

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
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    // 커스텀 필터 설정

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter. Suppose we can extract JWT and perform Authentication

        //filter에서 하고 싶은 내용을 재정의
        //pre filter 동작
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter : request id -> {}", request.getId());

            // Custom Post Filter.Suppose we can call error response handler based on error code.

            //post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(()->{  //스프링5에서 지원하는 기능으로 비동기로 값을 전달할때 사용되는 객체
                log.info("Custom POST filter : response id -> {}", response.getStatusCode());
            }));
        });
    }

    public static class Config {
        // Put the configuration properties
        // configuration 정보 입력
    }

}
