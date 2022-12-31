package com.github.aursu.hosting.webapp.repositories;

import com.github.aursu.hosting.webapp.entities.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, String> {
}