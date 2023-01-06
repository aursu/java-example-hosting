package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.beans.DomainSearchBean;
import com.github.aursu.hosting.webapp.entities.DomainHostingService;
import com.github.aursu.hosting.webapp.model.CustomerSearch;
import com.github.aursu.hosting.webapp.model.DomainSearch;
import com.github.aursu.hosting.webapp.entities.Customer;
import com.github.aursu.hosting.webapp.entities.Domain;
import com.github.aursu.hosting.webapp.entities.Product;
import com.github.aursu.hosting.webapp.repositories.DomainHostingServiceRepository;
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

@Controller
@AllArgsConstructor
public class DomainController {
    private final ProductRepository productRepository;
    private final DomainHostingServiceRepository serviceRepository;

    private final DomainService domainService;

    private DomainSearchBean searchBean;

    @GetMapping(value = {"/"})
    public String index(Model model, HttpServletRequest request) {
        // title page  has own title "Hosting Platform"
        model.addAttribute("pageTitle", "Hosting Platform");

        return showSearchForm(model, request);
    }

    @GetMapping(value = {"/search/domain"})
    public String showSearch(Model model, HttpServletRequest request) {
        return showSearchForm(model, request);
    }

    @PostMapping(
            value = {
                    "/search/domain",
                    "/edit/domain",
                    "/create/domain"
            },
            params = "action=back")
    public String searchBack() {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        if (domainSearch.getPage("back") == null)
            return "redirect:/search/domain";

        return String.format("redirect:%s", domainSearch.getPage("back"));
    }

    @PostMapping(
            value = "/search/domain",
            params = "action=search")
    public String actionSearch(Model model, @ModelAttribute DomainSearch search) {
        return showDomain(model, search.getDomainName());
    }

    @PostMapping(
            value = "/edit/domain",
            params = "action=change-password")
    public String editDomainChangePassword(Model model, @ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        model.addAttribute("search", domainSearch);
        return "/edit/password/domain";
    }

    @PostMapping(
            value = "/edit/domain",
            params = "action=cancel-password")
    public String cancelPasswordEdit() {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        if (domainSearch.getPage("cancel-password") == null)
            return String.format("redirect:/show/domain/%s", domainSearch.getDomainName());

        return String.format("redirect:%s", domainSearch.getPage("cancel-password"));
    }

    @PostMapping(
            value = "/edit/domain",
            params = "action=update-password")
    public String updatePassword(@ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        domainService.updatePassword(domainSearch.getDomain(), search.getPassword1(), search.getPassword2());

        if (domainSearch.getPage("update-password") == null)
            return String.format("redirect:/show/domain/%s", domainSearch.getDomainName());

        return String.format("redirect:%s", domainSearch.getPage("update-password"));
    }

    @GetMapping(value = {"/show/domain/{domainName}"})
    public String showDomainByName(Model model, @PathVariable String domainName) {
        return showDomain(model, domainName);
    }

    @PostMapping(
            value = "/edit/domain",
            params = "action=update")
    public String editDomainForm(Model model, @ModelAttribute DomainSearch search) {
        return showEditDomainForm(model);
    }

    @PostMapping(
            value = "/edit/domain/{domainName}",
            params = "action=cancel")
    public String cancelEditDomain(@PathVariable String domainName) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        if (domainSearch.getPage("cancel") == null)
            return String.format("redirect:/search/domain/%s", domainName);

