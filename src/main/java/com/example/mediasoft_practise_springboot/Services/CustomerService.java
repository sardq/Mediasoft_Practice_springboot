package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Mappers.CustomerMapper;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository,
                           RatingRepository ratingRepository,
                           RatingService ratingService, CustomerMapper mapper) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
        this.mapper = mapper;
    }
    public List<CustomerResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
    public CustomerResponseDTO getById(Long id) {
        Customer customer = repository.findById(id);
        return mapper.toDTO(customer);
    }
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer entity = mapper.toEntity(dto);
        Customer saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer existing = repository.findById(id);
        mapper.updateEntity(dto, existing);
        Customer saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    public void remove(Long id) {
        List<Rating> ratings = ratingRepository.findByCustomerId(id);

        for (Rating r : ratings) {
            ratingRepository.remove(r.getCustomerId(), r.getRestaurantId());
            ratingService.recalcRestaurantRating(r.getRestaurantId());
        }

        repository.remove(id);
    }
    //для инициализации и тестирования
    public Customer createRaw(Customer customer) {
        return repository.save(customer);
    }
    public List<Customer> findAllEntities() {
        return repository.findAll();
    }
}
