package com.security.springboot.domain.User.Model;

import com.security.springboot.domain.User.Role.UserRole;
import com.security.springboot.domain.Common.CommonEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
public class UserEntity extends CommonEntity implements Serializable{

    @Column(nullable = false, unique = true, length = 50)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false, length =50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public UserEntity(String userEmail, String userPw){
        this.userEmail = userEmail;
        this.userPw = userPw;
    }

}
