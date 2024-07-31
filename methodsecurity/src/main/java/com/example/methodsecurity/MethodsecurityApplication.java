package com.example.methodsecurity;

import com.example.methodsecurity.service.BankAccountService;
import com.example.methodsecurity.service.BankAccountServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MethodsecurityApplication {

	@Bean
	BankAccountService bankAccountService() {
		return new BankAccountServiceImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(MethodsecurityApplication.class, args);
	}

}
