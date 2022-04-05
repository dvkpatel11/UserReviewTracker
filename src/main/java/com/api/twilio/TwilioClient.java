package com.api.twilio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {

    // Method to call Twilio's SMS API to send a text message
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Messages.json")
    public void sendTextMessage(@Param("AccountSid") String accountSid,
            @Param("To") String to,
            @Param("From") String from,
            @Param("Body") String body);

}
