package com.example.coffeeservice.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GrainArrivalMessage {

    private final Float weight = 60.0F;

    private String country;

    private Float robustaPercent;

    private Float arabicaPercent;

    private String grainVariety;

}
