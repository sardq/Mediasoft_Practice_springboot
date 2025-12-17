package com.example.mediasoft_practise_springboot.services;

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
import com.example.mediasoft_practise_springboot.Services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void findAll_shouldReturnRatings() {
        Rating rating = new Rating();
        RatingResponseDTO dto = new RatingResponseDTO("1L", "1L", 5, "Вкусная паста");

        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        when(ratingMapper.toDTO(rating)).thenReturn(dto);

        List<RatingResponseDTO> result = ratingService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_shouldReturnRating() {
        Rating rating = new Rating();
        RatingId id = new RatingId(1L, 2L);
        RatingResponseDTO dto = new RatingResponseDTO("1L", "1L", 5, "Вкусная паста");
        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(ratingMapper.toDTO(rating)).thenReturn(dto);

        RatingResponseDTO result = ratingService.getById(1L, 2L);

        assertNotNull(result);
    }

    @Test
    void create_shouldSaveRatingAndRecalc() {
        RatingRequestDTO request = new RatingRequestDTO(1L, 2L, 5, "Вкусная паста");
        RatingResponseDTO response = new RatingResponseDTO("1L", "2L", 5, "Вкусная паста");

        Rating rating = new Rating();
        rating.setRestaurantId(2L);
        rating.setCustomerId(1L);
        rating.setRate(5);
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant();

        when(ratingMapper.toEntity(request)).thenReturn(rating);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingMapper.toDTO(rating)).thenReturn(response);
        when(ratingRepository.findByRestaurantId(2L)).thenReturn(List.of(rating));

        RatingResponseDTO result = ratingService.create(request);

        assertNotNull(result);
    }

    @Test
    void remove_shouldDeleteRatingAndRecalc() {
        Rating rating = new Rating();
        RatingId id = new RatingId(1L, 2L);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(ratingRepository.findByRestaurantId(2L)).thenReturn(List.of());

        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));

        ratingService.remove(1L, 2L);

        verify(ratingRepository).delete(rating);
    }
}

