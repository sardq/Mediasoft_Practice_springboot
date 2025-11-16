package com.example.mediasoft_practise_springboot.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KitchenType {
    Other("Другая"),
    Europe("Европейская"),
    Italy("Итальянская"),
    China("Китайская");
    private final String name;
    @Override
    public String toString() {
        return name;
    }
}
