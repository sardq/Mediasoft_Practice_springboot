package com.example.mediasoft_practise_springboot.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.math.BigDecimal;
@Value
public class RestaurantResponseDTO {
    @Schema(description = "Уникальный идентификатор ресторана", example = "1")
    Long id;

    @Schema(description = "Название ресторана", example = "La Pasta")
    String name;

    @Schema(description = "Описание ресторана, необязательно", example = "Итальянская кухня в центре города")
    String description;

    @Schema(description = "Тип кухни ресторана в виде строки", example = "Итальянская")
    String kitchenType;

    @Schema(description = "Средний чек на человека", example = "1200.50")
    BigDecimal averageCheck;

    @Schema(description = "Средняя оценка пользователей", example = "4.5")
    BigDecimal rating;
}
