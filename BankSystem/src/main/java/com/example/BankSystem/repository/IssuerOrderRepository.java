package com.example.BankSystem.repository;

import com.example.BankSystem.model.IssuerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuerOrderRepository extends JpaRepository<IssuerOrder, Integer> {
}
