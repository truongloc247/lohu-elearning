package com.tanloc.lohu.lohuelearninguserapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "FlashCard")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "Term", columnDefinition = "NVARCHAR(3000)", length = 3000, nullable = false)
    String term;

    @Column(name = "Definition", columnDefinition = "NVARCHAR(3000)", length = 3000, nullable = false)
    String definition;

    @Column(name = "Image", columnDefinition = "NVARCHAR(1000)", length = 1000)
    String image;

    @ManyToOne
    @JoinColumn(name = "FlashCardSetId")
    FlashCardSet flashCardSet;
}
