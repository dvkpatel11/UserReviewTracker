package com.api.twilio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2022-03-01/Accounts/{AccountSid}/Calls.json")
    public void sendVoiceMessage(@Param("AccountSid") String accountSid,
            @Param("To") String to,
            @Param("From") String from,
            @Param("Twim") String twiml);

}
