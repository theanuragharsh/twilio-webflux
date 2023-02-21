package com.twilio.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioAccountDetails {
    private String authToken;
    private String accountSid;
    private String phoneNumber;
}
