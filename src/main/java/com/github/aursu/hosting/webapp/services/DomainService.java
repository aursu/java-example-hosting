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

    public boolean isValidDomain(Domain domain) {
        if (domain == null) return false;

        return isValidDomain(domain.getId());
    }

    public boolean isValidDomain(String domain) {
        if (domain == null || domain.isBlank()) return false;
        return InternetDomainName.isValid(domain);
    }

    public Domain lookupDomain(String domain) {
        if (! isValidDomain(domain)) return null;

        Optional<Domain> domainLookup = domainRepository.findById(domain);

        if (domainLookup.isEmpty()) return null;

        Domain domainEntity = domainLookup.get();

        return domainEntity;
    }

    public Domain lookupDomain(Domain domain) {
        if (domain == null) return null;

        return lookupDomain(domain.getId());
    }

    private Domain checkSubdomain(String subDomain) {
        InternetDomainName idn = InternetDomainName.from(subDomain);

        if ( ! idn.hasParent() ) return null;
        InternetDomainName parent = idn.parent();

        return lookupDomain(parent.toString());
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
        if( ! isValidDomain(domain)) return false;
        
        domainRepository.save(setParentDomain(domain));

        return true;
    }

    public boolean updatePassword(Domain domain, String password1, String password2) {
        if ( ! password2.equals(password1)) return false;

        Domain domainEntity = lookupDomain(domain);

        if (domainEntity == null) return false;

        domainEntity.setPassword(password1);
        domainRepository.save(domainEntity);

        return true;
    }

    public boolean updateDomain(Domain domain) {
        Domain domainEntity = lookupDomain(domain);
        if (domainEntity == null) return false;

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
