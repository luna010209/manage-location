package com.example.ManageLocation.config;

import com.example.ManageLocation.config.jpa.AuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class JpaConfig {
    @Bean
    public AuditorAware<Integer> auditAware() {
        return new AuditAwareImpl();
    }
}
