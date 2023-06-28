package com.field.muzi.web.user.controller.API;

import com.field.muzi.domain.entity.UnitCostEntity;
import com.field.muzi.web.user.service.UnitCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UnitCostAPI {

    private final UnitCostService unitCostService;

    @GetMapping("/unit")
    public UnitCostEntity unitCost() {
       return unitCostService.unitCost();
    }

}
