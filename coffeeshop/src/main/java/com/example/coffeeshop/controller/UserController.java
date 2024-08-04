package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.User;
import com.example.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(
    UserService userService
  ) {
    this.userService = userService;
  }

  @GetMapping(
    path = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<User> listAllUsers() {
    return userService.listAllUsers();
  }

  @GetMapping(
    path = "/user/{userId}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Optional<User> findUserById(
    @PathVariable Long userId
  ) {
    return userService.findUserById(userId);
  }
}
