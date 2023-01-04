package com.github.aursu.hosting.webapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CustomerSearch {
    private Integer customerId;

    private String firstName;
    private String lastName;
    private String email;

    private Map<String, String> navigation = new HashMap<>();
    private String action;
    
    public void addPage(String action, String webPage) {
        navigation.put(action, webPage);
    }

    public String getPage(String action) {
        return navigation.get(action);
    }
}
