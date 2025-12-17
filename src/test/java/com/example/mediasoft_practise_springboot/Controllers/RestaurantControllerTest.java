package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Enums.KitchenType;
import com.example.mediasoft_practise_springboot.Services.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAll_shouldReturn200() throws Exception {
        RestaurantResponseDTO dto =
                new RestaurantResponseDTO(1L, "Pizza House", "Italian", null, null, BigDecimal.valueOf(4.5));

        when(restaurantService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza House"));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        RestaurantResponseDTO dto =
                new RestaurantResponseDTO(1L, "Pizza House", "Italian", null, null, BigDecimal.valueOf(4.5));

        when(restaurantService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void create_shouldReturn200() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO(
                "Pizza House", "Italian", KitchenType.Italy, BigDecimal.valueOf(1000));

        RestaurantResponseDTO response = new RestaurantResponseDTO(
                1L, "Pizza House", "Italian", null, null, BigDecimal.ZERO);

        when(restaurantService.create(any(RestaurantRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pizza House"));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        RestaurantResponseDTO response =
                new RestaurantResponseDTO(1L, "Pizza House Updated", "Italian", null, null, BigDecimal.ZERO);

        when(restaurantService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Pizza House Updated"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza House Updated"));
    }

    @Test
    void delete_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());
    }
}

