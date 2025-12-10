package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.RatingRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.RatingResponseDTO;
import com.example.mediasoft_practise_springboot.Enums.SortDirection;
import com.example.mediasoft_practise_springboot.Services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Rating", description = "Управление оценками ресторанов")
public class RatingController {
    private  final RatingService ratingService;
    RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }
    @GetMapping
    @Operation(summary = "Получить все оценки")
    @ApiResponse(responseCode = "200", description = "Список оценок")
    public List<RatingResponseDTO> GetAll(){
        return ratingService.findAll();
    }
    @GetMapping("/{customerId}/{restaurantId}")
    @Operation(summary = "Получить определенную оценку")
    @ApiResponse(responseCode = "200", description = "Информация об оценке")
    @ApiResponse(responseCode = "404", description = "Оценка не найдена")
    public RatingResponseDTO GetRating(@Parameter(description = "ID посетителя, оценку которого надо получить", required = true)
                                           @PathVariable Long customerId,
                                            @Parameter(description = "ID ресторана, оценку которого надо получить", required = true)
                                            @PathVariable Long restaurantId){
        return ratingService.getById(customerId,restaurantId);
    }

    @PostMapping
    @Operation(summary = "Создать оценку")
    @ApiResponse(responseCode = "201", description = "Оценка успешно создана")
    public RatingResponseDTO CreateRating(@RequestBody RatingRequestDTO rating)
    {
        return ratingService.create(rating);
    }
    @PutMapping
    @Operation(summary = "Обновить оценку")
    @ApiResponse(responseCode = "200", description = "Оценка успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Оценка не найдена")
    public RatingResponseDTO UpdateRating(@RequestBody RatingRequestDTO rating)
    {
        return ratingService.update(rating.getCustomerId(), rating.getRestaurantId(), rating);
    }
    @DeleteMapping("/{customerId}/{restaurantId}")
    @Operation(summary = "Удалить оценку")
    @ApiResponse(responseCode = "200", description = "Оценка удалена")
    @ApiResponse(responseCode = "404", description = "Оценка не найдена")
    public void DeleteRating(@Parameter(description = "ID посетителя, оценку которого надо удалить", required = true)
                                 @PathVariable Long customerId,
                             @Parameter(description = "ID ресторана, оценку которого надо удалить", required = true) @PathVariable Long restaurantId)
    {
        ratingService.remove(customerId,restaurantId);
    }
    @GetMapping("/paged")
    @Operation(summary = "Получить отзывы постранично с сортировкой")
    @ApiResponse(responseCode = "200", description = "Страница отзывов")
    public Page<RatingResponseDTO> getPaged(
            @Parameter(description = "Направление сортировки: ASC - по возрастанию, DESC - по убыванию") @RequestParam(defaultValue = "DESC") SortDirection direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Sort sort = direction == SortDirection.ASC ?
                Sort.by("rate").ascending() :
                Sort.by("rate").descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ratingService.findPaged(pageable);
    }

}
