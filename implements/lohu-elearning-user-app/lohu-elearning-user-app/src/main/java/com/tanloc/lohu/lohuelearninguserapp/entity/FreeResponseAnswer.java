package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "FreeResponseAnswer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FreeResponseAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "AnswerContent", columnDefinition = "NVARCHAR(4000)", length = 4000, nullable = false)
    String answerContent;

    @Column(name = "IsSample", nullable = false)
    Boolean isSample;

    @ManyToOne
    @JoinColumn(name = "FreeResponseQuestionId")
    FreeResponseQuestion freeResponseQuestion;
}
