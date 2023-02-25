package com.twilio.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetVerifyOtpDto {
    private String userName;
    private String otp;
}
