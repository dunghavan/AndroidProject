package com.example.dung.demo_recyclerview.model;


import android.util.Log;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthenticationException;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RestAuthenticationProvider  {
    private static String secret = "4d53bce03ec34c0a911182d4c228ee6c";
    private static String data = "A93reRTUJHsCuQSHR+L3GxqOJyDmQpCgps102ciuabc=";

    public static void calculateHMAC() {
        String result = "";
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = new String(Base64.encodeBase64(rawHmac));
            Log.d("HMAC", result);
            //return result;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }
}