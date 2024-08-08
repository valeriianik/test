package org.example.exampractice1.Web;

import org.example.exampractice1.Entities.Customer;
import org.example.exampractice1.Repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Model model;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer(null, "123", "John Doe", 1000.0, 5, "Savings-Delux");

        when(customerRepository.existsByCustno(anyString())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String viewName = customerController.addCustomer(customer, model);

        verify(customerRepository, times(1)).existsByCustno("123");
        verify(customerRepository, times(1)).save(customer);
        assertEquals("redirect:/", viewName);
    }

    @Test
    void addCustomer_ExistingCustomerNumber() {
        Customer customer = new Customer(null, "123", "John Doe", 1000.0, 5, "Savings-Delux");

        when(customerRepository.existsByCustno(anyString())).thenReturn(true);

        String viewName = customerController.addCustomer(customer, model);

        verify(customerRepository, times(1)).existsByCustno("123");
        verify(customerRepository, times(0)).save(any(Customer.class));
        assertEquals("addCustomer", viewName);
    }

    @Test
    void editCustomer() {
        Customer customer = new Customer(1L, "123", "John Doe", 1000.0, 5, "Savings-Delux");

        when(customerRepository.findByCustno(anyString())).thenReturn(null);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String viewName = customerController.editCustomer(customer, model);

        verify(customerRepository, times(1)).findByCustno("123");
        verify(customerRepository, times(1)).save(customer);
        assertEquals("redirect:/", viewName);
    }

    @Test
    void editCustomer_ExistingCustomerNumber() {
        Customer customer = new Customer(1L, "123", "John Doe", 1000.0, 5, "Savings-Delux");
        Customer existingCustomer = new Customer(2L, "123", "Jane Doe", 2000.0, 3, "Savings-Regular");

        when(customerRepository.findByCustno(anyString())).thenReturn(existingCustomer);

        String viewName = customerController.editCustomer(customer, model);

        verify(customerRepository, times(1)).findByCustno("123");
        verify(customerRepository, times(0)).save(any(Customer.class));
        assertEquals("editCustomer", viewName);
    }

    @Test
    void deleteCustomer() {
        doNothing().when(customerRepository).deleteById(anyLong());

        String viewName = customerController.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
        assertEquals("redirect:/", viewName);
    }
}
