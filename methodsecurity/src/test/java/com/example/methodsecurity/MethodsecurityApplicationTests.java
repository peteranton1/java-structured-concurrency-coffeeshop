package com.example.methodsecurity;

import com.example.methodsecurity.service.BankAccount;
import com.example.methodsecurity.service.BankAccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class MethodsecurityApplicationTests {

  @Autowired
  BankAccountService bank;

  @WithMockUser("Alice")
  @Test
  void findById() {
    Assertions.assertThat(bank.findById(1)).isNotNull();
  }

  @WithMockUser("Bob")
  @Test
  void findByIdWhenDenied() {
    Assertions.assertThatExceptionOfType(AuthorizationDeniedException.class)
      .isThrownBy(() -> bank.findById(1));
  }

  @WithMockUser("Alice")
  @Test
  void getById() {
    Assertions.assertThat(bank.getById(1)).isNotNull();
  }

  @WithMockUser("Bob")
  @Test
  void getByIdWhenDenied() {
    Assertions.assertThatExceptionOfType(AuthorizationDeniedException.class)
      .isThrownBy(() -> bank.getById(1));
  }

  @WithMockUser(username = "accountant", roles = "ACCOUNTANT")
  @Test
  void findByIdWhenAccountant() {
    Assertions.assertThat(bank.findById(1)).isNotNull();
  }

  @WithMockUser(username = "accountant", roles = "ACCOUNTANT")
  @Test
  void getByIdWhenAccountant() {
    Assertions.assertThat(bank.getById(1)).isNotNull();
  }

  @WithMockUser(username = "accountant", roles = "ACCOUNTANT")
  @Test
  void findByIdWhenAccountantNumber() {
    BankAccount bankAccount = bank.findById(1);
    Assertions.assertThat(bankAccount.getAccountNumber())
      .isEqualTo("*".repeat(10));
  }

}
