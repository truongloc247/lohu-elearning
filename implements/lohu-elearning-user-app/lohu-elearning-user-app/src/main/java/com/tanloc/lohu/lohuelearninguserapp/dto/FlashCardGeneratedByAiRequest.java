package com.tanloc.lohu.lohuelearninguserapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlashCardGeneratedByAiRequest {
    @NotBlank(message = "Vui lòng nhập nội dung muốn AI hỗ trợ tạo nhanh (chủ đề/từ vựng/kiến thức cốt lõi,...)")
    String message;

    MultipartFile image;
}
