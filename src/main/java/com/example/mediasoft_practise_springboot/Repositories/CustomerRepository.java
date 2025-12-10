package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Методы генерируются автоматически
}
