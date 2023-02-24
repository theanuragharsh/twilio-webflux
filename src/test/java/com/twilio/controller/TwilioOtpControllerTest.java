package com.twilio.controller;

import com.twilio.models.PasswordResetRequestDto;
import com.twilio.models.PasswordResetResponseDto;
import com.twilio.models.enums.OtpStatus;
import com.twilio.service.TwilioService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.when;


@WebFluxTest(TwilioOtpController.class)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TwilioOtpControllerTest {

    @MockBean
    private TwilioService twilioService;

    @Autowired
    private WebTestClient webTestClient;

    private final Instant now = Instant.now().truncatedTo(ChronoUnit.MINUTES);
    private final PasswordResetRequestDto passwordResetRequestDto = PasswordResetRequestDto.builder().userName("Anurag").phoneNumber("+91 7979775728").otp("444444").build();
    private final PasswordResetResponseDto passwordResetResponseDto = PasswordResetResponseDto.builder()
            .message("Dear customer otp for resetting your password is: 320750").otpStatus(OtpStatus.DELIVERED)
            .build();

    @Test
    void sendOtpTest() {
        when(twilioService.sendOtp(passwordResetRequestDto)).thenReturn(Mono.just(new PasswordResetResponseDto()));
        webTestClient.post().uri("/otp/verify").bodyValue(passwordResetRequestDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(PasswordResetResponseDto.class)
                .getResponseBody();
    }

}
