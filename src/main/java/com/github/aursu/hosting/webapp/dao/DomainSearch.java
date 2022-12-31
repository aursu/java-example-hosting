package com.github.aursu.hosting.webapp.dao;

import com.github.aursu.hosting.webapp.entities.Domain;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DomainSearch {
    private String domainName;
    private Domain domain;

    private String webPage;
    private String action;
}
