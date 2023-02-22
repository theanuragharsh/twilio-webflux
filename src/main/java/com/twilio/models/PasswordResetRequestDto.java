package com.twilio.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetRequestDto {
    private String phoneNumber;
    private String userName;
    private String otp;
}
