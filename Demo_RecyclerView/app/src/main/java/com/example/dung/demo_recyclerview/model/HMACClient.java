package com.example.dung.demo_recyclerview.model;

import android.util.Log;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACClient {
    private final static String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    static String APIKey = "A93reRTUJHsCuQSHR+L3GxqOJyDmQpCgps102ciuabc=";
    static String AppId = "4d53bce03ec34c0a911182d4c228ee6c";

    public void makeHTTPCallUsingHMAC(String username) throws HttpException, IOException, NoSuchAlgorithmException {
        String url = "http://orderfooduit.azurewebsites.net/api/hinhthucthanhtoan/get";

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long timeStamp = calendar.getTimeInMillis() / 1000L;

        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        String nonce = UUID.randomUUID().toString();
        String contentHashBase64 = "";
        String signatureRawData = AppId + "GET" + parseURL(url) + timeStamp + nonce + contentHashBase64;

        //signatureRawData = "4d53bce03ec34c0a911182d4c228ee6cGEThttp://orderfooduit.azurewebsites.net/api/hinhthucthanhtoan/get1516358557de6569cb-1df6-4fe7-bcae-3a4de7730d7a";

        byte[] byteArrayFromCSharp = {3, (byte)221, (byte)235, 121, 20, (byte)212, 36, 123, 2, (byte)185
                , 4, (byte)135, 71, (byte)226, (byte)247, 27, 26, (byte)142, 39, 32
                , (byte)230, 66, (byte)144, (byte)160, (byte)166, (byte)205, 116, (byte)217, (byte)200, (byte)174
                , 105, (byte)183};
        String signatureBase64String = calculateHMAC(signatureRawData);

        HttpGet httpGet = new HttpGet(url);
        Log.d("HMAC 2", signatureBase64String);
        httpGet.addHeader("amx", AppId + ":" + signatureBase64String + ":" + nonce + ":" + timeStamp);

        HttpClient client = new DefaultHttpClient();

        //System.out.println("client response:" + response.getStatusLine().getStatusCode());
    }


    private String calculateHMAC(String data) {
        try {
            byte[] byteBase64 = org.apache.commons.codec.binary.Base64.decodeBase64(APIKey.getBytes());
            SecretKeySpec signingKey = new SecretKeySpec(byteBase64, HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            String result = Base64.encodeToString(rawHmac, Base64.CRLF);
            return result.replaceAll("(\\r|\\n)", "");
        } catch (GeneralSecurityException e) {
            Log.d("Error creating hash: ", e.getMessage());
            throw new IllegalArgumentException();
        }
    }
    private String parseURL(String api){
        return api.replaceAll(":", "%3a").replaceAll("/", "%2f");
    }

    private String calculateMD5(String contentToEncode) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(contentToEncode.getBytes());
        String result = new String(org.apache.commons.codec.binary.Base64.encodeBase64(digest.digest()));
        return result;
    }

    private static String generateNonce() throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException
    {
        String dateTimeString = Long.toString(new Date().getTime());
        byte[] nonceByte = dateTimeString.getBytes();
        return "";
    }
}