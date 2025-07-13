package com.example.ManageLocation.entity.auth;

import com.example.ManageLocation.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
