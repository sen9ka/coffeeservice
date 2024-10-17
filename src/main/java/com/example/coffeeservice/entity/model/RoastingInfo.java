package com.example.coffeeservice.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "roasting_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoastingInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roastingInfoId;

    @Column(name = "type")
    private String type;

    @Column(name = "country")
    private String country;

    @Column(name = "weight")
    private Double weight;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brigade_id", referencedColumnName = "id")
    private Brigade brigade;

    @Column(name = "loss_percentage")
    private Double lossPercentage;

}
