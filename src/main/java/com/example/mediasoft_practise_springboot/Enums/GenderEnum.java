package com.example.mediasoft_practise_springboot.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderEnum {
    NotSpecified("Не указан"),
    Male("Мужской"),
    Female("Женский");
    private final String name;
    @Override
    public String toString() {
        return name;
    }
}
