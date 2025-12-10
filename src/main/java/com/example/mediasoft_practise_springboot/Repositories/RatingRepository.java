package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Entities.RatingId;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    //Поиск всех оценок с помощью пагинации
    @NonNull Page<Rating> findAll(
            @NonNull Pageable pageable
    );
    //Поиск оценок по Id ресторана
    List<Rating> findByRestaurantId(Long restaurantId);
    //Поиск оценок по Id посетителя
    List<Rating> findByCustomerId(Long customerId);
}
