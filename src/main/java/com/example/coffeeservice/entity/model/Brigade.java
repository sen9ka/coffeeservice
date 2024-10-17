package com.example.coffeeservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "brigades")
@AllArgsConstructor
@NoArgsConstructor
public class Brigade {

    @Id
    @Column(name = "id")
    @JsonIgnore
    private UUID brigadeId;

    @Column(name = "number")
    private Long brigadeNumber;

    @Column(name = "name")
    private String brigadeName;

}
