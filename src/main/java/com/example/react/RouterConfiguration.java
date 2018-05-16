package com.example.react;

import com.example.react.handler.PersonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Slf4j
@Configuration
public class RouterConfiguration {

    private static long requestCount;

    private static final int LOG_REQUEST_FACTOR = 100;

    private static final RequestPredicate GET_ALL_PERSONS = GET("/persons");
    private static final RequestPredicate GET_PERSON_BY_ID = GET("/person/{id}");

    //@formatter:off
    @Bean
    public RouterFunction<ServerResponse> personRouting(PersonHandler personHandler) {

        return RouterFunctions
                .route(GET_ALL_PERSONS, personHandler::list)
                    .filter(RouterConfiguration::countRequests)
                .andRoute(GET_PERSON_BY_ID, personHandler::get)
                    .filter(RouterConfiguration::logRequestCount);
    }
    //@formatter:on

    private static Mono<ServerResponse> countRequests(ServerRequest request, HandlerFunction<ServerResponse> next) {
        requestCount++;
        return next.handle(request);
    }

    private static Mono<ServerResponse> logRequestCount(ServerRequest request, HandlerFunction<ServerResponse> next) {
        if (requestCount % LOG_REQUEST_FACTOR == 0) {
            log.info("Request amount is: " + requestCount);
        }
        return next.handle(request);
    }

}
