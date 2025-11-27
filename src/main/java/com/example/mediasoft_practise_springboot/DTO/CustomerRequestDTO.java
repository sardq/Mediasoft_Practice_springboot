package com.example.mediasoft_practise_springboot.DTO;

import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CustomerRequestDTO {
    @Schema(description = "Имя посетителя. Необязательно, можно оставить пустым для анонимного пользователя", example = "Иванов Иван")
    @Size(max = 50)
    String name;
    @Schema(description = "Возраст посетителя. Обязательное поле, должно быть положительным числом", example = "25")
    @NotNull
    @Min(1)
    Integer age;
    @Schema(description = "Пол посетителя. Обязательное поле, значения: Male, Female, NotSpecified", example = "Male")
    @NotNull
    GenderEnum gender;
}
