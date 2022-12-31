package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.dao.DomainSearch;
import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.repositories.DomainRepository;
import com.github.aursu.hosting.webapp.services.DomainService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class DomainController {
    private final DomainRepository domainRepository;

    private final DomainService domainService;

    @GetMapping(value = {"/", "/search/domain"})
    public String index(Model model, HttpServletRequest request) {
        DomainSearch domainSearch = new DomainSearch();

        domainSearch.setWebPage(request.getRequestURI());

        model.addAttribute("search", domainSearch);
        return "/search/domain";
    }

    @PostMapping("/search/domain")
    public String search(Model model, @ModelAttribute DomainSearch domainSearch) {
        String domain = domainSearch.getDomainName();

        if (domainService.isValidDomain(domain)) {
            Optional<Domain> domainLookup = domainRepository.findById(domain);

            if (domainLookup.isPresent()) {
                Domain domainEntity = domainLookup.get();

                domainSearch.setDomain(domainEntity);
                model.addAttribute("domain", domainEntity);
            }

            model.addAttribute("search", domainSearch);
            return "/show/domain";
        }
        else {
            model.addAttribute("search", domainSearch);
            return "/invalid/domain";
        }
    }

    @PostMapping("/create/domain")
    public String create(Model model, @ModelAttribute DomainSearch domainSearch) {

        // check if submit action is "back" (do not create)
        if (domainSearch.getAction().equals("back")) {
            return String.format("redirect:%s", domainSearch.getWebPage());
        }

        String domainName = domainSearch.getDomainName();

        Domain domainEntity = new Domain();
        domainEntity.setId(domainName);

        model.addAttribute("domain", domainEntity);
        return "/create/domain";
    }

    @PostMapping("/create/domain/{domain}")
    public String create(Model model, @ModelAttribute Domain domainEntity, @PathVariable String domain) {
        return "redirect:/";
    }
}
