package com.example.ManageLocation.service.area;

import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.area.AreaRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl {
    private final AreaRepo areaRepo;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private Polygon convertToPolygon(List<List<Double>> coordinates){
        if (coordinates == null || coordinates.size() < 3) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Coordinates are required at least three points to create polygon");
        }

        List<Coordinate> coordinateList = new ArrayList<>();
        for (List<Double> coordinate: coordinates){
            if (coordinate.size()!=2)
                throw new CustomException(HttpStatus.BAD_REQUEST, "Longitude and latitude are both required!");
            coordinateList.add(new Coordinate(coordinate.get(0), coordinate.get(1)));
        }

        if (!coordinateList.get(0).equals(coordinateList.get(coordinateList.size()-1))){
            coordinateList.add(coordinateList.get(0));
        }

        LinearRing ring = geometryFactory.createLinearRing(coordinateList.toArray(new Coordinate[0]));
        return geometryFactory.createPolygon(ring);
    }
}