        return String.format("redirect:%s", domainSearch.getPage("cancel"));
    }

    @PostMapping(
            value = "/edit/domain/{ignoredDomainName}",
            params = "action=update")
    public String editDomain(Model model, @ModelAttribute DomainSearch search, @PathVariable String ignoredDomainName) {
        // extract domain from DomainSearch object
        Domain domain = search.getDomain();

        // setup customer from  session
        domain.setCustomer(search.getCustomer());

        // setup product from DomainSearch object
        domain.setProduct(search.getProduct());

        // update it in database
        domainService.updateDomain(domain);

        return showEditDomainForm(model);
    }

    @GetMapping("/create/domain")
    public String showCreate(Model model, @ModelAttribute("search") DomainSearch search, RedirectAttributes redirectAttributes) {
        // restore navigation after redirect to CustomerController
        DomainSearch domainSearch = searchBean.getDomainSearch();
        String domainName = domainSearch.getDomainName();

        // probably direct access to /create/domain URL
        if (domainName == null) {
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
        domainEntity.setId(domainName);

        // store domain into session
        domainSearch.setDomain(domainEntity);

        // when cancelled - search for new domain
        domainSearch.addPage("cancel", "/search/domain");

        // store it into session
        searchBean.setDomainSearch(domainSearch);

        // customer and domain are stored in Domain search object
        model.addAttribute("search", domainSearch);

        List<Product> packages = productRepository.findPackages();
        model.addAttribute("packages", packages);

        return "/create/domain";
    }

    @PostMapping(
            value = "/create/domain",
            params = "action=create")
    public String selectCustomer(RedirectAttributes redirectAttributes, @ModelAttribute DomainSearch search) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // save domain name to create it later
        String domainName = search.getDomainName();
        domainSearch.setDomainName(domainName);

        // store current domain search before redirect
        searchBean.setDomainSearch(domainSearch);

        return searchCustomer(redirectAttributes);
    }

    @PostMapping(
            value = "/create/domain/{ignoredDomainName}",
            params = "action=cancel")
    public String cancelCreate(@PathVariable String ignoredDomainName) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        // check if submit action is "back" (do not create)
        if (domainSearch.getPage("cancel") == null)
            return "redirect:/search/domain";

        return String.format("redirect:%s", domainSearch.getPage("cancel"));
    }

    @PostMapping(
            value = "/create/domain/{domainName}",
            params = "action=create")
    public String create(@ModelAttribute DomainSearch search, @PathVariable String domainName) {

        // extract domain from DomainSearch object
        Domain domain = search.getDomain();

        // setup customer from  session
        domain.setCustomer(search.getCustomer());

        // setup product from DomainSearch object
        domain.setProduct(search.getProduct());

        // save it to database
        domainService.createDomain(domain);

        return String.format("redirect:/show/domain/%s", domainName);
    }

    private String showDomain(Model model, String domainName) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        domainSearch.setDomainName(domainName);

        domainSearch.addPage("cancel-password", String.format("/show/domain/%s", domainName));
        domainSearch.addPage("update-password", String.format("/show/domain/%s", domainName));

        return navigateTo(model, "/show/domain", domainSearch);
    }

    private String navigateTo(Model model, String path, DomainSearch domainSearch) {
        int status = setupDomain(domainSearch);

        model.addAttribute("search", domainSearch);

        if (status == 400) {
            model.addAttribute("pageTitle", "Invalid Domain Name");
            model.addAttribute("message", String.format("Domain name \"%s\" is invalid", domainSearch.getDomainName()));

            return "/search/domain";
        }

        if (status == 404) return "/404/domain";

        // store domain into Bean
        searchBean.setDomainSearch(domainSearch);

        return path;
    }

    private int setupDomain(DomainSearch search) {
        String domain = search.getDomainName();

        if ( ! domainService.isValidDomain(domain)) return 400;

        Domain domainEntity = domainService.lookupDomain(domain);

        if (domainEntity == null) {
            search.unset();
            return 404;
        }

        search.setDomain(domainEntity);
        search.setCustomer(domainEntity.getCustomer());
        search.setProduct(domainEntity.getProduct());

        return 200;
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

    private String showEditDomainForm(Model model) {
        DomainSearch domainSearch = searchBean.getDomainSearch();
        String domainName = domainSearch.getDomainName();

        // we need packages to edit domain
        List<Product> packages = productRepository.findPackages();
        model.addAttribute("packages", packages);

        // we need services to add to domain
        List<Product> services = productRepository.findServices();
        model.addAttribute("services", services);

        // existing domain services
        List<DomainHostingService> domainServices = serviceRepository.findByDomainName(domainName);
        model.addAttribute("domainServices", domainServices);

        domainSearch.addPage("cancel", String.format("/show/domain/%s", domainName));

        return navigateTo(model, "/edit/domain", domainSearch);
    }

    private String showSearchForm(Model model, HttpServletRequest request) {
        DomainSearch domainSearch = searchBean.getDomainSearch();

        domainSearch.addPage("back", request.getRequestURI());

        searchBean.setDomainSearch(domainSearch);

        model.addAttribute("search", domainSearch);

        return "/search/domain";
    }
}
