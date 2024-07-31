package com.example.methodsecurity.service;

public interface BankAccountService {

  @PostReadBankAccount
  BankAccount findById(int id);

  @PostReadBankAccount
  BankAccount getById(int id);
}
