package com.example.ManageLocation.controller.address;

import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.service.address.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressServiceImpl addressService;

    @PostMapping
    public ResponseEntity<Long> newAddress(@RequestBody AddressDTO addressDTO) {
        Long addressId = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(addressId);
    }

    @PutMapping("{addressId}")
    public ResponseEntity<Void> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressDTO
    ) {
        addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok().build();
    }
}
