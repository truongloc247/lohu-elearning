package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ChoiceAnswer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChoiceAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "AnswerContent", columnDefinition = "NVARCHAR(3000)", length = 3000, nullable = false)
    String answerContent;

    @Column(name = "Image", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String image;

    @Column(name = "IsCorrect", nullable = false)
    Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "MultipleChoiceQuestionId")
    MultipleChoiceQuestion multipleChoiceQuestion;
}
