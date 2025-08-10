package com.example.ManageLocation.controller.area;

import com.example.ManageLocation.dto.area.AreaRequest;
import com.example.ManageLocation.service.area.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("name/{areaId}")
    public ResponseEntity<Void> updateAreaName(
            @PathVariable("areaId") Long areaId,
            @RequestBody AreaRequest areaRequest
    ){
        areaService.updateAreaName(areaRequest, areaId);
        return ResponseEntity.ok().build();
    }
}
