package com.example.mediasoft_practise_springboot.Entities;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private @NonNull String name;
    private String description;
    private @NonNull KitchenType kitchenType;
    private @NonNull BigDecimal averageCheck;
    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;
}
