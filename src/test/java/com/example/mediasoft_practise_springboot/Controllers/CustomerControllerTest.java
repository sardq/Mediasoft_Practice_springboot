package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Enums.GenderEnum;
import com.example.mediasoft_practise_springboot.Services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAll_shouldReturn200() throws Exception {
        CustomerResponseDTO dto =
                new CustomerResponseDTO(1L, "Ivan", 25, "Male");

        when(customerService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        CustomerResponseDTO dto =
                new CustomerResponseDTO(1L, "Ivan", 25, "Male");

        when(customerService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));
    }

    @Test
    void create_shouldReturn200() throws Exception {
        CustomerRequestDTO request =
                new CustomerRequestDTO("Ivan", 25, GenderEnum.Male);

        CustomerResponseDTO response =
                new CustomerResponseDTO(1L, "Ivan", 25, "Male");

        when(customerService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Ivan",
                                  "age": 25,
                                  "gender": "Male"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        CustomerResponseDTO response =
                new CustomerResponseDTO(1L, "Ivan", 26, "Male");

        when(customerService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Ivan",
                                  "age": 26,
                                  "gender": "Male"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(26));
    }

    @Test
    void delete_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteNonExistingUser_shouldReturn404() throws Exception {
        doThrow(new RuntimeException("Customer not found"))
                .when(customerService).remove(999L);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }
    @Test
    void createInvalidCustomer_shouldReturn400() throws Exception {
        CustomerRequestDTO request = new CustomerRequestDTO("Ivan", 0, GenderEnum.Male);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}

