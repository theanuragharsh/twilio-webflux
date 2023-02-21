package com.twilio.service;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.TwilioAccountDetails;
import com.twilio.models.enums.OtpStatus;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class TwilioService {

    @Autowired
    TwilioAccountDetails twilioAccountDetails;

    public Mono<String> otpGenerator() {
        return Mono.just(new DecimalFormat("000000").format(new Random().nextInt(999999)));
    }

    public Mono<PasswordResetResponseDto> sendOtp(PasswordResetRequestDto passwordResetRequestDto) {

        PasswordResetResponseDto passwordResetResponseDto=null;
        try {
            PhoneNumber sender = new PhoneNumber(twilioAccountDetails.getTrialNumber());
            PhoneNumber receiver = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            Mono<String> otp = otpGenerator();

            String otpMessage = "Dear customer otp for reseting your password is: " + otp;
            Message message = Message.creator(receiver, sender, otpMessage)
                    .create();
            passwordResetResponseDto= PasswordResetResponseDto.builder().otpStatus(OtpStatus.DELIVERED).message(otpMessage).build();

        } catch (Exception exception) {
            return Mono.just(PasswordResetResponseDto.builder().otpStatus(OtpStatus.FAILED).message("Could not generate otp").build());
        }

        return Mono.just(passwordResetRequestDto);
    }
}
