package com.example.kotlin.member

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "MEMBER_TABLE")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id", nullable = false)
    var id: UUID?,

    @Column(name = "name", nullable = false)
    var name: String?,

    @Column(name = "email")
    var email: String?,

    @Column(name = "picture")
    var picture: String?,

    @Enumerated(EnumType.STRING)
    var role: Role?

)
