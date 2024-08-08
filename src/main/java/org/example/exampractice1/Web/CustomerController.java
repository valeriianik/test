//package org.example.exampractice1.Web;
//
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//@SessionAttributes({"a", "e"})
//@Controller
//@AllArgsConstructor
//public class CustomerController {
//
//    @GetMapping(path = "/")
//    public String index(Model model) {
//        return "customers";
//    }
//}

//package org.example.exampractice1.Web;
//
//import lombok.AllArgsConstructor;
//import org.example.exampractice1.Entities.Customer;
//import org.example.exampractice1.Repositories.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import java.util.List;
//
//@SessionAttributes({"a", "e"})
//@Controller
//@AllArgsConstructor
//public class CustomerController {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @GetMapping(path = "/")
//    public String customers(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
//        List<Customer> customers;
//        if (keyword.isEmpty()) {
//            customers = customerRepository.findAll();
//        } else {
//            customers = customerRepository.findByCustnoContaining(keyword);
//        }
//        model.addAttribute("listCustomers", customers);
//        return "customers";
//    }
//}

package org.example.exampractice1.Web;

import lombok.AllArgsConstructor;
import org.example.exampractice1.Entities.Customer;
import org.example.exampractice1.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"a", "e"})
@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/")
    public String customers(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Customer> customers;
        if (keyword.isEmpty()) {
            customers = customerRepository.findAll();
        } else {
            customers = customerRepository.findByCustnoContaining(keyword);
        }
        model.addAttribute("listCustomers", customers);
        return "customers";
    }

    @GetMapping("/addCustomer")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "addCustomer";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        if (customerRepository.existsByCustno(customer.getCustno())) {
            model.addAttribute("errorMessage", "Customer number already exists.");
            return "addCustomer";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/editCustomer")
    public String showEditCustomerForm(@RequestParam("id") Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "editCustomer";
    }

    @PostMapping("/editCustomer")
    public String editCustomer(@ModelAttribute Customer customer, Model model) {
        Customer existingCustomer = customerRepository.findByCustno(customer.getCustno());
        if (existingCustomer != null && !existingCustomer.getId().equals(customer.getId())) {
            model.addAttribute("errorMessage", "Customer number already exists.");
            return "editCustomer";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam("id") Long id) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/projectInvestment")
    public String projectInvestment(@RequestParam("id") Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        List<ProjectionRow> projection = calculateProjection(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("projection", projection);
        return "projectInvestment";
    }

    private List<ProjectionRow> calculateProjection(Customer customer) {
        List<ProjectionRow> projection = new ArrayList<>();
        double interestRate = customer.getSavtype().equals("Savings-Delux") ? 0.15 : 0.10;
        double startingAmount = customer.getCdep();
        for (int year = 1; year <= customer.getNyears(); year++) {
            double interest = startingAmount * interestRate;
            double endingBalance = startingAmount + interest;
            projection.add(new ProjectionRow(year, startingAmount, interest, endingBalance));
            startingAmount = endingBalance;
        }
        return projection;
    }

    public static class ProjectionRow {
        private final int year;
        private final double startingAmount;
        private final double interest;
        private final double endingBalance;

        public ProjectionRow(int year, double startingAmount, double interest, double endingBalance) {
            this.year = year;
            this.startingAmount = startingAmount;
            this.interest = interest;
            this.endingBalance = endingBalance;
        }

        public int getYear() {
            return year;
        }

        public double getStartingAmount() {
            return startingAmount;
        }

        public double getInterest() {
            return interest;
        }

        public double getEndingBalance() {
            return endingBalance;
        }
    }
}


