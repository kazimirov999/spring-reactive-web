package com.example.react.repository;

import com.example.react.entity.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.LongStream;

import static com.example.react.entity.Person.DEFAULT_NAME;

@Repository
public class InnerPersonRepository implements PersonRepository {

    private Map<Long, Person> personHolder = new HashMap<>();

    @Override
    public Mono<Void> save(Mono<Person> personMono) {
        return personMono.doOnNext((person -> personHolder.put(nextId(), person)))
                         .thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Person> get(long id) {
        return Mono.justOrEmpty(personHolder.get(id));
    }

    @Override
    public Flux<Person> all() {
        return Flux.fromIterable(personHolder.values());
    }

    @Override
    public void generatePersons(long amountOfPerson) {
        Random random = new Random();
        LongStream.range(nextId(), amountOfPerson)
                  .mapToObj(id -> new Person(id, DEFAULT_NAME + id, random.nextInt(20) + 10))
                  .forEach(person -> personHolder.put(person.getId(), person));
    }

    private Long nextId() {
        return (long) (personHolder.size() + 1);
    }

}
