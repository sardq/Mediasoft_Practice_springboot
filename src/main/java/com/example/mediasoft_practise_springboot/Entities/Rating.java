package com.example.mediasoft_practise_springboot.Entities;
import lombok.*;
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Rating {
    private @NonNull Long customerId;
    private @NonNull Long restaurantId;
    private @NonNull Integer rate;
    private String review;
    }
