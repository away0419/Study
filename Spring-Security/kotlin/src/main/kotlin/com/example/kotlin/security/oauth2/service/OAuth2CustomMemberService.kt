package com.example.kotlin.security.oauth2.service

import com.example.kotlin.member.Member
import com.example.kotlin.member.repository.MemberRepository
import com.example.kotlin.security.jwt.MemberPrincipal
import com.example.kotlin.security.oauth2.OAuth2Attributes
import com.example.kotlin.security.oauth2.OAuth2CustomUser
import com.example.kotlin.security.oauth2.OAuth2UserInfo
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2CustomMemberService(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2CustomUser {
        log.info("=========OAuth2UserService=========")

        if (userRequest == null) throw OAuth2AuthenticationException("Oauth2 UserRequest Error") // 만약 user request 가 없다면 에러 발생

        val delegate = DefaultOAuth2UserService() // Security에 있는 Default Oauth2 service를 가져옴.
        val oAuth2User = delegate.loadUser(userRequest) // userRequest에서 oAuth2User 정보 가져오기
        val registrationId =
            userRequest.clientRegistration.registrationId // registrationId는 Oauth2 서비스 이름 (구글, 네이버, 카카오 등)
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName // OAuth2 로그인 진행시 키가 되는 필드값
        val attributes = oAuth2User.attributes; // OAuth2 서비스의 유저 정보들
        val oauth2UserInfo =
            OAuth2Attributes.extract(registrationId, attributes) // Oauth2 서비스 별 들어 있는 정보를 통일 된 userInfo로 추출
        val member = saveOrUpdate(oauth2UserInfo) // 통일된 정보를 이용하여 DB에 있는지 확인 후 반환. 없을 경우 DB에 등록 후 등록 된 Entity 반환.
        val memberPrincipal = MemberPrincipal(member) // token에 담을 memberPrincipal 생성

        return OAuth2CustomUser( // 개발자가 memberPrincipal 담기 위해 구현한 객체
            setOf(SimpleGrantedAuthority(member.role.key)),
            attributes,
            userNameAttributeName,
            memberPrincipal
        )
    }

    fun saveOrUpdate(oauth2UserInfo: OAuth2UserInfo): Member {
        return memberRepository.findByoAuth2Key(oauth2UserInfo.oAuth2Key)
            ?: memberRepository.save(oauth2UserInfo.toEntity())

    }
}