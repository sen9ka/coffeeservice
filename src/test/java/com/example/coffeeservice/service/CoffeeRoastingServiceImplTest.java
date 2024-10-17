package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.exception.BrigadeNotFoundException;
import com.example.coffeeservice.entity.model.Brigade;
import com.example.coffeeservice.entity.model.RoastingInfo;
import com.example.coffeeservice.proto.CoffeeRoasting;
import com.example.coffeeservice.repository.BrigadeRepository;
import com.example.coffeeservice.repository.RoastingInfoRepository;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoffeeRoastingServiceImplTest {

    @InjectMocks
    private CoffeeRoastingServiceImpl coffeeRoastingService;

    @Mock
    private RoastingInfoRepository roastingInfoRepository;

    @Mock
    private BrigadeRepository brigadeRepository;

    @Mock
    private StreamObserver<CoffeeRoasting.RoastingResponse> responseObserver;

    @Test
    void testCreateRoastingInfoSuccess() {
        CoffeeRoasting.RoastingRequest request = CoffeeRoasting.RoastingRequest.newBuilder()
                .setType("type")
                .setCountry("country")
                .setWeight(10)
                .setBrigadeNumber(1L)
                .build();

        RoastingInfo roastingInfo = new RoastingInfo();
        when(roastingInfoRepository.save(any(RoastingInfo.class))).thenReturn(roastingInfo);
        when(brigadeRepository.findByBrigadeNumber(request.getBrigadeNumber())).thenReturn(java.util.Optional.of(new Brigade()));
        coffeeRoastingService.createRoastingInfo(request, responseObserver);
        verify(roastingInfoRepository).save(any(RoastingInfo.class));
        verify(responseObserver).onNext(any(CoffeeRoasting.RoastingResponse.class));
        verify(responseObserver).onCompleted();
    }

    @Test
    void testCreateRoastingInfoBrigadeNotFound() {
        CoffeeRoasting.RoastingRequest request = CoffeeRoasting.RoastingRequest.newBuilder()
                .setType("type")
                .setCountry("country")
                .setWeight(10)
                .setBrigadeNumber(1L)
                .build();

        when(brigadeRepository.findByBrigadeNumber(request.getBrigadeNumber())).thenReturn(java.util.Optional.empty());
        assertThrows(BrigadeNotFoundException.class, () -> coffeeRoastingService.createRoastingInfo(request, responseObserver));
    }
}