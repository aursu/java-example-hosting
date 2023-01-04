package com.github.aursu.hosting.webapp.services;

import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.repositories.DomainRepository;
import com.google.common.net.InternetDomainName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;

    public boolean isValidDomain(String domain) {
        return InternetDomainName.isValid(domain);
    }

    public void updatePassword(Domain domain, String password1, String password2) {
        String domainName = domain.getId();

        Optional<Domain> domainLookup = domainRepository.findById(domainName);
        if (domainLookup.isPresent()  && password2.equals(password1)) {
            Domain domainEntity = domainLookup.get();
            domainEntity.setPassword(password1);

            domainRepository.save(domainEntity);
        }
    }
}
