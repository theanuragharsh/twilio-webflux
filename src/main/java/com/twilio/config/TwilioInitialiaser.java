package com.twilio.config;

import com.twilio.Twilio;
import com.twilio.models.TwilioAccountDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitialiaser {

    @Autowired
    TwilioAccountDetails twilioAccountDetails;

    @PostConstruct
    public void initTwilio() {

        Twilio.init(twilioAccountDetails.getAccountSid(), twilioAccountDetails.getAuthToken());
    }
}
