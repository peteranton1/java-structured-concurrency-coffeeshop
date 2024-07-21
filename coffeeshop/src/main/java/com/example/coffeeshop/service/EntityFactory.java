package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Item;
import com.example.coffeeshop.model.ItemType;
import com.example.coffeeshop.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public enum EntityFactory {
  INSTANCE;

  private final AtomicLong userIds = new AtomicLong();
  private final ConcurrentMap<String, User> usersMap = new ConcurrentHashMap<>();
  private final ConcurrentMap<Long, Item> itemsMap = new ConcurrentHashMap<>();

  public User createUser(String name) {
    User user = usersMap.get(name);
    if (user == null) {
      user = new User(userIds.incrementAndGet(), name);
      usersMap.put(user.name(), user);
    }
    return user;
  }

  public Item createItem(ItemType itemType) {
    long ordinal = itemType.ordinal() + 1;
    Item item = itemsMap.get(ordinal);
    if (item == null) {
      item = new Item(ordinal, itemType);
      itemsMap.put(item.id(), item);
    }
    return item;
  }
}
