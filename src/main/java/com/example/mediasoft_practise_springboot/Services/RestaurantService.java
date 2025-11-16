package com.example.mediasoft_practise_springboot.Services;

import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RatingRepository ratingRepository;

    public RestaurantService(RestaurantRepository repository, RatingRepository ratingRepository) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
    }
    public Restaurant save(Restaurant restaurant){
        repository.save(restaurant);
        return  restaurant;
    }
    public void remove(Long id){
        ratingRepository.removeByRestaurantId(id);
        repository.remove(id);
    }
    public List<Restaurant> findAll(){
        return repository.findAll();
    }
}
