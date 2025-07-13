package com.example.ManageLocation.repo.auth;

import com.example.ManageLocation.entity.auth.HistoryLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLoginRepo extends JpaRepository<HistoryLogin, Long> {
}
