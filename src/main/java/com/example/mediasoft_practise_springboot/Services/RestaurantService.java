package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Mappers.RestaurantMapper;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RatingRepository ratingRepository;
    private final RestaurantMapper mapper;

    public RestaurantService(RestaurantRepository repository, RatingRepository ratingRepository, RestaurantMapper mapper) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
    }
    public List<RestaurantResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RestaurantResponseDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id));
    }

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant entity = mapper.toEntity(dto);
        Restaurant saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        Restaurant existing = repository.findById(id);
        mapper.updateEntity(dto, existing);
        Restaurant saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    public void remove(Long id) {
        ratingRepository.removeByRestaurantId(id);
        repository.remove(id);
    }
    //для инициализации и тестирования
    public Restaurant createRaw(Restaurant restaurant) {
        return repository.save(restaurant);
    }
    public List<Restaurant> findAllEntities() {
        return repository.findAll();
    }
}
