package com.example.kotlin.member

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "MEMBER_TABLE")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID?,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "picture")
    var picture: String?,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @Column(name = "oauth2_key")
    var oAuth2Key: String?,

    @Column(name = "oauth2_provider")
    var oAuth2Provider: String?
)
