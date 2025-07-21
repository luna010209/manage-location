package com.example.ManageLocation.dto.auth;

import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.entity.address.Address;
import com.example.ManageLocation.entity.auth.UserEntity;

import java.util.List;

public record UserInfo(
        String username,
        String email,
        String phone,
        List<AddressDTO> addressDTOList
) {
    public static UserInfo from(UserEntity user, List<Address> addresses){
        List<AddressDTO> addressDTOS = addresses.stream().map(x-> AddressDTO.from(x)).toList();
        return new UserInfo(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                addressDTOS
        );
    }
}
