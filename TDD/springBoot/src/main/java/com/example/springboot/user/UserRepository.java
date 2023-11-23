package com.example.springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, UserEntity> {
    public UserEntity save(UserEntity UserEntity);

}
