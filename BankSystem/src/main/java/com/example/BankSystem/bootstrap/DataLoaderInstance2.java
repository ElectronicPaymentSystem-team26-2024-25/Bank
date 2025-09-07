package com.example.BankSystem.bootstrap;

import com.example.BankSystem.model.BankAccount;
import com.example.BankSystem.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("instance2")
public class DataLoaderInstance2 implements CommandLineRunner {
    private final BankAccountRepository repo;
    public DataLoaderInstance2(BankAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        BankAccount a = new BankAccount();
        a.setAccountId("hr41");
        a.setPAN("22222222222");
        a.setSecurityCode(333);
        a.setCardHolderName("Petar Petrovic");
        a.setExpirationDate(LocalDate.parse("2027-10-30"));
        a.setAvailableFunds(5000);
        a.setAccountNumber("15000000005678");
        repo.save(a);

    }
}