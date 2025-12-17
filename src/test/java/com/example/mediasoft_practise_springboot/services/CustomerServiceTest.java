package com.example.mediasoft_practise_springboot.services;


import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Entities.Customer;
import com.example.mediasoft_practise_springboot.Entities.Rating;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import com.example.mediasoft_practise_springboot.Mappers.CustomerMapper;
import com.example.mediasoft_practise_springboot.Repositories.CustomerRepository;
import com.example.mediasoft_practise_springboot.Repositories.RatingRepository;
import com.example.mediasoft_practise_springboot.Services.CustomerService;
import com.example.mediasoft_practise_springboot.Services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private RatingService ratingService;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findAll_shouldReturnList() {
        Customer customer = new Customer();
        CustomerResponseDTO dto = new CustomerResponseDTO(1L,
                "Ivan", 25, "Male");;

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        List<CustomerResponseDTO> result = customerService.findAll();

        assertEquals(1, result.size());
        verify(customerRepository).findAll();
    }

    @Test
    void getById_shouldReturnCustomer() {
        Customer customer = new Customer();
        CustomerResponseDTO dto = new CustomerResponseDTO(1L,
                "Ivan", 25, "Male");;

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        CustomerResponseDTO result = customerService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void create_shouldSaveCustomer() {
        CustomerRequestDTO request = new CustomerRequestDTO("Ivan", 25, GenderEnum.Male);
        Customer customer = new Customer();
        CustomerResponseDTO response = new CustomerResponseDTO(1L,
                "Ivan", 25, "Male");;

        when(customerMapper.toEntity(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(response);

        CustomerResponseDTO result = customerService.create(request);

        assertNotNull(result);
        verify(customerRepository).save(customer);
    }

    @Test
    void update_shouldUpdateCustomer() {
        CustomerRequestDTO request = new CustomerRequestDTO("Ivan", 25, GenderEnum.Male);
        Customer customer = new Customer();
        CustomerResponseDTO response = new CustomerResponseDTO(1L,
                "Ivan", 25, "Male");;

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(response);

        CustomerResponseDTO result = customerService.update(1L, request);

        assertNotNull(result);
        verify(customerMapper).updateEntity(request, customer);
    }

    @Test
    void remove_shouldDeleteCustomerAndRatings() {
        Rating rating = new Rating();
        rating.setRestaurantId(10L);

        when(ratingRepository.findByCustomerId(1L)).thenReturn(List.of(rating));

        customerService.remove(1L);

        verify(ratingRepository).delete(rating);
        verify(ratingService).recalcRestaurantRating(10L);
        verify(customerRepository).deleteById(1L);
    }
}

