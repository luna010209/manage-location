package com.example.ManageLocation.dto.auth;

import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.entity.auth.UserEntity;

public record UserInfo(
        String username,
        String email,
        String phone,
        AddressDTO address
) {
    public static UserInfo from(UserEntity user){
//        List<AddressDTO> addressDTOS = addresses.stream().map(x-> AddressDTO.from(x)).toList();
        return new UserInfo(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()!=null? AddressDTO.from(user.getAddress()): null
        );
    }
}
