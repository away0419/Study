package com.example.springboot.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

}
