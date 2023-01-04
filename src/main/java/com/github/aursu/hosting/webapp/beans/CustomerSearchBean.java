package com.github.aursu.hosting.webapp.beans;

import com.github.aursu.hosting.webapp.model.CustomerSearch;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class CustomerSearchBean {
    private CustomerSearch customerSearch;

    public CustomerSearch getCustomerSearch() {
        if (customerSearch == null)
            return new CustomerSearch();
        return customerSearch;
    }
}
