package com.example.mediasoft_practise_springboot.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class RatingResponseDTO {
    @Schema(description = "ID пользователя, который оставил отзыв", example = "Разубаев Сергей")
    String customerId;
    @Schema(description = "ID ресторана, к которому относится отзыв", example = "La Pasta")
    String restaurantId;
    @Schema(description = "Оценка ресторана от 1 до 5", example = "4")
    Integer rate;
    @Schema(description = "Текст отзыва", example = "Вкусно")
    String review;
}
