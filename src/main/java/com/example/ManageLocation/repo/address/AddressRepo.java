package com.example.ManageLocation.repo.address;

import com.example.ManageLocation.entity.address.Address;
import com.example.ManageLocation.enums.AddressTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
//    boolean existsByOriginalAddress(String originalAddress);
//    List<Address> findByAddressTargetAndTargetId(AddressTarget addressTarget, Long targetId);
}
