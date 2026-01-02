package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MultipleChoiceTest")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MultipleChoiceTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(100)", length = 100, nullable = false)
    String name;

    @Column(name = "Description", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String description;

    @Column(name = "Audio", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String audio;

    @Column(name = "Image", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String image;

    @Column(name = "Video", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String video;

    @Column(name = "ModifiedDate", nullable = false)
    LocalDateTime modifiedDate;

    @Column(name = "IsPublic", nullable = false)
    Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "LearningFolderId")
    LearningFolder learningFolder;

    @ManyToOne
    @JoinColumn(name = "MilestoneId")
    Milestone milestone;

    @ManyToOne
    @JoinColumn(name = "UserId")
    User user;

    @OneToMany(mappedBy = "multipleChoiceTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<MultipleChoiceQuestion> multipleChoiceQuestions;
}
