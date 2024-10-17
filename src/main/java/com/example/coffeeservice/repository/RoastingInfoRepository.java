package com.example.coffeeservice.repository;

import com.example.coffeeservice.entity.model.RoastingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoastingInfoRepository extends JpaRepository<RoastingInfo, Long> {
    List<RoastingInfo> findByBrigadeBrigadeId(UUID brigadeId);
}
