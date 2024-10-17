package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.Constant;
import com.example.coffeeservice.controller.exceptionHandler.exception.BrigadeNotFoundException;
import com.example.coffeeservice.entity.dto.BrigadeDTO;
import com.example.coffeeservice.entity.model.Brigade;
import com.example.coffeeservice.entity.model.RoastingInfo;
import com.example.coffeeservice.repository.BrigadeRepository;
import com.example.coffeeservice.repository.RoastingInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrigadeService {

    private final BrigadeRepository brigadeRepository;
    private final RoastingInfoRepository roastingInfoRepository;

    @Transactional
    public BrigadeDTO getBrigadeInfo(UUID brigadeId) {
        Brigade brigade = brigadeRepository.findById(brigadeId)
                .orElseThrow(() -> new BrigadeNotFoundException(Constant.BRIGADE_NOT_FOUND_BY_ID_EXCEPTION_TEXT));
        BrigadeDTO brigadeDTO = new BrigadeDTO()
                .toBuilder()
                .brigadeName(brigade.getBrigadeName())
                .brigadeNumber(brigade.getBrigadeNumber())
                .build();

        // Подсчет потерь при обжарке
        List<RoastingInfo> roastingInfoList = roastingInfoRepository.findByBrigadeBrigadeId(brigadeId);
        Double roastLoss = roastingInfoList.stream()
                .map(RoastingInfo::getLossPercentage)
                .filter(Objects::nonNull)
                .collect(Collectors.averagingDouble(Double::doubleValue));
        brigadeDTO.setRoastLoss(roastLoss);

        // Подсчет потерь по каждой стране
        Map<String, Double> countryLossMap = roastingInfoList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        RoastingInfo::getCountry,
                        Collectors.averagingDouble(RoastingInfo::getLossPercentage)
                ));

        List<BrigadeDTO.CountryInfo> countryInfoList = countryLossMap.entrySet().stream()
                .map(entry -> new BrigadeDTO.CountryInfo(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        brigadeDTO.setCountryInfoList(countryInfoList);
        return brigadeDTO;
    }

    @Transactional
    public List<BrigadeDTO> getAllBrigadesInfo() {
        List<Brigade> brigadeList = brigadeRepository.findAll();
        List<BrigadeDTO> brigadeDTOList = new ArrayList<>();
        brigadeList.forEach(brigade -> {
            BrigadeDTO brigadeDTO = getBrigadeInfo(brigade.getBrigadeId());
            brigadeDTOList.add(brigadeDTO);
        });
        return brigadeDTOList;
    }
}
