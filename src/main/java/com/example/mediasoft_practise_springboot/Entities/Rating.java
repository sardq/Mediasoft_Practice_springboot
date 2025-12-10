package com.example.mediasoft_practise_springboot.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RatingId.class)
@Table(name = "ratings")
public class Rating {

    @Id
    private Long customerId;

    @Id
    private Long restaurantId;

    @Column(nullable = false)
    private Integer rate;

    private String review;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant;
}
