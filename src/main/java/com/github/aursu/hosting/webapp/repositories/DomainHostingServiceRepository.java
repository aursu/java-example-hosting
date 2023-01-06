package com.github.aursu.hosting.webapp.repositories;

import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.entities.DomainHostingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainHostingServiceRepository extends JpaRepository<DomainHostingService, Long> {
    List<DomainHostingService> findByDomain(Domain domain);
    List<DomainHostingService> findByDomainName(String domain);
}