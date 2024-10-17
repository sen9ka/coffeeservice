package com.example.coffeeservice.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "grain_warehouse")
public class GrainWarehouse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grainWarehouseId;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private String type;

    @Column(name = "weight")
    private Double weight;

}
