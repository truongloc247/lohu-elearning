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
public class FlashCardEditRequest {
    @NotNull()
    Long id;

    @NotBlank(message = "Thuật ngữ không được để trống")
    String term;

    @NotBlank(message = "Định nghĩa không được để trống")
    String definition;

    MultipartFile image;
}
