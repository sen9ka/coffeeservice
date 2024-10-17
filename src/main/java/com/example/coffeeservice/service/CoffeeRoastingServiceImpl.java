package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.Constant;
import com.example.coffeeservice.controller.exceptionHandler.exception.BrigadeNotFoundException;
import com.example.coffeeservice.entity.model.RoastingInfo;
import com.example.coffeeservice.proto.CoffeeRoasting;
import com.example.coffeeservice.proto.CoffeeRoastingServiceGrpc;
import com.example.coffeeservice.repository.BrigadeRepository;
import com.example.coffeeservice.repository.RoastingInfoRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CoffeeRoastingServiceImpl extends CoffeeRoastingServiceGrpc.CoffeeRoastingServiceImplBase {

    private final RoastingInfoRepository roastingInfoRepository;
    private final BrigadeRepository brigadeRepository;

    @Override
    public void createRoastingInfo(CoffeeRoasting.RoastingRequest request, StreamObserver<CoffeeRoasting.RoastingResponse> responseObserver) {
        RoastingInfo roastingInfo = new RoastingInfo().toBuilder()
                .type(request.getType())
                .country(request.getCountry())
                .weight(request.getWeight())
                .brigade(brigadeRepository.findByBrigadeNumber(request.getBrigadeNumber())
                        .orElseThrow(() -> new BrigadeNotFoundException(Constant.BRIGADE_NOT_FOUND_BY_NAME_EXCEPTION_TEXT)))
                .build();
        roastingInfoRepository.save(roastingInfo);
        CoffeeRoasting.RoastingResponse response = CoffeeRoasting.RoastingResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Information saved successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
