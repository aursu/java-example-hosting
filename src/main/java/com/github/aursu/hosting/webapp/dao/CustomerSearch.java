package com.github.aursu.hosting.webapp.dao;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class CustomerSearch {
    private String firstName;
    private String lastName;
    private String email;

    private Integer customerId;

    private Map<String, String> navigation = new HashMap<>();
    private String action;
    
    public void addPage(String action, String webPage) {
        navigation.put(action, webPage);
    }

    public String getPage(String action) {
        return navigation.get(action);
    }
}
