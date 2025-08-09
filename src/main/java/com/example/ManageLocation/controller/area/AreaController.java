package com.example.ManageLocation.controller.area;

import com.example.ManageLocation.dto.area.AreaRequest;
import com.example.ManageLocation.service.area.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {
    private final AreaService areaService;

    @PostMapping
    public ResponseEntity<Long> newArea(@RequestBody AreaRequest areaRequest){
        Long id = areaService.createArea(areaRequest);
        return ResponseEntity.ok(id);
    }
}
