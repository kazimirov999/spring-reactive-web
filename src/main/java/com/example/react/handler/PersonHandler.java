package com.example.react.handler;

import com.example.react.entity.Person;
import com.example.react.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
public class PersonHandler {

    private static final String ID = "id";

    @Autowired
    private PersonRepository personRepository;

    public Mono<ServerResponse> get(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable(ID));

        return personRepository.get(id)
                               .log("Get Person by id, id: " + id)
                               .flatMap(this::packPerson)
                               .log("Time is: " + LocalDate.now())
                               .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(personRepository.all(), Person.class);
    }

    private Mono<ServerResponse> packPerson(Person person) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(person));
    }
}
