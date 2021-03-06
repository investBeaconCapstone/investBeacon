package com.example.investbeacon;

import com.example.investbeacon.models.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CaptchaValidator {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.recaptcha.key.secret}")
    private String reCaptchaSecret;

    public boolean isValid(String captcha) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret="+reCaptchaSecret+"&response="+captcha;

        CaptchaResponse resp = restTemplate.postForObject(url+params, null, CaptchaResponse.class);
        System.out.println("Success: " + resp.isSuccess());
        System.out.println("Hostname: " + resp.getHostname());
        System.out.println("Challenge_ts: " + resp.getChallenge_ts());

        if(resp.getErrorCodes() != null) {
            for (String error : resp.getErrorCodes()) {
                System.out.println("\t" + error);
            }
        }
        return resp.isSuccess();
    }
}
