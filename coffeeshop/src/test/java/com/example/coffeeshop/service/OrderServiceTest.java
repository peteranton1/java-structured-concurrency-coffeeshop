package com.example.coffeeshop.service;

import com.example.coffeeshop.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class OrderServiceTest {

  private final User alice = new User(1L, "Alice");
  private final Item coffee = new Item(1L, ItemType.COFFEE);
  private final Item tea = new Item(2L, ItemType.TEA);
  private final Item cake = new Item(3L, ItemType.CAKE);
  private final Item unknown = new Item(4L, ItemType.UNKNOWN);
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
      new Example<>("Alice", alice),
      new Example<>("Alice", alice),
      new Example<>("alice", alice),
      new Example<>("BoBBie", new User(2L, "Bobbie")),
      new Example<>("Charlie", new User(3L, "Charlie")),
      new Example<>("Dino", new User(4L, "Dino")),
      new Example<>("Eddie", new User(5L, "Eddie")));

    examples.forEach(example -> {
      User actual = underTest.lookupUser(example.input());
      Assertions.assertEquals(example.expected(), actual);
    });
  }

  @Test
  void getItemsWhenValid() {

    List<Example<List<String>>> examples = List.of(
      new Example<>(List.of("COFFEE"), List.of(coffee)),
      new Example<>(List.of("Coffee"), List.of(coffee)),
      new Example<>(List.of("TEA"), List.of(tea)),
      new Example<>(List.of("CAKE"), List.of(cake)),
      new Example<>(List.of("COFFEE", "coffEE", "CaKE"),
        List.of(coffee, coffee, cake)),
      new Example<>(List.of("", "BANANA"), List.of(
        unknown, unknown))
    );

    examples.forEach(example -> {
      var result = underTest.getItems(example.input());
      Assertions.assertEquals(example.expected(), result);
    });
  }

  @Test
  void placeOrderWhenValid() {

    List<Example<List<String>>> examples = List.of(
      new Example<>(List.of("Alice", "COFFEE"),
        new Order(1L, alice, List.of(coffee))),
      new Example<>(List.of("alice", "Coffee"),
        new Order(2L, alice, List.of(coffee))),
      new Example<>(List.of("Alice", "COFFEE,coffEE,CaKE"),
        new Order(3L, alice, List.of(coffee, coffee, cake))),
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

  @Test
  void getOrderByIdWhenFound() {
    var order = underTest.placeOrder("Alice", "Coffee");
    var expected = Optional.of(order);
    var actual = underTest.getOrderById(order.id());
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void getOrderByIdWhenNotFound() {
    var expected = Optional.empty();
    var actual = underTest.getOrderById(-1L);
    Assertions.assertEquals(expected, actual);
  }
}