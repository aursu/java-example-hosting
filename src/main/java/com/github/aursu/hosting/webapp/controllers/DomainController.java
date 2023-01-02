package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.dao.CustomerSearch;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class DomainController {
    private final DomainRepository domainRepository;

    private final DomainService domainService;

    private DomainSearch domainSearch;

    @GetMapping(value = {"/search/domain"})
    public String showSearch(Model model, HttpServletRequest request) {
        domainSearch.addPage("back", request.getRequestURI());
        model.addAttribute("search", domainSearch);

        return "/search/domain";
    }

    @GetMapping(value = {"/"})
    public String index(Model model, HttpServletRequest request) {
        showSearch(model, request);
        return "main";
    }

    private String searchCustomer(RedirectAttributes redirectAttributes) {
        // Now we need customer to create domain for. Look for it up and return to domain creation
        CustomerSearch customerSearch = new CustomerSearch();

        // we can create domain only when customer has been found and returned
        customerSearch.addPage("select", "/create/domain");

        // if customer could not be found, then return to domain search again
        customerSearch.addPage("back", "/search/domain");

        redirectAttributes.addFlashAttribute("search", customerSearch);
        return "redirect:/search/customer";
    }

    @GetMapping(value = {"/create/domain"})
    public String showCreate(Model model,  @ModelAttribute("search") DomainSearch search, RedirectAttributes redirectAttributes) {

        String  domain = domainSearch.getDomainName();

        // probably direct access to /create/domain URL
        if (domain == null) {
            if (domainSearch.getPage("back") == null)
                // set back URL to /search/domain if not set any
                domainSearch.addPage("back", "/search/domain");

            // return to previous page if domain is not specified
            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        if (search == null || search.getCustomer() == null) {
            return searchCustomer(redirectAttributes);
        }
        else {
            domainSearch.setCustomer(search.getCustomer());
        }

        Domain domainEntity = new Domain();
        domainEntity.setId(domain);

        // customer is stored in Domain search object
        model.addAttribute("search", domainSearch);

        model.addAttribute("domain", domainEntity);

        return "/create/domain";
    }

    @PostMapping("/search/domain")
    public String search(Model model, @ModelAttribute DomainSearch search) {
        // check if submit action is "back" (do not create)
        if (search.getAction().equals("back")) {
            if (domainSearch.getPage("back") == null)
                // set back URL to /search/domain if not set any
                domainSearch.addPage("back", "/search/domain");
            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        String domain = search.getDomainName();

        if (domainService.isValidDomain(domain)) {
            domainSearch.setDomainName(domain);

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
            model.addAttribute("search", search);
            return "/invalid/domain";
        }
    }

    @PostMapping("/create/domain")
    public String create(Model model, @ModelAttribute DomainSearch search, RedirectAttributes redirectAttributes) {

        // check if submit action is "back" (do not create)
        if (search.getAction().equals("back")) {
            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        // save domain name to create it later
        String domainName = search.getDomainName();
        domainSearch.setDomainName(domainName);

        return searchCustomer(redirectAttributes);
    }

    @PostMapping("/create/domain/{domain}")
    public String create(Model model, @ModelAttribute Domain domainEntity, @PathVariable String domain) {
        return "redirect:/";
    }
}
