package com.security.springboot.domain.User.Model;


import com.security.springboot.domain.User.Role.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private Long id;
    private String userEmail;
    private String userPw;
    private UserRole role;

    public UserVO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.userEmail = userEntity.getUserEmail();
        this.userPw = userEntity.getUserPw();
        this.role = userEntity.getRole();
    }

    public UserEntity makeUserEntity() {
        return UserEntity.builder().userEmail(this.userEmail).userPw(this.userPw).role(this.role).build();
    }
}
