package com.example.mediasoft_practise_springboot.Mappers;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Restaurant;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    Restaurant toEntity(RestaurantRequestDTO dto);
    @Mapping(target = "kitchenType", source = "kitchenType", qualifiedByName = "enumToString")
    RestaurantResponseDTO toDTO(Restaurant entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    void updateEntity(RestaurantRequestDTO dto, @MappingTarget Restaurant entity);
    @Named("enumToString")
    default String enumToString(KitchenType kitchenType) {
        return kitchenType == null ? null : kitchenType.getName();
    }

}

