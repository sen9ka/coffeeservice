package com.example.coffeeservice.repository;

import com.example.coffeeservice.entity.model.GrainArrivalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrainArrivalRepository extends JpaRepository<GrainArrivalModel, Long> {
}
