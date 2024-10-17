package com.example.coffeeservice.repository;

import com.example.coffeeservice.entity.model.Brigade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrigadeRepository extends JpaRepository<Brigade, UUID> {
    Optional<Brigade> findByBrigadeNumber(Long brigadeNumber);
}
