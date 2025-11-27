package com.example.mediasoft_practise_springboot.DTO;

import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CustomerResponseDTO {
    @Schema(description = "ID посетителя", example = "Разубаев Сергей")
    Long id;
    @Schema(description = "Имя посетителя", example = "Разубаев Сергей")
    String name;
    @Schema(description = "Возраст посетителя", example = "25")
    Integer age;
    @Schema(description = "Пол посетителя", example = "Male")
    String gender;
}
