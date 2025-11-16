package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerRepository {
    private final List<Customer> customers = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);
    public List<Customer> findAll(){
        return new ArrayList<>(customers);
    }
    public Customer save(Customer customer){
        if (customer.getId() == null) {
            customer.setId(idSequence.getAndIncrement());
            customers.add(customer);
        } else {
            customers.removeIf(r -> r.getId().equals(customer.getId()));
            customers.add(customer);
        }
        return customer;
    }
    public void remove(Long id){
        customers.removeIf(c -> c.getId().equals(id));
    }
    public Customer findById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
