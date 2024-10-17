package com.example.coffeeservice.controller;

import com.example.coffeeservice.entity.dto.BrigadeDTO;
import com.example.coffeeservice.entity.model.GrainWarehouse;
import com.example.coffeeservice.service.BrigadeService;
import com.example.coffeeservice.service.GrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brigade")
@Tag(name = "Контроллер для работы с бригадами")
public class BrigadeController {

    private final BrigadeService brigadeService;

    @GetMapping
    @Operation(summary = "Получение информации о бригаде по UUID")
    public ResponseEntity<BrigadeDTO> getBrigadeInfo(
            @RequestParam(required = false) UUID brigadeId
    ) {
        return new ResponseEntity<>(brigadeService.getBrigadeInfo(brigadeId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение информации обо всех бригадах")
    public ResponseEntity<List<BrigadeDTO>> getAllBrigadesInfo() {
        return new ResponseEntity<>(brigadeService.getAllBrigadesInfo(), HttpStatus.OK);
    }

}
