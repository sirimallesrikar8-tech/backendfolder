package com.eventapp.repository;

import com.eventapp.entity.ProfileInfo;
import com.eventapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileInfoRepository extends JpaRepository<ProfileInfo, Long> {

    Optional<ProfileInfo> findByUser(User user);

    void deleteByUser(User user);
}
