package com.field.muzi.web.admin.controller.API;

import com.field.muzi.domain.entity.UnitCostEntity;
import com.field.muzi.web.user.dto.unitCost.UnitCostUpdateRequest;
import com.field.muzi.web.user.service.UnitCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUnitCostAPI {

    private final UnitCostService unitCostService;

    @GetMapping("/unit")
    public UnitCostEntity unitCost() {
       return unitCostService.unitCost();
    }

    @PostMapping("/unit/update")
    public void unitCostUpdate(UnitCostUpdateRequest request) {
        unitCostService.unitCostUpdate(request);
    }

}
