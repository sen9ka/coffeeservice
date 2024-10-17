package com.example.coffeeservice.service;

import com.example.coffeeservice.controller.exceptionHandler.Constant;
import com.example.coffeeservice.controller.exceptionHandler.exception.GrainArrivalMessageProcessingException;
import com.example.coffeeservice.entity.dto.GrainArrivalMessage;
import com.example.coffeeservice.entity.model.GrainArrivalModel;
import com.example.coffeeservice.entity.model.GrainWarehouse;
import com.example.coffeeservice.repository.GrainArrivalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrainServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private GrainArrivalRepository grainArrivalRepository;

    @InjectMocks
    private GrainService grainService;

    @Test
    void testConvertToGrainArrivalMessage_ValidJson() throws JsonProcessingException {
        String jsonData = "{\"country\":\"Brazil\"}";
        GrainArrivalMessage result = grainService.convertToGrainArrivalMessage(jsonData);

        assertNotNull(result);
        assertEquals("Brazil", result.getCountry());
    }

    @Test
    void testConvertToGrainArrivalMessage_InvalidJson() {
        String invalidJsonData = "invalid json";

        Exception exception = assertThrows(GrainArrivalMessageProcessingException.class, () ->
                grainService.convertToGrainArrivalMessage(invalidJsonData));

        assertEquals(Constant.GRAIN_ARRIVAL_PROCESSING_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    void testConvertToGrainArrivalModel() {
        GrainArrivalMessage message = new GrainArrivalMessage();
        message.setCountry("Brazil");

        GrainArrivalModel model = grainService.convertToGrainArrivalModel(message);

        assertNotNull(model);
        assertEquals("Brazil", model.getCountry());
    }

    @Test
    void testSaveGrainArrival() {
        GrainArrivalModel model = new GrainArrivalModel();

        grainService.saveGrainArrival(model);

        verify(grainArrivalRepository, times(1)).save(model);
    }

    @Test
    void testGetFilteredGrainWarehouseInfo_WithCountry() {
        Optional<String> country = Optional.of("Brazil");
        Optional<String> type = Optional.empty();

        GrainWarehouse warehouse = new GrainWarehouse();
        warehouse.setCountry("Brazil");

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<GrainWarehouse> cq = mock(CriteriaQuery.class);
        Root<GrainWarehouse> root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(GrainWarehouse.class)).thenReturn(cq);
        when(cq.from(GrainWarehouse.class)).thenReturn(root);
        when(cb.equal(root.get("country"), "Brazil")).thenReturn(predicate);

        List<GrainWarehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);

        when(entityManager.createQuery(cq)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery(cq).getResultList()).thenReturn(warehouses);

        List<GrainWarehouse> result = grainService.getFilteredGrainWarehouseInfo(country, type);

        assertEquals(1, result.size());
        assertEquals("Brazil", result.get(0).getCountry());
    }
    
}