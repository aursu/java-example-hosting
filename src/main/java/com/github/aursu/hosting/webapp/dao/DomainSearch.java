package com.github.aursu.hosting.webapp.dao;

import com.github.aursu.hosting.webapp.entities.Customer;
import com.github.aursu.hosting.webapp.entities.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Data
@Component
public class DomainSearch {
    private String domainName;

    private Domain domain;
    private Customer customer;
    private Package product;

    private Map<String, String> navigation = new HashMap<>();
    private String action;

    public void addPage(String action, String webPage) {
        navigation.put(action, webPage);
    }

    public String getPage(String  action) {
        return navigation.get(action);
    }

    public void unsetDomain() {
        domain = null;
    }
}
