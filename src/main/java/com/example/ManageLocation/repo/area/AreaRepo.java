package com.example.ManageLocation.repo.area;

import com.example.ManageLocation.entity.area.Area;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AreaRepo extends JpaRepository<Area, Long> {
    @Query("SELECT a FROM Area a WHERE ST_Contains(a.polygon, :point) = true ORDER BY a.createAt ASC")
    Optional<Area> findAreaByPoint(@Param("point") Point point);

}
