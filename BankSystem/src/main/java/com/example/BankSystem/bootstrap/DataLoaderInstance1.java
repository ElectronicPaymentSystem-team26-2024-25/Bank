package com.example.BankSystem.bootstrap;

import com.example.BankSystem.model.BankAccount;
import com.example.BankSystem.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("instance1")
public class DataLoaderInstance1 implements CommandLineRunner {
    private final BankAccountRepository repo;
    public DataLoaderInstance1(BankAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        BankAccount a = new BankAccount();
        a.setAccountId("fd23");
        a.setPAN("11111111111");
        a.setSecurityCode(222);
        a.setCardHolderName("Ivan Partalo");
        a.setExpirationDate(LocalDate.parse("2027-10-30"));
        a.setAvailableFunds(5000);
        a.setAccountNumber("17000000001234");
        repo.save(a);

        BankAccount b = new BankAccount();
        b.setAccountId("gf42");
        b.setPAN("11111111122");
        b.setSecurityCode(111);
        b.setCardHolderName("John Doe");
        b.setExpirationDate(LocalDate.parse("2026-10-30"));
        b.setAvailableFunds(15000);
        b.setMerchantId("123e4567-e89b-12d3-a456-426614174000");
        b.setMerchantPassword("789e1234-e89b-56d3-a456-426614174111");
        b.setAccountNumber("17000000004321");
        repo.save(b);
    }
}