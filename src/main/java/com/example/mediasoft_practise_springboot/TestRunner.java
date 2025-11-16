package com.example.mediasoft_practise_springboot;

import com.example.mediasoft_practise_springboot.Services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    public TestRunner(CustomerService customerService,
                      RestaurantService restaurantService,
                      RatingService ratingService) {
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Тесты с помощью CommandLineRunner ===");
        System.out.println("Посетители: " + customerService.findAll());
        System.out.println("Рестораны: " + restaurantService.findAll());
        System.out.println("Оценки: " + ratingService.findAll());
    }
}

