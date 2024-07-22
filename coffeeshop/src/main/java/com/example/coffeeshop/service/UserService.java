package com.example.coffeeshop.service;

import com.example.coffeeshop.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final static EntityFactory creator = EntityFactory.INSTANCE;

  private final static List<String> NAMES = List.of(
    "Alice", "Bobbie", "Charlie", "Dino", "Eddie");

  private final static Map<String, User> USERS = initUsers();

  private static Map<String, User> initUsers() {
    return NAMES.stream().map(creator::createUser)
      .collect(Collectors.toMap(u -> u.name().toUpperCase(), Function.identity()));
  }

  public Optional<User> lookupUser(String name) {
    if (name != null) {
      User user = USERS.get(name.toUpperCase());
      if (user != null) {
        return Optional.of(user);
      }
    }
    return Optional.empty();
  }

  public List<User> listAllUsers() {
    return USERS.values().stream().toList();
  }

  public Optional<User> findUserById(long userId) {
    return USERS.values().stream().filter(u -> u.id() == userId).findAny();
  }
}
