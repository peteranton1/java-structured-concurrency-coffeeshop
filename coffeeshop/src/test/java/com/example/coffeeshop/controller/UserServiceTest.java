package com.example.coffeeshop.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class UserServiceTest {

  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService();
  }

  @Test
  void lookupUserWhenValidName() {

    List<Example> examples = Stream.of(
        "Alice", "Bobbie", "Charlie", "Dino", "Eddie")
      .map(name -> new Example(name, name)).toList();

    examples.forEach(example -> {
      var result = underTest.lookupUser(example.name).orElseThrow();
      Assertions.assertEquals(example.expected, result.name());
    });
  }

  @Test
  void lookupUserWhenInvalidName() {

    List<Example> examples = List.of(
      new Example("Mark", "X"),
      new Example("", "X"),
      new Example(null, "X")
    );

    examples.forEach(example -> {
      Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
        underTest.lookupUser(example.name).orElseThrow(() -> new RuntimeException("X"));
      });
      Assertions.assertEquals(example.expected, exception.getMessage());
    });
  }

  public record Example(String name, String expected) {
  }
}