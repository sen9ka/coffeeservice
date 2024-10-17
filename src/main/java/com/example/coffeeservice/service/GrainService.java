package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.Constant;
import com.example.coffeeservice.controller.exceptionHandler.exception.GrainArrivalMessageProcessingException;
import com.example.coffeeservice.entity.dto.GrainArrivalMessage;
import com.example.coffeeservice.entity.model.GrainArrivalModel;
import com.example.coffeeservice.entity.model.GrainWarehouse;
import com.example.coffeeservice.repository.GrainArrivalRepository;
import com.example.coffeeservice.repository.GrainWarehouseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrainService {

    private final EntityManager entityManager;
    private final GrainArrivalRepository grainArrivalRepository;

    public GrainArrivalMessage convertToGrainArrivalMessage(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, GrainArrivalMessage.class);
        } catch (JsonProcessingException e) {
            throw new GrainArrivalMessageProcessingException(Constant.GRAIN_ARRIVAL_PROCESSING_EXCEPTION_TEXT);
        }
    }

    public GrainArrivalModel convertToGrainArrivalModel(GrainArrivalMessage grainArrivalMessage) {
        GrainArrivalModel grainArrivalModel = new GrainArrivalModel();
        BeanUtils.copyProperties(grainArrivalMessage, grainArrivalModel);
        return grainArrivalModel;
    }

    @Transactional
    public void saveGrainArrival(GrainArrivalModel grainArrivalModel) {
        grainArrivalRepository.save(grainArrivalModel);
    }

    @Transactional
    public List<GrainWarehouse> getFilteredGrainWarehouseInfo(Optional<String> country, Optional<String> type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GrainWarehouse> cq = cb.createQuery(GrainWarehouse.class);
        Root<GrainWarehouse> root = cq.from(GrainWarehouse.class);

        List<Predicate> predicates = new ArrayList<>();

        country.ifPresent(c -> predicates.add(cb.equal(root.get("country"), c)));
        type.ifPresent(t -> predicates.add(cb.equal(root.get("type"), t)));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }

}
