package com.example.ManageLocation.entity.auth;

import com.example.ManageLocation.config.jpa.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "luna_history_login")
@Getter
@NoArgsConstructor
public class HistoryLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private String ipAddress;

    private String userAgent;

    private LocalDateTime createdAt = LocalDateTime.now();

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    private HistoryLogin(UserEntity user, String ipAddress, String userAgent){
        this.user = user;
        this.ipAddress= ipAddress;
        this.userAgent = userAgent;
    }

    public static HistoryLogin of(UserEntity user, String ipAddress, String userAgent){
        return new HistoryLogin(user, ipAddress, userAgent);
    }

}
