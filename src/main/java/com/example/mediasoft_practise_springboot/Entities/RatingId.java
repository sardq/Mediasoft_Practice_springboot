package com.example.mediasoft_practise_springboot.Entities;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingId implements Serializable {
    private Long customerId;
    private Long restaurantId;
}