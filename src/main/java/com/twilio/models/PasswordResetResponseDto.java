package com.twilio.models;

import com.twilio.models.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {
    private String message;
    private OtpStatus otpStatus;
}
