package com.example.kotlin.security.oauth2

import com.example.kotlin.security.jwt.MemberPrincipal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    authorities: Collection<GrantedAuthority>,
    attributes: Map<String, Any>,
    userNameAttributeName: String,
    val memberPrincipal: MemberPrincipal // MemberVO 추가
) : DefaultOAuth2User(authorities, attributes, userNameAttributeName), OAuth2User
