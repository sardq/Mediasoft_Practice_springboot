package com.example.mediasoft_practise_springboot;

import com.example.mediasoft_practise_springboot.Entities.*;
import com.example.mediasoft_practise_springboot.Enums.*;
import com.example.mediasoft_practise_springboot.Services.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestDataInitializer {

    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    public TestDataInitializer(CustomerService customerService,
                               RestaurantService restaurantService,
                               RatingService ratingService) {
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
    }

    @PostConstruct
    public void init() {

        Customer c1 = customerService.save(
                Customer.builder().age(21).gender(GenderEnum.Male).name("Сергей").build()
        );
        Customer c2 = customerService.save(
                Customer.builder().age(30).gender(GenderEnum.Female).build()
        );

        Restaurant r1 = restaurantService.save(
                Restaurant.builder().name("La Pasta").kitchenType(KitchenType.Italy).averageCheck(BigDecimal.valueOf(823.11)).build()
        );
        Restaurant r2 = restaurantService.save(
                Restaurant.builder().name("China Town").kitchenType(KitchenType.China).averageCheck(BigDecimal.valueOf(1321.31)).build()
        );

        ratingService.save(new Rating(c1.getId(), r1.getId(), 5, "Отлично!"));
        Rating rating2 = ratingService.save(new Rating(c2.getId(), r1.getId(), 3, "Нормально"));
        ratingService.save(new Rating(c2.getId(), r2.getId(), 3, null));

        System.out.println("Все пользователи: " + customerService.findAll());
        System.out.println("Все рестораны: " + restaurantService.findAll());
        System.out.println("Все оценки: " + ratingService.findAll());
        customerService.remove(c2.getId());
        restaurantService.remove(r2.getId());
        ratingService.remove(rating2.getCustomerId(), rating2.getRestaurantId());
    }
}
