package com.example.mediasoft_practise_springboot.Entities;

import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import lombok.*;
    @Data
    @Builder
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
public class Customer {
        private Long id;
        private String name;
        private @NonNull Integer age;
        private @NonNull GenderEnum gender;
    }
