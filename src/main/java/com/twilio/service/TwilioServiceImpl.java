package com.twilio.service;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.TwilioAccountDetails;
import com.twilio.models.enums.OtpStatus;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwilioServiceImpl implements TwilioService {

    private final TwilioAccountDetails twilioAccountDetails;
    private static final DecimalFormat OTP_FORMAT = new DecimalFormat("000000");
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private HashMap<String, String> userOtpMap = new HashMap<>();

    public String otpGenerator() {
        return OTP_FORMAT.format(SECURE_RANDOM.nextInt(999999));
    }

    public Mono<PasswordResetResponseDto> sendOtp(PasswordResetRequestDto passwordResetRequestDto) {

        PasswordResetResponseDto passwordResetResponseDto;
        try {
            PhoneNumber sender = new PhoneNumber(twilioAccountDetails.getTrialNumber());
            PhoneNumber receiver = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            String otp = otpGenerator();

            String otpMessage = "Dear customer otp for resetting your password is: " + otp;
            Message.creator(receiver, sender, otpMessage)
                    .create();
            passwordResetResponseDto = PasswordResetResponseDto.builder()
                    .otpStatus(OtpStatus.DELIVERED)
                    .message(otpMessage)
                    .build();
            userOtpMap.put(passwordResetRequestDto.getUserName(), otp);
        } catch (Exception exception) {

            return Mono.just(PasswordResetResponseDto.builder()
                    .otpStatus(OtpStatus.FAILED).message(exception.getMessage())
                    .build());
        }
        log.info(userOtpMap.get(passwordResetRequestDto.getUserName()));
        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOtp(String userName, String userInputOtp) {
        String otp = userOtpMap.get(userName);
        if (otp == null) {
            return Mono.error(new IllegalArgumentException("No OTP request found for user " + userName));
        }
        if (otp.equals(userInputOtp)) {
            userOtpMap.remove(userName);
            return Mono.just("Valid OTP, please enter new password!");
        }
        return Mono.error(new IllegalArgumentException("Invalid OTP, please retry!"));
    }
}
