package com.eventapp.repository;

import com.eventapp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Can add custom queries later if needed
}
