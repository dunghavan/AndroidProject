package com.example.dung.demo_recyclerview.viewpager_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dung on 12/10/2017.
 */

public class PayPal_Response {
    @JsonProperty("create_time")
    private String create_time;
    @JsonProperty("id")
    private String id;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("state")
    private String state;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
