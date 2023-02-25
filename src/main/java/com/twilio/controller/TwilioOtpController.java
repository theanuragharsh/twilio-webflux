package com.twilio.controller;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.PasswordResetVerifyOtpDto;
import com.twilio.service.TwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TwilioOtpController {

    private final TwilioService twilioService;

    @PostMapping("/otp/send")
    public Mono<PasswordResetResponseDto> sendOtp(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        return twilioService.sendOtp(passwordResetRequestDto);
    }

    @PostMapping("/otp/verify")
    public Mono<String> validateOtp(@RequestBody PasswordResetVerifyOtpDto passwordResetVerifyOtpDto) {
        return twilioService.verifyOtp(passwordResetVerifyOtpDto);
    }
}
