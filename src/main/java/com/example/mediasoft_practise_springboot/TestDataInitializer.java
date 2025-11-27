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

        Customer c1 = customerService.createRaw(
                Customer.builder()
                        .name("Сергей")
                        .age(21)
                        .gender(GenderEnum.Male)
                        .build()
        );

        Customer c2 = customerService.createRaw(
                Customer.builder()
                        .age(30)
                        .gender(GenderEnum.Female)
                        .build()
        );

        Restaurant r1 = restaurantService.createRaw(
                Restaurant.builder()
                        .name("La Pasta")
                        .description("Итальянская кухня")
                        .kitchenType(KitchenType.Italy)
                        .averageCheck(BigDecimal.valueOf(823.11))
                        .build()
        );

        Restaurant r2 = restaurantService.createRaw(
                Restaurant.builder()
                        .name("China Town")
                        .description("Китайская кухня")
                        .kitchenType(KitchenType.China)
                        .averageCheck(BigDecimal.valueOf(1321.31))
                        .build()
        );

        ratingService.createRaw(
                new Rating(c1.getId(), r1.getId(), 5, "Отлично!")
        );

        Rating rating2 = ratingService.createRaw(
                new Rating(c2.getId(), r1.getId(), 3, "Нормально")
        );

        ratingService.createRaw(
                new Rating(c2.getId(), r2.getId(), 3, null)
        );

        System.out.println("Все пользователи: " + customerService.findAllEntities());
        System.out.println("Все рестораны: " + restaurantService.findAllEntities());
        System.out.println("Все оценки: " + ratingService.findAllEntities());

        customerService.remove(c2.getId());
        restaurantService.remove(r2.getId());

        System.out.println("После удаления:");
        System.out.println("Все пользователи: " + customerService.findAllEntities());
        System.out.println("Все рестораны: " + restaurantService.findAllEntities());
        System.out.println("Все оценки: " + ratingService.findAllEntities());
    }
}
