package com.example.kotlin.domain.member

import jakarta.persistence.*
import java.util.UUID

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    val id:UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String,
)