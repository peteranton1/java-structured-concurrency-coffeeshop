package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Example;
import com.example.coffeeshop.model.Item;
import com.example.coffeeshop.model.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemServiceTest {
  private ItemService underTest;

  private final Item coffee = new Item(1L, ItemType.COFFEE);
  private final Item tea = new Item(2L, ItemType.TEA);
  private final Item cake = new Item(3L, ItemType.CAKE);
  private final Item unknown = new Item(4L, ItemType.UNKNOWN);

  @BeforeEach
  void setUp() {
    underTest = new ItemService();
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
      var actual = underTest.getItems(example.input());
      Assertions.assertEquals(example.expected(), actual);
    });
  }

}