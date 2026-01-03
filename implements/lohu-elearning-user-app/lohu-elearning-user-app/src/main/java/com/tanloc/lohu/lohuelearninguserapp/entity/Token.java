package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "Token")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Token", columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    String token;

    @Column(name = "Email", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String email;

    @Column(name = "ExpirationTime", nullable = false)
    LocalDateTime expirationTime;

    @Column(name = "IsVerified", nullable = false)
    Boolean isVerified;
}
