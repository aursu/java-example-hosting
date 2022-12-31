package com.github.aursu.hosting.webapp.services;

import org.springframework.stereotype.Component;

import com.google.common.net.InternetDomainName;

@Component
public class DomainService {
    public boolean isValidDomain(String domain) {
        return InternetDomainName.isValid(domain);
    }
}
