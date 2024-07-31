package com.example.methodsecurity.service;

import org.springframework.security.authorization.AuthorizationProxyFactory;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;

public class BankAccountServiceImpl implements BankAccountService {

  @Override
  public BankAccount findById(int id) {
    BankAccount result = getAlice(id);
    AuthorizationProxyFactory proxyFactory = AuthorizationAdvisorProxyFactory.withDefaults();
    return (BankAccount) proxyFactory.proxy(result);
  }

  @Override
  public BankAccount getById(int id) {
    return findById(id);
  }

  private BankAccount getAlice(int id) {
    return new BankAccount(id,"Alice","12345678",1234.56);
  }
}
