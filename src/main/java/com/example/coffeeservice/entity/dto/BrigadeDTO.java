package com.example.coffeeservice.entity.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BrigadeDTO {

    private Long brigadeNumber;
    private String brigadeName;
    private Double roastLoss;
    private List<CountryInfo> countryInfoList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountryInfo {
        private String countryName;
        private Double countryLoss;

    }



}
