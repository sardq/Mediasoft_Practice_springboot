package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Services.RatingService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAll_shouldReturn200() throws Exception {
        RatingResponseDTO dto =
                new RatingResponseDTO("1L", "2L", 5, "Отлично");

        when(ratingService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate").value(5));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        RatingResponseDTO dto =
                new RatingResponseDTO("1L", "2L", 4, "Хорошо");

        when(ratingService.getById(1L, 2L)).thenReturn(dto);

        mockMvc.perform(get("/api/reviews/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(4));
    }

    @Test
    void create_shouldReturn200() throws Exception {
        RatingResponseDTO response =
                new RatingResponseDTO("1L", "2L", 5, "Отлично");

        when(ratingService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": 1,
                                  "restaurantId": 2,
                                  "rate": 5,
                                  "review": "Отлично"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(5));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        RatingResponseDTO response =
                new RatingResponseDTO("1L", "2L", 3, "Нормально");

        when(ratingService.update(eq(1L), eq(2L), any())).thenReturn(response);

        mockMvc.perform(put("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": 1,
                                  "restaurantId": 2,
                                  "rate": 3,
                                  "review": "Нормально"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(3));
    }

    @Test
    void delete_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/reviews/1/2"))
                .andExpect(status().isOk());
    }
    @Test
    void createDuplicateRating_shouldReturn404() throws Exception {
        RatingRequestDTO request = new RatingRequestDTO(1L, 2L, 5, "Отлично");

        when(ratingService.create(any())).thenThrow(new RuntimeException("Rating already exists"));

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rating already exists"));
    }

}

