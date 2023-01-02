package com.github.aursu.hosting.webapp.controllers;

import com.github.aursu.hosting.webapp.dao.CustomerSearch;
import com.github.aursu.hosting.webapp.dao.DomainSearch;
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

    private CustomerSearch customerSearch;

    @GetMapping("/search/customer")
    public String showSearch(Model model, @ModelAttribute("search") CustomerSearch search) {

        // copy navigation data into bean
        for (String action : search.getNavigation().keySet())
            customerSearch.addPage(action, search.getPage(action));

        model.addAttribute("search", customerSearch);
        return "/search/customer";
    }

    @PostMapping("/search/customer")
    public String search(Model model, @ModelAttribute CustomerSearch search) {
        // cancel customer search
        if (search.getAction().equals("back")) {
            // in case of direct page access - set back button to page itself
            if (customerSearch.getPage("back") == null)
                // set back URL to /search/domain if not set any
                customerSearch.addPage("back", "/search/customer");
            return String.format("redirect:%s", customerSearch.getPage("back"));
        }

         String email = search.getEmail(),
                firstName = search.getFirstName(),
                lastName = search.getLastName();

        email = email == null || email.isBlank() ? "" : email;
        firstName = firstName == null || firstName.isBlank() ? "" : firstName;
        lastName = lastName == null || lastName.isBlank() ? "" : lastName;

        List<Customer> customerList = customerRepository.findBy(email, firstName, lastName);

        // added ability to cancel current search
        customerSearch.addPage("cancel", "/search/customer");

        model.addAttribute("search", customerSearch);
        // not found
        if (customerList.isEmpty()) {
            return "/search/customer";
        }
        else {
            model.addAttribute("customers", customerList);
            return "/select/customer";
        }
    }

    @PostMapping("/select/customer")
    public String select(Model model, @ModelAttribute CustomerSearch search, RedirectAttributes redirectAttributes) {
        // check if submit action is "cancel" (do not create)
        if (search.getAction().equals("cancel")) {
            return String.format("redirect:%s", customerSearch.getPage("cancel"));
        }

        Integer customerId = search.getCustomerId();

        // whatever reason for missed customerId - search it again
        if (customerId == null) {
            model.addAttribute("search", customerSearch);
            return "/search/customer";
        }

        // lookup for customer and search again if not present
        Optional<Customer> customerLookup = customerRepository.findById(customerId);
        if ( ! customerLookup.isPresent()) {
            model.addAttribute("search", customerSearch);
            return "/search/customer";
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
