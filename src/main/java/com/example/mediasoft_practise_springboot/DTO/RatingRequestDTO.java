package com.example.mediasoft_practise_springboot.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RatingRequestDTO {
    @Schema(description = "ID пользователя, который оставил отзыв", example = "1")
    @NotNull
    Long customerId;
    @Schema(description = "ID ресторана, к которому относится отзыв", example = "2")
    @NotNull
    Long restaurantId;
    @Schema(description = "Оценка ресторана от 1 до 5", example = "4", required = true)
    @NotNull
    @Min(1)
    @Max(5)
    Integer rate;
    @Schema(description = "Текст отзыва, необязательно", example = "Вкусно")
    @Size(max = 200)
    String review;
}
