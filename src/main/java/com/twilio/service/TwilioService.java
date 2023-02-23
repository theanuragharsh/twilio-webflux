package com.twilio.service;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import reactor.core.publisher.Mono;

public interface TwilioService {

    public Mono<PasswordResetResponseDto> sendOtp(PasswordResetRequestDto passwordResetRequestDto);

    public Mono<String> validateOtp(String userName, String userInputOtp);
}
