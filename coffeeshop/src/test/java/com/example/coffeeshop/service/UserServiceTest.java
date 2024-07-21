package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Example;
import com.example.coffeeshop.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserServiceTest {

  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService();
  }

  @Test
  void lookupUserWhenValidName() {

    List<Example<String>> examples = List.of(
        new Example<>("Alice", new User("Alice")),
        new Example<>("Alice", new User("Alice")),
        new Example<>("alice", new User("Alice")),
        new Example<>("BoBBie", new User("Bobbie")),
        new Example<>("Charlie", new User("Charlie")),
        new Example<>("Dino", new User("Dino")),
        new Example<>("Eddie", new User("Eddie")));

    examples.forEach(example -> {
      User actual = underTest.lookupUser(example.input()).orElseThrow();
      Assertions.assertEquals(example.expected(), actual);
    });
  }

  @Test
  void lookupUserWhenInvalidName() {

    String message = "X";
    List<Example<String>> examples = List.of(
      new Example<>("Mark", message),
      new Example<>("", message),
      new Example<>(null, message)
    );

    examples.forEach(example -> {
      Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> underTest.lookupUser(example.input()).orElseThrow(() -> new RuntimeException(message)));
      Assertions.assertEquals(example.expected(), exception.getMessage());
    });
  }


}