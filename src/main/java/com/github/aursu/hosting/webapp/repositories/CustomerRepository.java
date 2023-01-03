package com.github.aursu.hosting.webapp.repositories;

import com.github.aursu.hosting.webapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findBy(String email, String firstName, String lastName);
}