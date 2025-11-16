package com.example.mediasoft_practise_springboot.Repositories;

import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);
    public List<Restaurant> findAll(){
        return new ArrayList<>(restaurants);
    }
    public Restaurant save(Restaurant restaurant){
        if (restaurant.getId() == null) {
            restaurant.setId(idSequence.getAndDecrement());
            restaurants.add(restaurant);
        }  else {
        restaurants.removeIf(r -> r.getId().equals(restaurant.getId()));
        restaurants.add(restaurant);
        }
        return restaurant;
    }
    public void remove(Long id){
        restaurants.removeIf(c -> c.getId().equals(id));
    }
    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
