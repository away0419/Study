package com.example.kotlin.security.oauth2

import com.example.kotlin.member.Member
import com.example.kotlin.member.repository.MemberRepository
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2MemberService(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        if (userRequest == null) throw OAuth2AuthenticationException("Oauth2 UserRequest Error")

        // userRequest에서 user 정보 가져오기
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        // registrationId는 Oauth2 서비스 이름 (구글, 네이버, 카카오 등)
        val registrationId = userRequest.clientRegistration.registrationId
        // OAuth2 로그인 진행시 키가 되는 필드값
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        // OAuth2 서비스의 유저 정보들
        val attributes = oAuth2User.attributes;
        // userInfo 추출
        val oauth2UserInfo = OAuth2Attributes.extract(registrationId,attributes)

        // 전달받은 OAuth2User의 attribute를 이용하여 회원가입 및 수정의 역할을 한다.
        val member = oauth2UserInfo?.let { saveOrUpdate(it) }

        // session에 SessionUser(user의 정보를 담는 객체)를 담아 저장한다.
//        httpSession.setAttribute("user", SessionUser(user))

        return DefaultOAuth2User(
            setOf(SimpleGrantedAuthority(member?.role?.key)),
            attributes,
            userNameAttributeName
        )
    }

    fun saveOrUpdate(oauth2UserInfo: Oauth2UserInfo): Member {
        val member = memberRepository.findByEmail(oauth2UserInfo.email?:"")
            ?: oauth2UserInfo.toEntity()

        return memberRepository.save(member)
    }
}