package com.example.mediasoft_practise_springboot.DTO;

import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class RestaurantRequestDTO {
    @Schema(description = "Название ресторана. Обязательно к заполнению", example = "La Food")
    @NotNull
    String name;
    @Schema(description = "Описание ресторана. Не обязательно к заполнению", example = "Итальянская кухня в центре города")
    String description;
    @Schema(description = "Тип кухни ресторана. Обязательно к заполнению. Доступные значения: Other, Europe, Italy, China", example = "Italy")
    @NotNull
    KitchenType kitchenType;
    @Schema(description = "Средний чек на человека. Обязательно к заполнению, должен быть положительным", example = "850.50")
    @NotNull
    @DecimalMin(value = "0.0",inclusive = false)
    BigDecimal averageCheck;
}
