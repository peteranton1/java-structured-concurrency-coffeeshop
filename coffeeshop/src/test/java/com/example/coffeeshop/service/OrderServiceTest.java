package com.example.coffeeshop.service;

import com.example.coffeeshop.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderServiceTest {

  private OrderService underTest;

  @BeforeEach
  void setUp() {
    UserService userService = new UserService();
    ItemService itemService = new ItemService();
    this.underTest = new OrderService(userService, itemService);
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
      User actual = underTest.lookupUser(example.input());
      Assertions.assertEquals(example.expected(), actual);
    });
  }

  @Test
  void getItemsWhenValid() {

    List<Example<List<String>>> examples = List.of(
      new Example<>(List.of("COFFEE"), List.of(new Item(ItemType.COFFEE))),
      new Example<>(List.of("Coffee"), List.of(new Item(ItemType.COFFEE))),
      new Example<>(List.of("TEA"), List.of(new Item(ItemType.TEA))),
      new Example<>(List.of("CAKE"), List.of(new Item(ItemType.CAKE))),
      new Example<>(List.of("COFFEE", "coffEE", "CaKE"),
        List.of(new Item(ItemType.COFFEE), new Item(ItemType.COFFEE), new Item(ItemType.CAKE))),
      new Example<>(List.of("", "BANANA"), List.of(
        new Item(ItemType.UNKNOWN), new Item(ItemType.UNKNOWN)))
    );

    examples.forEach(example -> {
      var result = underTest.getItems(example.input());
      Assertions.assertEquals(example.expected(), result);
    });
  }

  @Test
  void placeOrderWhenValid() {

    User alice = new User("Alice");
    Item coffee = new Item(ItemType.COFFEE);
    Item unknown = new Item(ItemType.UNKNOWN);
    List<Example<List<String>>> examples = List.of(
      new Example<>(List.of("Alice", "COFFEE"),
        new Order(1L, alice, List.of(coffee))),
      new Example<>(List.of("alice", "Coffee"),
        new Order(2L, alice, List.of(coffee))),
      new Example<>(List.of("Alice", "COFFEE,coffEE,CaKE"),
        new Order(3L, alice, List.of(coffee, coffee, new Item(ItemType.CAKE)))),
      new Example<>(List.of("aLICe", ",BANANA"),
        new Order(4L, alice, List.of(unknown, unknown)))
      );

    examples.forEach(example -> {
      List<String> input = example.input();
      var actual = underTest.placeOrder(input.get(0), input.get(1));
      Assertions.assertEquals(example.expected(), actual);
    });
  }

  @Test
  void listOrders() {

    User alice = new User("Alice");
    Item coffee = new Item(ItemType.COFFEE);

    Item cake = new Item(ItemType.CAKE);
    List<Example<List<String>>> examples = List.of(
      new Example<>(List.of("Alice", "COFFEE"),
        new Order(1L, alice, List.of(coffee))),
      new Example<>(List.of("Alice", "COFFEE,coffEE,CaKE"),
        new Order(2L, alice, List.of(coffee, coffee, cake)))
    );

    examples.forEach(example -> {
      List<String> input = example.input();
      var actual = underTest.placeOrder(input.get(0), input.get(1));
      Assertions.assertEquals(example.expected(), actual);
    });

    // main teat
    var expected = List.of(
      new Order(1L, alice, List.of(coffee)),
      new Order(2L, alice, List.of(coffee, coffee, cake))
    );
    var actual = underTest.listOrders();
    Assertions.assertEquals(expected, actual);
  }
}