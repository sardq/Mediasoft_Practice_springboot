package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    public CustomerService(CustomerRepository repository,
                           RatingRepository ratingRepository,
                           RatingService ratingService) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
    }

    public Customer save(Customer customer){
        return repository.save(customer);
    }
    public void remove(Long id){
        List<Rating> ratings = ratingRepository.findByCustomerId(id);

        for (Rating r : ratings) {
            ratingRepository.remove(r.getCustomerId(), r.getRestaurantId());
            ratingService.recalcRestaurantRating(r.getRestaurantId());
        }
        repository.remove(id);
    }
    public List<Customer> findAll(){
        return repository.findAll();
    }
}
