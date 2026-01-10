package com.tanloc.lohu.lohuelearningadminapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Username", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false, unique = true)
    String username;

    @Column(name = "Password", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String password;
}
