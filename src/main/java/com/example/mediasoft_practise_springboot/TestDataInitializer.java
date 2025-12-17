package com.example.mediasoft_practise_springboot;

import com.example.mediasoft_practise_springboot.DTO.*;
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
// Закомментировано для корректной работы интеграционного теста
//        CustomerResponseDTO c1 = customerService.create(
//                new CustomerRequestDTO("Сергей", 21, GenderEnum.Male)
//        );
//
//        CustomerResponseDTO c2 = customerService.create(
//                new CustomerRequestDTO(null, 30, GenderEnum.Female)
//        );
//
//
//        RestaurantResponseDTO r1 = restaurantService.create(
//                new RestaurantRequestDTO("La Pasta", "Итальянская кухня", KitchenType.Italy, BigDecimal.valueOf(823.11))
//        );
//
//        RestaurantResponseDTO r2 = restaurantService.create(
//                new RestaurantRequestDTO("China Town", "Китайская кухня", KitchenType.China, BigDecimal.valueOf(1321.31))
//        );
//
//
//        ratingService.create(new RatingRequestDTO(c1.getId(), r1.getId(), 5, "Отлично!"));
//        ratingService.create(new RatingRequestDTO(c2.getId(), r1.getId(), 3, "Нормально"));
//        ratingService.create(new RatingRequestDTO(c2.getId(), r2.getId(), 3, null));
//
//
//        System.out.println("Все пользователи: " + customerService.findAll());
//        System.out.println("Все рестораны: " + restaurantService.findAll());
//        System.out.println("Все оценки: " + ratingService.findAll());
//
//        customerService.remove(c2.getId());
//        restaurantService.remove(r2.getId());
//
//        System.out.println("После удаления:");
//        System.out.println("Все пользователи: " + customerService.findAll());
//        System.out.println("Все рестораны: " + restaurantService.findAll());
//        System.out.println("Все оценки: " + ratingService.findAll());
    }
}
