package com.github.aursu.hosting.webapp.repositories;

import com.github.aursu.hosting.webapp.entities.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, String> {
}