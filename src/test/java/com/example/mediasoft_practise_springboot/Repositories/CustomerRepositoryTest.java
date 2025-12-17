package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void saveAndFindAllCustomers() {
        Customer c1 = new Customer();
        c1.setName("Ivan");
        c1.setAge(25);
        c1.setGender(GenderEnum.Male);
        customerRepository.save(c1);

        Customer c2 = new Customer();
        c2.setName("Maria");
        c2.setAge(30);
        c2.setGender(GenderEnum.Female);
        customerRepository.save(c2);

        List<Customer> customers = customerRepository.findAll();
        assertEquals(2, customers.size());
        assertTrue(customers.stream().anyMatch(c -> c.getName().equals("Ivan")));
        assertTrue(customers.stream().anyMatch(c -> c.getName().equals("Maria")));
    }

    @Test
    void findCustomerById() {
        Customer c = new Customer();
        c.setName("Alex");
        c.setAge(28);
        c.setGender(GenderEnum.Male);
        Customer saved = customerRepository.save(c);

        Optional<Customer> found = customerRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Alex", found.get().getName());
    }
}