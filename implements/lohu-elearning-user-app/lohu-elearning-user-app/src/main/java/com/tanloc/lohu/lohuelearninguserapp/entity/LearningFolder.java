package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LearningFolder")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LearningFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String name;

    @Column(name = "Description", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String description;

    @Column(name = "ModifiedDate", nullable = false)
    LocalDateTime modifiedDate;

    @Column(name = "IsPublic", nullable = false)
    Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "MilestoneId")
    Milestone milestone;

    @ManyToOne
    @JoinColumn(name = "UserId")
    User user;

    @OneToMany(mappedBy = "learningFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FlashCardSet> flashCardSets;

    @OneToMany(mappedBy = "learningFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<MultipleChoiceTest> multipleChoiceTests;

    @OneToMany(mappedBy = "learningFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FreeResponseTest> freeResponseTests;
}
