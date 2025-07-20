package com.example.ManageLocation.dto.address;

import com.example.ManageLocation.entity.address.Address;

public record AddressDTO(
        Long id,
        String country,
        String cityOrProvince,
        String district,
        String commune,
        String detailAdd1,
        String detailAdd2,
        String zipCode,
        String originalAddress,
        String altAddress,
        double longitude,
        double latitude
) {
    public static AddressDTO from (Address address){
        return new AddressDTO(
                address.getId(),
                address.getCountry(),
                address.getCityOrProvince(),
                address.getDistrict(),
                address.getCommune(),
                address.getDetailAdd1(),
                address.getDetailAdd2(),
                address.getZipCode(),
                address.getOriginalAddress(),
                address.getAltAddress(),
                address.getLongitude(),
                address.getLongitude()
        );
    }
}
