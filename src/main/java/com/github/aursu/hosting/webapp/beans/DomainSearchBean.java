package com.github.aursu.hosting.webapp.beans;

import com.github.aursu.hosting.webapp.model.DomainSearch;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class DomainSearchBean {
    private DomainSearch domainSearch;

    public DomainSearch getDomainSearch() {
        if (domainSearch == null)
            return new DomainSearch();
        return domainSearch;
    }
}
