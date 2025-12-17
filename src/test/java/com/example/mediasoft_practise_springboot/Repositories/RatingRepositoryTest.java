package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void saveAndFindByRestaurantId() {
        Customer customer = customerRepository.save(new Customer(null, "Ivan", 25, GenderEnum.Male));
        Restaurant restaurant = restaurantRepository.save(
                new Restaurant(null, "Pizza House", "Italian", KitchenType.Italy, BigDecimal.valueOf(1000), BigDecimal.valueOf(4.5))
        );

        Rating r1 = new Rating(customer.getId(), restaurant.getId(), 5, "Excellent", customer, restaurant);
        ratingRepository.save(r1);

        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurant.getId());
        assertEquals(1, ratings.size());
        assertEquals(5, ratings.get(0).getRate());
    }

    @Test
    void saveAndFindByCustomerId() {
        Customer customer = customerRepository.save(new Customer(null, "Maria", 30, GenderEnum.Female));
        Restaurant restaurant = restaurantRepository.save(
                new Restaurant(null, "Sushi Bar", "Japanese", KitchenType.Other, BigDecimal.valueOf(1200), BigDecimal.valueOf(4.0))
        );

        Rating r1 = new Rating(customer.getId(), restaurant.getId(), 4, "Good", customer, restaurant);
        ratingRepository.save(r1);

        List<Rating> ratings = ratingRepository.findByCustomerId(customer.getId());
        assertEquals(1, ratings.size());
        assertEquals("Good", ratings.get(0).getReview());
    }
}