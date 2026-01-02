package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "Milestone")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String name;

    @Column(name = "Description", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String description;

    @Column(name = "IsFinished", nullable = false)
    Boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "LearningRoadmapId")
    LearningRoadmap learningRoadmap;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<LearningFolder> learningFolders;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FlashCardSet> flashCardSets;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<MultipleChoiceTest> multipleChoiceTests;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FreeResponseTest> freeResponseTests;
}
