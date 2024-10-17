package com.example.coffeeservice.repository;

import com.example.coffeeservice.entity.model.GrainWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrainWarehouseRepository extends JpaRepository<GrainWarehouse, Long> {
    List<GrainWarehouse> findByCountryAndType(String country, String type);
}
