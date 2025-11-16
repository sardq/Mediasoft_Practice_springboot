package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class RatingService {
    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    public RatingService(RestaurantRepository restaurantRepository,RatingRepository ratingRepository) {
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
    }

    public Rating save(Rating rating){
        ratingRepository.save(rating);
        recalcRestaurantRating(rating.getRestaurantId());
        return rating;
    }
    public void remove(Long customerId, Long restaurantId){
        Rating rating = ratingRepository.findById(customerId,restaurantId);
        if (rating == null) return;
        ratingRepository.remove(customerId, restaurantId);
        recalcRestaurantRating(rating.getRestaurantId());
    }
    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }

    public void recalcRestaurantRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) return;
        if (ratings.isEmpty()) {
            restaurant.setRating(BigDecimal.ZERO);
            restaurantRepository.save(restaurant);
            return;
        }
        BigDecimal avg = ratings.stream()
                .map(r -> BigDecimal.valueOf(r.getRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(ratings.size()),
                        2, RoundingMode.HALF_UP
                );

        restaurant.setRating(avg);
        restaurantRepository.save(restaurant);
    }
}
