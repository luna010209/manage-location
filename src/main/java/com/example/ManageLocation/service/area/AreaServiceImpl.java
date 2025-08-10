package com.example.ManageLocation.service.area;

import com.example.ManageLocation.currentUser.CurrentUser;
import com.example.ManageLocation.dto.area.AreaRequest;
import com.example.ManageLocation.entity.area.Area;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.enums.Role;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.area.AreaRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepo areaRepo;
    private final CurrentUser currentUser;

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

    // Create new area
    @Override
    @Transactional
    public Long createArea(AreaRequest areaRequest) {
        Polygon polygon = convertToPolygon(areaRequest.coordinates());
        UserEntity user = currentUser.currentUser();
        if (!user.getRoles().contains(Role.ROLE_ADMIN))
            throw new CustomException(HttpStatus.BAD_REQUEST, "You don't have permission to create area");
        Area area = Area.builder()
                .name(areaRequest.name())
                .polygon(polygon)
                .user(user)
                .build();
        Area savedArea = areaRepo.save(area);
        return savedArea.getId();
    }

    // Get area to update
    private Area getArea(Long id){
        Area area = areaRepo.findById(id).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No exist area")
        );
        if (!area.getUser().equals(currentUser.currentUser()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "You do not have authority to update area information");
        return area;
    }

    @Override
    @Transactional
    public void updateAreaName(AreaRequest areaRequest, Long id) {
        Area area = getArea(id);
        area.setName(areaRequest.name());
    }

    @Override
    @Transactional
    public void updatePolygon(AreaRequest areaRequest, Long id) {
        Area area = getArea(id);
        Polygon newPolygon = convertToPolygon(areaRequest.coordinates());
        area.setPolygon(newPolygon);
    }
}
