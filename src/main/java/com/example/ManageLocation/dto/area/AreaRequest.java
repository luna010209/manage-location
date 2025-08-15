package com.example.ManageLocation.dto.area;

import com.example.ManageLocation.dto.address.AddressDTO;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

public record AreaRequest(
        String name,
        List<List<Double>> coordinates,
        Long addressId
) {
}
