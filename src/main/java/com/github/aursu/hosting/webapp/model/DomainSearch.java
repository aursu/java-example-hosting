package com.github.aursu.hosting.webapp.model;

import com.github.aursu.hosting.webapp.entities.Customer;
import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class DomainSearch {
    private String domainName;

    private Domain domain;
    private Customer customer;
    private Product product;

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

    public void unsetCustomer() {
        customer = null;
    }

    public void unsetProduct() {
        product = null;
    }

    public void unset() {
        unsetDomain();
        unsetCustomer();
        unsetProduct();
    }
}
