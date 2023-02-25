package com.twilio.controller;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.PasswordResetVerifyOtpDto;
import com.twilio.models.enums.OtpStatus;
import com.twilio.service.TwilioService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;


@WebFluxTest(TwilioOtpController.class)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TwilioOtpControllerTest {

    @MockBean
    private TwilioService twilioService;

    @Autowired
    private WebTestClient webTestClient;

    private final PasswordResetRequestDto passwordResetRequestDto = PasswordResetRequestDto.builder().userName("Anurag").phoneNumber("+91 7979775728").build();
    private final PasswordResetVerifyOtpDto passwordResetVerifyOtpDto = PasswordResetVerifyOtpDto.builder().userName("Anurag").otp("999999").build();
    private final PasswordResetResponseDto passwordResetResponseDto = PasswordResetResponseDto.builder()
            .message("Dear customer otp for resetting your password is: 320750").otpStatus(OtpStatus.DELIVERED)
            .build();

    @Test
    void sendOtpTest() {
        when(twilioService.sendOtp(passwordResetRequestDto)).thenReturn(Mono.just(new PasswordResetResponseDto()));
        webTestClient.post().uri("/otp/send").bodyValue(passwordResetRequestDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(PasswordResetResponseDto.class)
                .getResponseBody();
    }

    @Test
    void verifyOtpTest() {
        when(twilioService.verifyOtp(passwordResetVerifyOtpDto)).thenReturn(Mono.just("Valid OTP, please proceed further.."));
        webTestClient.post().uri("/otp/verify")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(passwordResetVerifyOtpDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Valid OTP, please proceed further..");
    }
}
