package com.twilio.service;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.PasswordResetVerifyOtpDto;
import reactor.core.publisher.Mono;

public interface TwilioService {

    Mono<PasswordResetResponseDto> sendOtp(PasswordResetRequestDto passwordResetRequestDto);

    Mono<String> verifyOtp(PasswordResetVerifyOtpDto passwordResetVerifyOtpDto);
}
