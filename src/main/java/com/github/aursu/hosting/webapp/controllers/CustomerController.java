package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.beans.CustomerSearchBean;
import com.github.aursu.hosting.webapp.model.CustomerSearch;
import com.github.aursu.hosting.webapp.model.DomainSearch;
import com.github.aursu.hosting.webapp.entities.Customer;
import com.github.aursu.hosting.webapp.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;

    private CustomerSearchBean searchBean;

    @GetMapping("/search/customer")
    public String showSearch(Model model, @ModelAttribute("search") CustomerSearch search) {
        CustomerSearch customerSearch = searchBean.getCustomerSearch();

        // copy navigation data into bean
        for (String action : search.getNavigation().keySet())
            customerSearch.addPage(action, search.getPage(action));

        searchBean.setCustomerSearch(customerSearch);

        model.addAttribute("search", customerSearch);
        return "/search/customer";
    }

    @PostMapping(
            value = "/search/customer",
            params = "action=back")
    public String backSearch() {
        CustomerSearch customerSearch = searchBean.getCustomerSearch();

        if (customerSearch.getPage("back") == null)
            return "redirect:/search/customer";

        return String.format("redirect:%s", customerSearch.getPage("back"));
    }

    @PostMapping(
            value = "/search/customer",
            params = "action=search")
    public String search(Model model, @ModelAttribute CustomerSearch search) {
        CustomerSearch customerSearch = searchBean.getCustomerSearch();

         String email = search.getEmail(),
                firstName = search.getFirstName(),
                lastName = search.getLastName();

        email = email == null || email.isBlank() ? "" : email;
        firstName = firstName == null || firstName.isBlank() ? "" : firstName;
        lastName = lastName == null || lastName.isBlank() ? "" : lastName;

        List<Customer> customerList = customerRepository.findBy(email, firstName, lastName);

        // added ability to cancel current search
        customerSearch.addPage("cancel", "/search/customer");

        customerSearch.setFirstName(firstName);
        customerSearch.setLastName(lastName);
        customerSearch.setEmail(email);

        searchBean.setCustomerSearch(customerSearch);

        // not found
        if (customerList.isEmpty())
            return "redirect:/search/customer";

        model.addAttribute("search", customerSearch);
        model.addAttribute("customers", customerList);

        return "/select/customer";
    }

    @PostMapping(
            value = "/select/customer",
            params = "action=cancel")
    public String cancelSelect() {
        CustomerSearch customerSearch = searchBean.getCustomerSearch();

        if (customerSearch.getPage("cancel") == null)
            return "redirect:/search/customer";

        return String.format("redirect:%s", customerSearch.getPage("cancel"));
    }

    @PostMapping(
            value = "/select/customer",
            params = "action=select")
    public String select(@ModelAttribute CustomerSearch search,
                         RedirectAttributes redirectAttributes) {
        CustomerSearch customerSearch = searchBean.getCustomerSearch();

        Integer customerId = search.getCustomerId();

        // whatever reason for missed customerId - search it again
        if (customerId == null) {
            return "redirect:/search/customer";
        }

        // lookup for customer and search again if not present
        Optional<Customer> customerLookup = customerRepository.findById(customerId);
        if (customerLookup.isEmpty()) {
            return "redirect:/search/customer";
        }

        // if customer selected
        String selectAction = customerSearch.getPage("select");

        // if select action is not set then return home
        if (selectAction == null) {
            return "redirect:/";
        } else if (selectAction.equals("/create/domain")) {

            // save customer into DomainSearch object
            DomainSearch domainSearch = new DomainSearch();
            domainSearch.setCustomer(customerLookup.get());
            redirectAttributes.addFlashAttribute("search", domainSearch);
        }

        return String.format("redirect:%s", customerSearch.getPage("select"));
    }
}
