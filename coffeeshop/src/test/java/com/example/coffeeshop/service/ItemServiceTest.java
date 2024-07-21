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

  @BeforeEach
  void setUp() {
    underTest = new ItemService();
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

}