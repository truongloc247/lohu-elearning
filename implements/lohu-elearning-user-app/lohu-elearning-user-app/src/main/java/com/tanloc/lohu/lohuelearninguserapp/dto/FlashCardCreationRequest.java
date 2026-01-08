package com.tanloc.lohu.lohuelearninguserapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlashCardCreationRequest {
    @NotBlank(message = "Thuật ngữ không được đẻ trống")
    private String term;

    @NotBlank(message = "Định nghĩa không được để trống")
    private String definition;

    private MultipartFile image;

    @NotNull(message = "Mã flash card set không được trống")
    private Long flashCardSetId;
}
