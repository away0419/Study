package com.security.springboot.domain.User.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsVO implements UserDetails {

    // UserEntity 메소드를 UserDetailVO 객체로 위임 시키는 어노테이션
    // 즉, UserEntity의 메소드를 자신이 바로 사용할 수 있음.
    @Delegate
    private final UserEntity userEntity;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserPw();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userEntity.getIsEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userEntity.getIsEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userEntity.getIsEnable();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getIsEnable();
    }
}
