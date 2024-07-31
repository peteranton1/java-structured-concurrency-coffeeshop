package com.example.methodsecurity.service;

import lombok.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;

public class BankAccount {
  @Getter
  int id;
  @Getter
  String owner;
  String accountNumber;
  @Getter
  Double balance;

  public BankAccount(int id, String owner, String accountNumber, Double balance) {
    this.id = id;
    this.owner = owner;
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  @PreAuthorize("this.owner == authentication?.name")
  @HandleAuthorizationDenied(handlerClass = MaskMethodAuthorizationDeniedHandler.class)
  public String getAccountNumber() {
    return accountNumber;
  }

}
