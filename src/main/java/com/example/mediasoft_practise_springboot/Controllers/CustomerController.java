package com.example.mediasoft_practise_springboot.Controllers;

import com.example.mediasoft_practise_springboot.DTO.CustomerRequestDTO;
import com.example.mediasoft_practise_springboot.DTO.CustomerResponseDTO;
import com.example.mediasoft_practise_springboot.Services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Customer", description = "Управление посетителями ресторана")
public class CustomerController {
    private  final CustomerService customerService;
    CustomerController(CustomerService customerService){
        this.customerService =customerService;
    }
    @GetMapping
    @Operation(summary = "Получить всех посетителей")
    @ApiResponse(responseCode = "200", description = "Список посетителей")
    public List<CustomerResponseDTO> GetAll(){
        return customerService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить определенного посетителя")
    @ApiResponse(responseCode = "200", description = "Информация о посетителе")
    @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    public CustomerResponseDTO GetCustomer(@Parameter(description = "ID посетителя, который нужно получить", required = true) @PathVariable Long id){
        return customerService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Создать посетителя")
    @ApiResponse(responseCode = "201", description = "Посетитель успешно создан")
    public CustomerResponseDTO CreateCustomer(@RequestBody CustomerRequestDTO customer)
    {
        return customerService.create(customer);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить посетителя")
    @ApiResponse(responseCode = "200", description = "Посетитель успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    public CustomerResponseDTO UpdateCustomer(@Parameter(description = "ID посетителя, которого нужно обновить", required = true) @PathVariable Long id,
                                              @RequestBody CustomerRequestDTO customer)
    {
        return customerService.update(id, customer);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя")
    @ApiResponse(responseCode = "200", description = "Посетитель удален")
    @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    public void DeleteCustomer(@Parameter(description = "ID посетителя, которого нужно удалить", required = true) @PathVariable Long id)
    {
        customerService.remove(id);
    }
}
