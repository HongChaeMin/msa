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

        // ex1)
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

        // ex2)
        // 이걸로 설정하면 글로벌 필터가 더 늦게 작동함
        /*return new OrderedGatewayFilter((exchange, chain)->{    // WebFlux를 활용하여 비동기 처리에서 request와 response를 가져올 수 있다.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter : request id -> {}", request.getId());

            // post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(()->{  // 스프링5에서 지원하는 기능으로 비동기로 값을 전달할때 사용되는 객체
                log.info("Custom POST filter : response id -> {}", response.getStatusCode());
            }));
        }, Ordered.HIGHEST_PRECEDENCE); //필터의 우선순위 */
    }

    public static class Config {
        // Put the configuration properties
        // configuration 정보 입력
    }

}
