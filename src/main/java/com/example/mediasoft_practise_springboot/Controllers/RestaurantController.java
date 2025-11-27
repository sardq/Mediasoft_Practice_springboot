package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.RestaurantRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RestaurantResponseDTO;
import com.example.mediasoft_practise_springboot.Services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/restaurants")
@Tag(name = "Restaurant", description = "Управление ресторанами")
public class RestaurantController {
    private  final RestaurantService restaurantService;
    RestaurantController(RestaurantService restaurantService){
        this.restaurantService =restaurantService;
    }
    @GetMapping
    @Operation(summary = "Получить все рестораны")
    @ApiResponse(responseCode = "200", description = "Список ресторанов")
    public List<RestaurantResponseDTO> GetAll(){
        return restaurantService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить определенный ресторан")
    @ApiResponse(responseCode = "200", description = "Информация о ресторане")
    @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    public RestaurantResponseDTO GetRestaurant(@Parameter(description = "ID ресторана, который нужно получить", required = true) @PathVariable Long id){
        return restaurantService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Создать ресторан")
    @ApiResponse(responseCode = "201", description = "Ресторан успешно создан")
    public RestaurantResponseDTO CreateRestaurant(@Parameter(description = "ID ресторана, который нужно создать", required = true) @RequestBody RestaurantRequestDTO restaurant)
    {
        return restaurantService.create(restaurant);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан")
    @ApiResponse(responseCode = "200", description = "Ресторан успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    public RestaurantResponseDTO UpdateRestaurant(@Parameter(description = "ID ресторана, который нужно обновить", required = true) @PathVariable Long id,
                                              @RequestBody RestaurantRequestDTO restaurant)
    {
        return restaurantService.update(id, restaurant);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан")
    @ApiResponse(responseCode = "200", description = "Ресторан удален")
    @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    public void DeleteRestaurant(@Parameter(description = "ID ресторана, который нужно удалить", required = true) @PathVariable Long id)
    {
        restaurantService.remove(id);
    }
}
