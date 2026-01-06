package com.tanloc.lohu.lohuelearninguserapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LearningFolderCreationRequest {
    @NotBlank(message = "Tên thư mục không được để trống hay chỉ chứa toàn ký tự trắng")
    String name;

    String description;

    @NotNull
    Boolean isPublic;
}
