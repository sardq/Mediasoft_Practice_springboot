package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Mappers.RestaurantMapper;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RatingRepository ratingRepository;
    private final RestaurantMapper mapper;

    public List<RestaurantResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RestaurantResponseDTO getById(Long id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return mapper.toDTO(restaurant);
    }

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        Restaurant existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        mapper.updateEntity(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    public void remove(Long id) {
        ratingRepository.findByRestaurantId(id)
                .forEach(ratingRepository::delete);
        repository.deleteById(id);
    }

    public List<RestaurantResponseDTO> findByMinRatingQuery(BigDecimal rating) {
        return repository.searchByRating(rating)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

