package com.example.coffeeservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "grain_arrival")
public class GrainArrivalModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long grainArrivalId;

    @Column(name = "country")
    private String country;

    @Column(name = "robusta_percent")
    private Double robustaPercent;

    @Column(name = "arabica_percent")
    private Double arabicaPercent;

    @Column(name = "grain_variety")
    private String grainVariety;

    @Column(name = "weight")
    private Double weight;

}
