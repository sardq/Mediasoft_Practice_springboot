package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void saveAndFindByRating() {
        Restaurant r1 = new Restaurant();
        r1.setName("Pizza House");
        r1.setDescription("Italian");
        r1.setRating(BigDecimal.valueOf(4.5));
        r1.setAverageCheck(BigDecimal.valueOf(1000));
        r1.setKitchenType(KitchenType.Italy);
        restaurantRepository.save(r1);

        Restaurant r2 = new Restaurant();
        r2.setName("Sushi Place");
        r2.setDescription("Japanese");
        r2.setRating(BigDecimal.valueOf(2.8));
        r2.setAverageCheck(BigDecimal.valueOf(1000));
        r2.setKitchenType(KitchenType.Italy);
        restaurantRepository.save(r2);

        List<Restaurant> result = restaurantRepository.searchByRating(BigDecimal.valueOf(4));
        assertEquals(1, result.size());
        assertEquals("Pizza House", result.get(0).getName());
    }
}

