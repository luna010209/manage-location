package com.example.ManageLocation.dto.area;

import com.example.ManageLocation.dto.address.AddressDTO;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

public record AreaResponse(
        Long id,
        String name,
        List<List<Double>> coordinates,
        List<AddressDTO> addresses
) {
}
