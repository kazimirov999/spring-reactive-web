package com.example.react.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Person implements Serializable {

    public static final String DEFAULT_NAME = "Person";

    private final Long id;
    private final String name;
    private final int age;
}
