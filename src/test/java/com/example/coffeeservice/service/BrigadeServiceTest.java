package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.Constant;
import com.example.coffeeservice.controller.exceptionHandler.exception.BrigadeNotFoundException;
import com.example.coffeeservice.entity.dto.BrigadeDTO;
import com.example.coffeeservice.entity.model.Brigade;
import com.example.coffeeservice.entity.model.RoastingInfo;
import com.example.coffeeservice.repository.BrigadeRepository;
import com.example.coffeeservice.repository.RoastingInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrigadeServiceTest {

    @Mock
    private BrigadeRepository brigadeRepository;

    @Mock
    private RoastingInfoRepository roastingInfoRepository;

    private BrigadeService brigadeService;

    @BeforeEach
    void setUp() {
        brigadeService = new BrigadeService(brigadeRepository, roastingInfoRepository);
    }

    @Test
    void getBrigadeInfo_whenBrigadeExists_shouldReturnBrigadeDTO() {
        UUID brigadeId = UUID.randomUUID();
        Brigade brigade = new Brigade();
        brigade.setBrigadeId(brigadeId);
        brigade.setBrigadeName("Brigade 1");
        brigade.setBrigadeNumber(1L);

        List<RoastingInfo> roastingInfos = Arrays.asList(
                new RoastingInfo(null, "Type A", "Country A", 100.0, brigade, 10.0),
                new RoastingInfo(null, "Type B", "Country B", 150.0, brigade, 20.0),
                new RoastingInfo(null, "Type C", "Country A", 200.0, brigade, 0.0)
        );

        when(brigadeRepository.findById(brigadeId)).thenReturn(Optional.of(brigade));
        when(roastingInfoRepository.findByBrigadeBrigadeId(brigadeId)).thenReturn(roastingInfos);

        BrigadeDTO result = brigadeService.getBrigadeInfo(brigadeId);

        assertNotNull(result);
        assertEquals("Brigade 1", result.getBrigadeName());
        assertEquals(1L, result.getBrigadeNumber());
        assertEquals(10.0, result.getRoastLoss());
        assertEquals(2, result.getCountryInfoList().size());
    }

    @Test
    void getBrigadeInfo_whenBrigadeDoesNotExist_shouldThrowBrigadeNotFoundException() {
        UUID brigadeId = UUID.randomUUID();
        when(brigadeRepository.findById(brigadeId)).thenReturn(Optional.empty());

        BrigadeNotFoundException exception = assertThrows(BrigadeNotFoundException.class, () -> {
            brigadeService.getBrigadeInfo(brigadeId);
        });

        assertEquals(Constant.BRIGADE_NOT_FOUND_BY_ID_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    void getAllBrigadesInfo_shouldReturnListOfBrigadeDTOs() {
        Brigade brigade1 = new Brigade();
        brigade1.setBrigadeId(UUID.randomUUID());
        brigade1.setBrigadeName("Brigade 1");
        brigade1.setBrigadeNumber(1L);

        Brigade brigade2 = new Brigade();
        brigade2.setBrigadeId(UUID.randomUUID());
        brigade2.setBrigadeName("Brigade 2");
        brigade2.setBrigadeNumber(2L);

        when(brigadeRepository.findAll()).thenReturn(Arrays.asList(brigade1, brigade2));
        when(roastingInfoRepository.findByBrigadeBrigadeId(brigade1.getBrigadeId())).thenReturn(Collections.emptyList());
        when(roastingInfoRepository.findByBrigadeBrigadeId(brigade2.getBrigadeId())).thenReturn(Collections.emptyList());
        when(brigadeRepository.findById(brigade1.getBrigadeId())).thenReturn(Optional.of(brigade1));
        when(brigadeRepository.findById(brigade2.getBrigadeId())).thenReturn(Optional.of(brigade2));

        List<BrigadeDTO> result = brigadeService.getAllBrigadesInfo();

        assertEquals(2, result.size());
        assertEquals("Brigade 1", result.get(0).getBrigadeName());
        assertEquals("Brigade 2", result.get(1).getBrigadeName());
    }
}