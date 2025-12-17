package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    //Поиск с помощью @Query и JPQL запроса ресторанов с рейтингом больше чем задано
    @Query(
            "SELECT r FROM Restaurant r WHERE r.rating > :rating"
    )
    List<Restaurant> searchByRating(BigDecimal rating);
}
