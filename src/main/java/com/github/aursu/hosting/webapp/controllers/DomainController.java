package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.beans.DomainSearchBean;
import com.github.aursu.hosting.webapp.model.CustomerSearch;
import com.github.aursu.hosting.webapp.model.DomainSearch;
import com.github.aursu.hosting.webapp.entities.Customer;
import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.entities.Product;
import com.github.aursu.hosting.webapp.repositories.DomainRepository;
import com.github.aursu.hosting.webapp.repositories.ProductRepository;
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

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DomainController {
    private final DomainRepository domainRepository;
    private final ProductRepository productRepository;

    private final DomainService domainService;

    private DomainSearchBean searchBean;

    @GetMapping(value = {"/search/domain"})
    public String showSearch(Model model, HttpServletRequest request) {

        DomainSearch search = searchBean.getDomainSearch();
        search.addPage("back", request.getRequestURI());

        searchBean.setDomainSearch(search);
        model.addAttribute("search", search);

        return "/search/domain";
    }

    @GetMapping(value = {"/"})
    public String index(Model model, HttpServletRequest request) {
        showSearch(model, request);
        return "main";
    }

    private String showDomain(Model model, String domainName, DomainSearch domainSearch) {
        domainSearch.setDomainName(domainName);

        // all cancellation actions and password update actions return to domain show page
        domainSearch.addPage("cancel-password", String.format("/show/domain/%s", domainName));
        domainSearch.addPage("update-password", String.format("/show/domain/%s", domainName));
        domainSearch.addPage("cancel", String.format("/show/domain/%s", domainName));

        return navigateTo(model, "/show/domain", domainSearch);
    }

    @PostMapping("/search/domain")
    public String search(Model model, @ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // check if submit action is "back" (do not search)
        if (search.getAction().equals("back")) {
            if (domainSearch.getPage("back") == null)
                return "redirect:/search/domain";

            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        String domain = search.getDomainName();

        return showDomain(model, domain, domainSearch);
    }

    @GetMapping(value = {"/show/domain/{domainName}"})
    public String showDomain(Model model, @PathVariable String domainName) {
        return showDomain(model, domainName, searchBean.getDomainSearch());
    }

    private String navigateTo(Model model, String path, DomainSearch domainSearch) {
        int status = setupDomain(domainSearch);

        model.addAttribute("search", domainSearch);

        if (status == 400) return "/invalid/domain";
        if (status == 404) return "/404/domain";

        // store domain into Bean
        searchBean.setDomainSearch(domainSearch);

        return path;
    }

    private int setupDomain(DomainSearch search) {
        String domain = search.getDomainName();

        if (domainService.isValidDomain(domain)) {
            Optional<Domain> domainLookup = domainRepository.findById(domain);

            if (domainLookup.isPresent()) {
                Domain domainEntity = domainLookup.get();

                search.setDomain(domainEntity);
                search.setCustomer(domainEntity.getCustomer());
                search.setProduct(domainEntity.getProduct());

                return 200;
            }

            // unset if not found
            search.unset();
            return 404;
        }
        return 400;
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
    public String showCreate(Model model, @ModelAttribute("search") DomainSearch search, RedirectAttributes redirectAttributes) {
        // restore navigation after redirect to CustomerController
        DomainSearch domainSearch = searchBean.getDomainSearch();

        String domain = domainSearch.getDomainName();

        // probably direct access to /create/domain URL
        if (domain == null) {
            return "redirect:/search/domain";
        }

        if (search.getCustomer() == null) {
            // we really need a customer alive here
            return searchCustomer(redirectAttributes);
        }

        Customer customer = search.getCustomer();
        domainSearch.setCustomer(customer);

        // provide domain and customer into template
        Domain domainEntity = new Domain();
        domainEntity.setId(domain);

        // store domain into session
        domainSearch.setDomain(domainEntity);

        // store it into session
        searchBean.setDomainSearch(domainSearch);

        // customer and domain are stored in Domain search object
        model.addAttribute("search", domainSearch);

        List<Product> packages = productRepository.findPackages();
        model.addAttribute("packages", packages);

        return "/create/domain";
    }

    @PostMapping("/create/domain")
    public String create(RedirectAttributes redirectAttributes, @ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // check if submit action is "back" (do not create)
        if (search.getAction().equals("back")) {
            if (domainSearch.getPage("back") == null)
                return "redirect:/search/domain";

            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        // save domain name to create it later
        String domain = search.getDomainName();
        domainSearch.setDomainName(domain);

        // store current domain search before redirect
        searchBean.setDomainSearch(domainSearch);

        return searchCustomer(redirectAttributes);
    }

    @PostMapping("/create/domain/{domainName}")
    public String create(@ModelAttribute DomainSearch search, @PathVariable String domainName) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // check if submit action is "back" (do not create)
        if (search.getAction().equals("cancel")) {
            if (domainSearch.getPage("cancel") == null)
                return "redirect:/create/domain";

            return String.format("redirect:%s", domainSearch.getPage("cancel"));
        }

        // extract domain from DomainSearch object
        Domain domain = search.getDomain();

        // setup customer from  session
        domain.setCustomer(search.getCustomer());

        // setup product from DomainSearch object
        domain.setProduct(search.getProduct());

        // save it to database
        domainRepository.save(domain);

        return String.format("redirect:/show/domain/%s", domainName);
    }

    @PostMapping("/edit/domain")
    public String edit(Model model, @ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        String domainName = search.getDomainName() == null ?
                domainSearch.getDomainName() : search.getDomainName();

        // check if submit action is "back" (do not edit)
        if (search.getAction().equals("back")) {
            if (domainSearch.getPage("back") == null)
                return "redirect:/search/domain";

            return String.format("redirect:%s", domainSearch.getPage("back"));
        }

        // password change
        if (search.getAction().equals("change-password")) {
            model.addAttribute("search", domainSearch);
            return "/edit/password/domain";
        }

        if (search.getAction().equals("cancel-password")) {
            if (domainSearch.getPage("cancel-password") == null)
                return String.format("redirect:/show/domain/%s", domainName);

            return String.format("redirect:%s", domainSearch.getPage("cancel-password"));
        }

        // update password
        if (search.getAction().equals("update-password")) {
            domainService.updatePassword(domainSearch.getDomain(), search.getPassword1(), search.getPassword2());

            if (domainSearch.getPage("update-password") == null)
                return String.format("redirect:/show/domain/%s", domainName);

            return String.format("redirect:%s", domainSearch.getPage("update-password"));
        }

        // we need packages to edit domain
        List<Product> packages = productRepository.findPackages();
        model.addAttribute("packages", packages);

        return navigateTo(model, "/edit/domain", domainSearch);
    }

    @PostMapping("/edit/domain/{domainName}")
    public String edit(Model model, @ModelAttribute DomainSearch search, @PathVariable String domainName) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // check if submit action is "back" (do not create)
        if (search.getAction().equals("cancel")) {
            if (domainSearch.getPage("cancel") == null)
                return "redirect:/search/domain";

            return String.format("redirect:%s", domainSearch.getPage("cancel"));
        }

        return String.format("redirect:/show/domain/%s", domainName);
    }
}
