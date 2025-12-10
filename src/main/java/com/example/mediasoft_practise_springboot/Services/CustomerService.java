package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Mappers.CustomerMapper;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final CustomerMapper mapper;

    public List<CustomerResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CustomerResponseDTO getById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapper.toDTO(customer);
    }

    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        mapper.updateEntity(dto, existing);

        return mapper.toDTO(repository.save(existing));
    }

    public void remove(Long id) {
        ratingRepository.findByCustomerId(id)
                .forEach(r -> {
                    ratingRepository.delete(r);
                    ratingService.recalcRestaurantRating(r.getRestaurantId());
                });

        repository.deleteById(id);
    }
}

