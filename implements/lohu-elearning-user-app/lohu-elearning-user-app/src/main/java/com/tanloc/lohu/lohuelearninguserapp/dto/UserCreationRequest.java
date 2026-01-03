package com.tanloc.lohu.lohuelearninguserapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreationRequest {
    @NotBlank(message = "Tên không được để trống")
    @Pattern(
            regexp = "^([A-Z][a-z]+)(\\s[A-Z][a-z]+)*$",
            message = "Mỗi từ phải bắt đầu hoa, chỉ chữ cái, không số/ký tự đặc biệt, không thừa khoảng trắng"
    )
    String name;

    @NotNull(message = "Giới tính không được để trống")
    Boolean gender;

    @NotNull(message = "Ngày không được để trống")
    @PastOrPresent(message = "Ngày không được lớn hơn ngày hiện tại")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Số điện thoại không hợp lệ"
    )
    String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email không hợp lệ"
    )
    String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    String address;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 100, message = "Tên đăng nhập phải từ 3 đến 100 ký tự")
    @Pattern(
            regexp = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){1,18}[a-zA-Z0-9]$",
            message = "Chỉ cho phép chữ thường, chữ hoa, số, dấu gạch dưới (_) và dấu chấm (.)"
    )
    String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 3, max = 100, message = "Mật khẩu phải từ 3 đến 100 ký tự")
    String password;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 3, max = 100, message = "Xác nhận mật khẩu phải từ 3 đến 100 ký tự")
    String confirmPassword;
}
