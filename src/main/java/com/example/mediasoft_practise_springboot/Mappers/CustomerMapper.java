package com.example.mediasoft_practise_springboot.Mappers;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerRequestDTO dto);
    @Mapping(target = "gender", source = "gender", qualifiedByName = "enumToString")
    CustomerResponseDTO toDTO(Customer customer);
    @Mapping(target = "id", ignore = true)
    void updateEntity(CustomerRequestDTO dto, @MappingTarget Customer entity);
    @Named("enumToString")
    default String enumToString(GenderEnum gender) {
        return gender == null ? null : gender.getName();
    }

}
