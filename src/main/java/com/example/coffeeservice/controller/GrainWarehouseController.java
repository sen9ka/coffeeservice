package com.example.coffeeservice.controller;

import com.example.coffeeservice.entity.model.GrainWarehouse;
import com.example.coffeeservice.service.GrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@Tag(name = "Контроллер для получение информации об остатках")
public class GrainWarehouseController {

    private final GrainService grainService;

    @GetMapping
    @Operation(summary = "Получение информации о количестве остатков по каждому сорту и стране")
    public ResponseEntity<List<GrainWarehouse>> getByFilter(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String type
    ) {
        return new ResponseEntity<>(grainService.getFilteredGrainWarehouseInfo(Optional.ofNullable(country), Optional.ofNullable(type)), HttpStatus.OK);
    }

}
