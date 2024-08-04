package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderRaw;
import com.example.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(path = "/order",
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Order> allOrders() {
    return orderService.listOrders();
  }

  @GetMapping(path = "/order/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Order> getOrderById(@PathVariable Long id) {
    return orderService.getOrderById(id);
  }

  @PostMapping(path = "/order",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public Order placeOrder(@RequestBody OrderRaw orderRaw) {
    return orderService.placeOrder(orderRaw.name(), orderRaw.items());
  }
}
