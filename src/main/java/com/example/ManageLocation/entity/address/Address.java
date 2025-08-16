package com.example.ManageLocation.entity.address;

import com.example.ManageLocation.config.jpa.CommonAuditFields;
import com.example.ManageLocation.dto.address.AddressDTO;
import com.example.ManageLocation.entity.area.Area;
import com.example.ManageLocation.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Area area;

    public void updateAddress(AddressDTO dto){
        this.country = dto.country();
        this.cityOrProvince = dto.cityOrProvince();
        this.district = dto.district();
        this.commune= dto.commune();
        this.detailAdd1 = dto.detailAdd1();
        this.detailAdd2 = dto.detailAdd2();
        this.zipCode = dto.zipCode();
        this.originalAddress = dto.originalAddress();
        this.altAddress = dto.altAddress();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
    }
}
