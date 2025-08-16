package com.example.ManageLocation.service.address;

import com.example.ManageLocation.currentUser.CurrentUser;
import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.entity.address.Address;
import com.example.ManageLocation.entity.area.Area;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.address.AddressRepo;
import com.example.ManageLocation.repo.area.AreaRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private final AddressRepo addressRepo;
    private final AreaRepo areaRepo;
    private final CurrentUser currentUser;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    @Override
    @Transactional
    public Long createAddress(AddressDTO addressDTO) {
        UserEntity user = currentUser.currentUser();
        Address address = Address.builder()
                .country(addressDTO.country())
                .cityOrProvince(addressDTO.cityOrProvince())
                .district(addressDTO.district())
                .commune(addressDTO.commune())
                .detailAdd1(addressDTO.detailAdd1())
                .detailAdd2(addressDTO.detailAdd2())
                .originalAddress(addressDTO.originalAddress())
                .altAddress(addressDTO.altAddress())
                .latitude(addressDTO.latitude())
                .longitude(addressDTO.longitude())
                .user(user)
                .build();

        Point point = geometryFactory.createPoint(new Coordinate(addressDTO.longitude(), addressDTO.latitude()));

        Optional<Area> area = areaRepo.findAreaByPoint(point);
        if (area.isPresent()) address.setArea(area.get());

        Address savedAddress = addressRepo.save(address);
        return savedAddress.getId();
    }

    @Override
    @Transactional
    public void updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepo.findById(id).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist address")
        );
        UserEntity user = currentUser.currentUser();
        if (!user.getAddress().getId().equals(id))
            throw new CustomException(HttpStatus.BAD_REQUEST, "This address is not valid");
        address.updateAddress(addressDTO);
    }
}
