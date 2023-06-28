package com.field.muzi.web.user.service;

import com.field.muzi.domain.entity.UnitCostEntity;
import com.field.muzi.repository.UnitCostRepository;
import com.field.muzi.web.user.dto.unitCost.UnitCostUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitCostService {

    private final UnitCostRepository unitCostRepository;

    @Transactional
    public UnitCostEntity unitCost() {
        String unitCostSeq = "unit_0000000001";
        UnitCostEntity unitCost = unitCostRepository.findById(unitCostSeq).orElseThrow(() -> new RuntimeException("부대 비용 정보를 찾을 수 없습니다."));

        return unitCost;
    }

    @Transactional
    public void unitCostUpdate(UnitCostUpdateRequest request) {
        String unitCostSeq = "unit_0000000001";
        UnitCostEntity unitCost = unitCostRepository.findById(unitCostSeq).orElseThrow(() -> new RuntimeException("부대 비용 정보를 찾을 수 없습니다."));

        unitCost.updateUnitCost(request.getBusFare(), request.getGuideFee(), request.getInsuranceFee());
    }
}
