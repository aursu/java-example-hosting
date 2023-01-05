package com.github.aursu.hosting.webapp.services;

import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.repositories.DomainRepository;
import com.google.common.net.InternetDomainName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;

    public boolean isValidDomain(String domain) {
        if (domain == null || domain.isBlank()) return false;
        return InternetDomainName.isValid(domain);
    }

    private Domain checkSubdomain(String subDomain) {
        InternetDomainName idn = InternetDomainName.from(subDomain);

        if ( ! idn.hasParent() ) return null;

        InternetDomainName parent = idn.parent(),
                topParent = idn.topPrivateDomain();

        Optional<Domain> parentLookup = domainRepository.findById(parent.toString());

        if (parentLookup.isEmpty()) return null;

        return parentLookup.get();
    }

    private Domain setParentDomain(Domain domain) {
        String domainName = domain.getId();
        Integer customerId = domain.getCustomer().getId();

        // check if subdomain could be added
        if (domain.getType().equals("Subdomain")) {
            // check if Primary domain exists
            Domain parentDomain = checkSubdomain(domainName);

            if (parentDomain == null || ! parentDomain.getCustomer().getId().equals(customerId)) {
                // it can not be Subdomain
                domain.setType("Regular");
            } else {
                // set primary domain
                domain.setPrimaryDomain(parentDomain);
            }
        }

        return domain;
    }

    public boolean createDomain(Domain domain) {
        // validate input
        if (domain == null || domain.getId() == null) return false;

        String domainName = domain.getId();

        if( ! isValidDomain(domainName)) return false;

        domain = setParentDomain(domain);

        domainRepository.save(domain);

        return true;
    }

    public boolean updatePassword(Domain domain, String password1, String password2) {
        String domainName = domain.getId();

        Optional<Domain> domainLookup = domainRepository.findById(domainName);
        if (domainLookup.isPresent()  && password2.equals(password1)) {
            Domain domainEntity = domainLookup.get();
            domainEntity.setPassword(password1);

            domainRepository.save(domainEntity);

            return true;
        }
        return false;
    }

    public boolean updateDomain(Domain domain) {
        if (domain == null || domain.getId() == null) return false;

        String domainName = domain.getId();

        if( ! isValidDomain(domainName)) return false;

        Optional<Domain> domainLookup = domainRepository.findById(domainName);

        // nothing to update
        if (domainLookup.isEmpty()) return false;

        Domain domainEntity = domainLookup.get();

        // domain type
        if ( ! Objects.equals(domain.getType(), domainEntity.getType()))
            domain = setParentDomain(domain);

        // domain has been suspended or unsuspended
        if ( ! Objects.equals(domain.getStatus(), domainEntity.getStatus()) )
            if (domain.getStatus().equals("Suspend")) {
                if (domain.getSuspendDate() == null)
                    domain.setSuspendDate(LocalDateTime.now());
            } else if (domainEntity.getStatus().equals("Suspend")) {
                domain.setSuspendDate(null);
            }

        domainRepository.save(domain);

        return true;
    }
}
