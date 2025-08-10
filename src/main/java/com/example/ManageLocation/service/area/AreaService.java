package com.example.ManageLocation.service.area;

import com.example.ManageLocation.dto.area.AreaRequest;

public interface AreaService {
    Long createArea(AreaRequest areaRequest);

    void updateAreaName(AreaRequest areaRequest, Long id);
}
