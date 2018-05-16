package com.example.react;

import com.example.react.handler.PersonHandler;
import com.example.react.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.springframework.boot.WebApplicationType.REACTIVE;

@Import(RouterConfiguration.class)
@Configuration
@Slf4j
@ComponentScan
@EnableWebFlux
@SpringBootApplication
public class Application {

    private static final int DEFAULT_PERSONS_AMOUNT = 100;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(REACTIVE);

        app.run(args);
    }

    @Bean
    PersonHandler getPersonHandler() {
        return new PersonHandler();
    }

    @Bean
    CommandLineRunner init(PersonRepository personRepository) {
        return args -> personRepository.generatePersons(allNotNull(args, args[0]) ? Long.parseLong(args[0]) : DEFAULT_PERSONS_AMOUNT);
    }
}
