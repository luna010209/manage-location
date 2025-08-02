package com.example.ManageLocation.entity.address;

import com.example.ManageLocation.config.jpa.CommonAuditFields;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.enums.AddressTarget;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "luna_address")
public class Address extends CommonAuditFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String cityOrProvince;
    private String district;
    private String commune;
    private String detailAdd1;
    private String detailAdd2;
    private String zipCode;

    private String originalAddress;
    private String altAddress;
    private double latitude;
    private double longitude;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

//    private AddressTarget addressTarget;
//    private Long targetId;
}
