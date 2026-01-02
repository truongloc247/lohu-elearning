package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "FreeResponseQuestion")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FreeResponseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "QuestionContent", columnDefinition = "NVARCHAR(3000)", length = 3000, nullable = false)
    String questionContent;

    @Column(name = "Image", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String image;

    @ManyToOne
    @JoinColumn(name = "FreeResponseTestId")
    FreeResponseTest freeResponseTest;

    @OneToMany(mappedBy = "freeResponseQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FreeResponseAnswer> freeResponseAnswers;
}
