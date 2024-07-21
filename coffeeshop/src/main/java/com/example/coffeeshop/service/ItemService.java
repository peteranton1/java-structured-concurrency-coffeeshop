package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Item;
import com.example.coffeeshop.model.ItemType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {

  private final EntityFactory creator = EntityFactory.INSTANCE;

  private final static List<String> NAMES = Arrays.stream(ItemType.values())
    .map(Enum::name).toList();

  private ItemType getItemType(String name) {
    if (NAMES.contains(name)) {
      return ItemType.valueOf(name);
    }
    return ItemType.UNKNOWN;
  }

  public Item getItem(String name) {
    return creator.createItem(getItemType(name));
  }

  public List<Item> getItems(List<String> names) {
    return names.stream().map(String::toUpperCase).map(this::getItem).toList();
  }

}
