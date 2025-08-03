package com.example.ManageLocation.service.address;

import com.example.ManageLocation.dto.address.AddressDTO;

public interface AddressService {
    Long createAddress(AddressDTO addressDTO);

    void updateAddress(Long id, AddressDTO addressDTO);
}
