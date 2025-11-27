package com.example.mediasoft_practise_springboot.Mappers;

import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    Rating toEntity(RatingRequestDTO dto);

    RatingResponseDTO toDTO(Rating entity);

    void updateEntity(RatingRequestDTO dto, @MappingTarget Rating entity);
}

