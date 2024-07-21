package com.example.coffeeshop.model;

import java.util.List;

public record Order(Long id, User user, List<Item> items) {
}
