package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Example;
import com.example.coffeeshop.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserServiceTest {

  private UserService underTest;

  private final User alice = new User(1L, "Alice");
  private final User bobbie = new User(2L, "Bobbie");
  private final User charlie = new User(3L, "Charlie");
  private final User dino = new User(4L, "Dino");
  private final User eddie = new User(5L, "Eddie");

  @BeforeEach
  void setUp() {
    underTest = new UserService();
  }

  @Test
  void lookupUserWhenValidName() {

    List<Example<String>> examples = List.of(
        new Example<>("Alice", alice),
        new Example<>("Alice", alice),
        new Example<>("alice", alice),
        new Example<>("BoBBie", bobbie),
        new Example<>("Charlie", charlie),
        new Example<>("Dino", dino),
        new Example<>("Eddie", eddie));

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

  @Test
  void lookupUserByIdWhenValidId() {

    List<User> expected = List.of(
      alice,
      bobbie,
      charlie,
      dino,
      eddie);

    List<User> actual = underTest.listAllUsers();
    Assertions.assertTrue(
      actual.size() == expected.size()
        && actual.containsAll(expected)
        && expected.containsAll(actual));
  }

  @Test
  void findUserByIdWhenValidId() {

    List<Example<Long>> examples = List.of(
      new Example<>(alice.id(), alice),
      new Example<>(bobbie.id(), bobbie),
      new Example<>(charlie.id(), charlie),
      new Example<>(dino.id(), dino),
      new Example<>(eddie.id(), eddie));

    examples.forEach(example -> {
      User actual = underTest.findUserById(example.input()).orElseThrow();
      Assertions.assertEquals(example.expected(), actual);
    });
  }


}