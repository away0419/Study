package com.security.springboot.domain.User.repository;

import com.security.springboot.domain.User.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserEmailAndUserPw(String userId, String userPw);

    Optional<UserEntity> findByUserEmail(String userId);
}
