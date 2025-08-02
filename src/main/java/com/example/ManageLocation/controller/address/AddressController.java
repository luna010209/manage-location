package com.example.ManageLocation.controller.address;

import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.service.address.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressServiceImpl addressService;

    @PostMapping
    public ResponseEntity<Long> newAddress(@RequestBody AddressDTO addressDTO){
        Long addressId = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(addressId);
    }
}
