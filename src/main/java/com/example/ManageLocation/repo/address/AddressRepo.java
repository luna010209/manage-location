package com.example.ManageLocation.repo.address;

import com.example.ManageLocation.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
    boolean existsByOriginalAddress(String originalAddress);
}
