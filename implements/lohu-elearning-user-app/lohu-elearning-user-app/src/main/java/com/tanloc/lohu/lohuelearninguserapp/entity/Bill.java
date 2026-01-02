package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bill")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "CreationDate", nullable = false)
    LocalDateTime creationDate;

    @Column(name = "Amount", nullable = false)
    Long amount;

    @Column(name = "IsPurchased", nullable = false)
    Boolean isPurchased;

    @Column(name = "Description", columnDefinition = "NVARCHAR(1000)", length = 1000, nullable = false)
    String description;

    @ManyToOne
    @JoinColumn(name = "UserId")
    User user;
}
