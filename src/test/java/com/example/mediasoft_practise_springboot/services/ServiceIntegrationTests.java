package com.example.mediasoft_practise_springboot.services;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import com.example.mediasoft_practise_springboot.Repositories.BaseIntegrationTest;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Services.CustomerService;
import com.example.mediasoft_practise_springboot.Services.RatingService;
import com.example.mediasoft_practise_springboot.Services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceIntegrationTests extends BaseIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RatingRepository ratingRepository;
    @BeforeEach
    void setup() {
        ratingRepository.deleteAll();
        restaurantRepository.deleteAll();
        customerRepository.deleteAll();
    }
    @Test
    void customerCrudTest() {
        CustomerRequestDTO request = new CustomerRequestDTO("Ivan", 25, GenderEnum.Male);
        CustomerResponseDTO created = customerService.create(request);
        assertNotNull(created.getId());

        CustomerResponseDTO fetched = customerService.getById(created.getId());
        assertEquals("Ivan", fetched.getName());

        request = new CustomerRequestDTO("Ivan Updated", 26, GenderEnum.Male);
        CustomerResponseDTO updated = customerService.update(created.getId(), request);
        assertEquals(26, updated.getAge());

        customerService.remove(created.getId());
        assertThrows(RuntimeException.class, () -> customerService.getById(created.getId()));
    }

    @Test
    void restaurantCrudTest() {
        RestaurantRequestDTO request = new RestaurantRequestDTO("La Pasta", "Italian", KitchenType.Italy, BigDecimal.valueOf(1000));
        RestaurantResponseDTO created = restaurantService.create(request);
        assertNotNull(created.getId());

        RestaurantResponseDTO fetched = restaurantService.getById(created.getId());
        assertEquals("La Pasta", fetched.getName());

        request = new RestaurantRequestDTO("La Pasta Updated", "Italian Updated", KitchenType.Italy, BigDecimal.valueOf(1200));
        RestaurantResponseDTO updated = restaurantService.update(created.getId(), request);
        assertEquals("La Pasta Updated", updated.getName());

        restaurantService.remove(created.getId());
        assertThrows(RuntimeException.class, () -> restaurantService.getById(created.getId()));
    }

    @Test
    void ratingCreateAndRecalcTest() {
        Customer customer = customerRepository.save(new Customer(null, "Ivan", 25, GenderEnum.Male));
        Restaurant restaurant = restaurantRepository.save(new Restaurant(null, "Pizza House", "Italian", KitchenType.Italy, BigDecimal.valueOf(1000), BigDecimal.ZERO));

        RatingRequestDTO ratingRequest = new RatingRequestDTO(customer.getId(), restaurant.getId(), 5, "Excellent");
        RatingResponseDTO rating = ratingService.create(ratingRequest);

        assertEquals(5, rating.getRate());

        RestaurantResponseDTO updatedRestaurant = restaurantService.getById(restaurant.getId());
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP), updatedRestaurant.getRating());

        Customer customer2 = customerRepository.save(new Customer(null, "Maria", 30, GenderEnum.Female));
        ratingService.create(new RatingRequestDTO(customer2.getId(), restaurant.getId(), 3, "Good"));

        updatedRestaurant = restaurantService.getById(restaurant.getId());
        assertEquals(BigDecimal.valueOf(4.00).setScale(2, RoundingMode.HALF_UP), updatedRestaurant.getRating());

        ratingService.remove(customer.getId(), restaurant.getId());
        updatedRestaurant = restaurantService.getById(restaurant.getId());
        assertEquals(BigDecimal.valueOf(3.00).setScale(2, RoundingMode.HALF_UP), updatedRestaurant.getRating());
    }

    @Test
    void ratingPaginationTest() {
        Customer customer = customerRepository.save(new Customer(null, "Ivan", 25, GenderEnum.Male));
        Restaurant restaurant = restaurantRepository.save(new Restaurant(null, "Sushi Place", "Japanese", KitchenType.Other, BigDecimal.valueOf(1200), BigDecimal.ZERO));

        ratingService.create(new RatingRequestDTO(customer.getId(), restaurant.getId(), 5, "Excellent"));

        var page = ratingService.findPaged(PageRequest.of(0, 10));
        assertEquals(1, page.getTotalElements());
        assertEquals(5, page.getContent().get(0).getRate());
    }
}
