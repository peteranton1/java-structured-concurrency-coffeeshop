package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Item;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

  private final AtomicLong orderIds = new AtomicLong();
  private final UserService userService;
  private final ItemService itemService;
  private final Map<Long, Order> ordersMap;

  @Autowired
  public OrderService(UserService userService, ItemService itemService) {
    this.userService = userService;
    this.itemService = itemService;
    this.ordersMap = new HashMap<>();
  }

  public User getUser(String name) {
    return userService.lookupUser(name).orElseThrow();
  }

  public List<Item> getItems(List<String> names) {
    return itemService.getItems(names);
  }

  public Order placeOrder(String name, String order) {
    User user = getUser(name);
    List<String> orderItemNames = Arrays.asList(order.split(","));
    List<Item> items = getItems(orderItemNames);

    return createOrder(user, items);
  }

  public List<Order> listOrders() {
    return (List<Order>) ordersMap.values();
  }

  private Order createOrder(User user, List<Item> items) {
    Order order = new Order(orderIds.incrementAndGet(), user, items);
    ordersMap.put(order.id(), order);
    return order;
  }

}
