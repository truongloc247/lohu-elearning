package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "[User]")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String name;

    @Column(name = "Gender", nullable = false)
    Boolean gender;

    @Column(name = "DateOfBirth", nullable = false)
    LocalDate dateOfBirth;

    @Column(name = "PhoneNumber", columnDefinition = "NVARCHAR(50)", length = 50, nullable = false)
    String phoneNumber;

    @Column(name = "Email", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false, unique = true)
    String email;

    @Column(name = "Address", columnDefinition = "NVARCHAR(1000)", length = 1000, nullable = false)
    String address;

    @Column(name = "Username", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false, unique = true)
    String username;

    @Column(name = "Password", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String password;

    @Column(name = "VipExpirationDate")
    LocalDateTime vipExpirationDate;

    @Column(name = "AccountType", nullable = false)
    Boolean accountType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Bill> bills;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<LearningRoadmap> learningRoadmaps;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<LearningFolder> learningFolders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FlashCardSet> flashCardSets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<MultipleChoiceTest> multipleChoiceTests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FreeResponseTest> freeResponseTests;
}
