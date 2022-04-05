package com.api.twilio;

import feign.Feign;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class TwilioClientTest {

    @Value("${TWILIO_SID}")
    private String twilioSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twiolioAuthToken;

    @Value("${TO_NUMBER}")
    private String toNumber;

    private static final String FROM_NUMBER = "+12183180829";

    private static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

    @Test
    void twilioTextMessageTest() {

        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twiolioAuthToken);

        TwilioClient client = Feign.builder()
                .requestInterceptor(interceptor)
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);

        client.sendTextMessage(twilioSid, toNumber, FROM_NUMBER, "Hi its John, John Doe!");

    }

}
