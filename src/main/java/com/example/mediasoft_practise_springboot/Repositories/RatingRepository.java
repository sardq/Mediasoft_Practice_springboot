package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();
    public List<Rating> findAll(){
        return new ArrayList<>(ratings);
    }
    public Rating save(Rating rating){
        ratings.removeIf(r ->
                r.getCustomerId().equals(rating.getCustomerId()) &&
                        r.getRestaurantId().equals(rating.getRestaurantId())
        );
        ratings.add(rating);
        return rating;
    }
    public void remove(Long customerId, Long restaurantId){
        ratings.removeIf(r -> r.getRestaurantId().equals(restaurantId) && r.getCustomerId().equals(customerId));
    }
    public List<Rating> findByRestaurantId(Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .toList();
    }
    public Rating findById(Long customerId, Long restaurantId) {
        return ratings.stream()
                .filter(r ->
                        r.getCustomerId().equals(customerId) &&
                                r.getRestaurantId().equals(restaurantId))
                .findFirst()
                .orElse(null);
    }
    public List<Rating> findByCustomerId(Long customerId) {
        return ratings.stream()
                .filter(r -> r.getCustomerId().equals(customerId))
                .toList();
    }

    public void removeByRestaurantId(Long restaurantId) {
        ratings.removeIf(r -> r.getRestaurantId().equals(restaurantId));
    }

    public void removeByCustomerId(Long customerId) {
        ratings.removeIf(r -> r.getCustomerId().equals(customerId));
    }
}
