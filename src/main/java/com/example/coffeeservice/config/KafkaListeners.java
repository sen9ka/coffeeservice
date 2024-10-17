package com.example.coffeeservice.config;

import com.example.coffeeservice.entity.dto.GrainArrivalMessage;
import com.example.coffeeservice.service.GrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final GrainService grainService;

    @KafkaListener(topics = "grain-arrival", groupId = "grain-arrival")
    void grainArrivalListener(String data) {
        GrainArrivalMessage grainArrivalMessage = grainService.convertToGrainArrivalMessage(data);
        grainService.saveGrainArrival(grainService.convertToGrainArrivalModel(grainArrivalMessage));
    }

}
