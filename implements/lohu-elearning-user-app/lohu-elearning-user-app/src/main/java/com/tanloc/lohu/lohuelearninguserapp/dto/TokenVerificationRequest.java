package com.tanloc.lohu.lohuelearninguserapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenVerificationRequest {
    @NotNull(message = "Mã token không hợp lệ")
    Long tokenId;

    @NotBlank(message = "Token không hợp lệ")
    String token;
}
