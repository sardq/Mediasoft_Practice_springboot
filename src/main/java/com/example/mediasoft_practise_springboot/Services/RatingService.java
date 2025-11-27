package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Mappers.RatingMapper;
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
    private final RatingMapper mapper;

    public RatingService(RestaurantRepository restaurantRepository, RatingRepository ratingRepository, RatingMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
    }

    public List<RatingResponseDTO> findAll() {
        return ratingRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RatingResponseDTO getById(Long customerId, Long restaurantId) {
        Rating rating = ratingRepository.findById(customerId, restaurantId);
        return mapper.toDTO(rating);
    }

    public RatingResponseDTO create(RatingRequestDTO dto) {
        Rating entity = mapper.toEntity(dto);
        ratingRepository.save(entity);
        recalcRestaurantRating(entity.getRestaurantId());
        return mapper.toDTO(entity);
    }

    public RatingResponseDTO update(Long customerId,
                                    Long restaurantId,
                                    RatingRequestDTO dto) {

        Rating existing = ratingRepository.findById(customerId, restaurantId);
        mapper.updateEntity(dto, existing);

        ratingRepository.save(existing);
        recalcRestaurantRating(existing.getRestaurantId());

        return mapper.toDTO(existing);
    }

    public void remove(Long customerId, Long restaurantId) {
        Rating rating = ratingRepository.findById(customerId, restaurantId);
        if (rating == null) return;

        ratingRepository.remove(customerId, restaurantId);
        recalcRestaurantRating(rating.getRestaurantId());
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
    //для инициализации и тестирования
    public Rating createRaw(Rating rating) {
        ratingRepository.save(rating);
        recalcRestaurantRating(rating.getRestaurantId());
        return rating;
    }
    public List<Rating> findAllEntities() {
        return ratingRepository.findAll();
    }
}
