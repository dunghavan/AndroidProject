package com.example.dung.demo_recyclerview.viewpager_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dung on 12/10/2017.
 */

public class PayPal_Confirm {
    @JsonProperty("client")
    private PayPal_Client client;
    @JsonProperty("response")
    private PayPal_Response response;
    @JsonProperty("response_type")
    private String response_type;

    public PayPal_Client getPayPal_client() {
        return client;
    }

    public void setPayPal_client(PayPal_Client payPal_client) {
        this.client = payPal_client;
    }

    public PayPal_Response getPayPal_response() {
        return response;
    }

    public void setPayPal_response(PayPal_Response payPal_response) {
        this.response = payPal_response;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }
}
