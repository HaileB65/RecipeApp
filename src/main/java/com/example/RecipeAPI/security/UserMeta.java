package com.example.RecipeAPI.security;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_meta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;
}
