package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Entities.RatingId;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Mappers.RatingMapper;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;
    private final CustomerRepository customerRepository;
    private final RatingMapper mapper;

    public List<RatingResponseDTO> findAll() {
        return ratingRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RatingResponseDTO getById(Long customerId, Long restaurantId) {

        RatingId id = new RatingId(customerId, restaurantId);

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        return mapper.toDTO(rating);
    }

    public RatingResponseDTO create(RatingRequestDTO dto) {
        Rating entity = mapper.toEntity(dto);
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        entity.setCustomer(customer);
        entity.setRestaurant(restaurant);
        Rating saved = ratingRepository.save(entity);
        recalcRestaurantRating(saved.getRestaurantId());
        return mapper.toDTO(saved);
    }

    public RatingResponseDTO update(Long customerId, Long restaurantId, RatingRequestDTO dto) {

        RatingId id = new RatingId(customerId, restaurantId);

        Rating existing = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        mapper.updateEntity(dto, existing);

        Rating saved = ratingRepository.save(existing);
        recalcRestaurantRating(saved.getRestaurantId());

        return mapper.toDTO(saved);
    }

    public void remove(Long customerId, Long restaurantId) {
        RatingId id = new RatingId(customerId, restaurantId);

        Rating rating = ratingRepository.findById(id)
                .orElse(null);

        if (rating != null) {
            ratingRepository.delete(rating);
            recalcRestaurantRating(restaurantId);
        }
    }

    public void recalcRestaurantRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (ratings.isEmpty()) {
            restaurant.setRating(BigDecimal.ZERO);
        } else {
            BigDecimal avg = ratings.stream()
                    .map(r -> BigDecimal.valueOf(r.getRate()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);

            restaurant.setRating(avg);
        }

        restaurantRepository.save(restaurant);
    }
    public Page<RatingResponseDTO> findPaged(Pageable pageable) {
        return ratingRepository.findAll(pageable)
                .map(mapper::toDTO);
    }
}

