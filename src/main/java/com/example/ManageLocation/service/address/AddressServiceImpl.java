package com.example.ManageLocation.service.address;

import com.example.ManageLocation.currentUser.CurrentUser;
import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.entity.address.Address;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.repo.address.AddressRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private final AddressRepo addressRepo;
    private final CurrentUser currentUser;

    @Override
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
        Address savedAddress = addressRepo.save(address);
        return savedAddress.getId();
    }
}
