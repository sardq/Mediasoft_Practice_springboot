package com.example.mediasoft_practise_springboot.Entities;

import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KitchenType kitchenType;

    @Column(nullable = false)
    private BigDecimal averageCheck;

    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;
}
