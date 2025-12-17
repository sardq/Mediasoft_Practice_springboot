package com.example.mediasoft_practise_springboot.services;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Mappers.RestaurantMapper;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import com.example.mediasoft_practise_springboot.Services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void findAll_shouldReturnRestaurants() {
        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO dto = new RestaurantResponseDTO(1L,
                "La Pasta", "Итальянская кухня в центре города",
                "Итальянская",BigDecimal.valueOf(1200.50),BigDecimal.valueOf(4.5));

        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(restaurantMapper.toDTO(restaurant)).thenReturn(dto);

        List<RestaurantResponseDTO> result = restaurantService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_shouldReturnRestaurant() {
        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO dto = new RestaurantResponseDTO(1L,
                "La Pasta", "Итальянская кухня в центре города",
                "Итальянская",BigDecimal.valueOf(1200.50),BigDecimal.valueOf(4.5));

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toDTO(restaurant)).thenReturn(dto);

        RestaurantResponseDTO result = restaurantService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void create_shouldSaveRestaurant() {
        RestaurantRequestDTO request = new RestaurantRequestDTO("La Pasta",
                "Итальянская кухня в центре города", KitchenType.Italy,BigDecimal.valueOf(1200.50) );
        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO response = new RestaurantResponseDTO(1L,
                "La Pasta", "Итальянская кухня в центре города",
                "Итальянская",BigDecimal.valueOf(1200.50),BigDecimal.valueOf(4.5));

        when(restaurantMapper.toEntity(request)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantMapper.toDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.create(request);

        assertNotNull(result);
    }

    @Test
    void update_shouldUpdateRestaurant() {
        RestaurantRequestDTO request = new RestaurantRequestDTO("La Pasta",
                "Итальянская кухня в центре города", KitchenType.Italy,BigDecimal.valueOf(1200.50) );
        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO response = new RestaurantResponseDTO(1L,
                "La Pasta", "Итальянская кухня в центре города",
                "Итальянская",BigDecimal.valueOf(1200.50),BigDecimal.valueOf(4.5));

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantMapper.toDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.update(1L, request);

        assertNotNull(result);
        verify(restaurantMapper).updateEntity(request, restaurant);
    }

    @Test
    void remove_shouldDeleteRestaurantAndRatings() {
        Rating rating = new Rating();

        when(ratingRepository.findByRestaurantId(1L)).thenReturn(List.of(rating));

        restaurantService.remove(1L);

        verify(ratingRepository).delete(rating);
        verify(restaurantRepository).deleteById(1L);
    }
}
