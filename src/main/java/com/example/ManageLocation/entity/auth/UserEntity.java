package com.example.ManageLocation.entity.auth;

import com.example.ManageLocation.entity.address.Address;
import com.example.ManageLocation.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "luna_user_account")
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String phone;
    @Setter
    private String email;
    @Setter
    private String password;

    @Setter
    private LocalDateTime lastLogin;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "targetId")
//    @Where(clause = "address_target = 'USER'")
//    private List<Address> addresses;

    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
