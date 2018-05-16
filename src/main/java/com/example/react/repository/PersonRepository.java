package com.example.react.repository;

import com.example.react.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Void> save(Mono<Person> person);

    Mono<Person> get(long id);

    Flux<Person> all();

    void generatePersons(long amountOfPerson);
}
