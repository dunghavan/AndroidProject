package com.example.dung.demo_recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.viewpager_adapter.PayPal_Confirm;
import com.example.dung.demo_recyclerview.viewpager_adapter.PayPal_Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MyPaymentActivity extends AppCompatActivity {

    public interface OnSendSubmitListener{
        void sendSubmitToServer(String paymentType, String payID);
    }
    static OnSendSubmitListener listener;
    public void setOnSendSubmitListener(OnSendSubmitListener _listener){
        this.listener = _listener;
    }

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    //Client_id sandbox: Ab_Og1wZ6BPfn4tS5HeEZQX9MYUReg8WM2mKVfKOa2dHas3XsfiwQ10G-q2O1Nx-TwuQRQzxJtL_QyKz
    //Client_id old: AVZUbOX3ry-gyvTBVykh9TnK1v49hM0ycQiquryr8NjuRwnayplCFEm1M4ZnK5Q9JCcWzn5_briWUeRH
    // note that these credentials will differ between live & sandbox
    // environments.
    private static final String CONFIG_CLIENT_ID = "Ab_Og1wZ6BPfn4tS5HeEZQX9MYUReg8WM2mKVfKOa2dHas3XsfiwQ10G-q2O1Nx-TwuQRQzxJtL_QyKz";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static Double amount = 0.0;
    private static Double ratio = 0.000044;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // the following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;
    TextView tv_amount;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Thanh toán qua cổng PayPal");

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        Bundle bundle = getIntent().getExtras(); //get total amount form MapActivity
        amount = bundle.getDouble("TOTAL");

        DecimalFormat df = new DecimalFormat("0.00");
        double finalValue = 0;
        String formate = df.format(amount * ratio);
        try{
            finalValue = (Double)df.parse(formate) ;
        }
        catch (Exception e){
            Log.d("Error convert amount", "PaypalActivity");
        }


        tv_amount = (TextView)findViewById(R.id.textview_amount);
        tv_amount.setText(amount.toString() + "VNĐ" + " (" + finalValue + " USD)");
        findViewById(R.id.order).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                thingToBuy = new PayPalPayment(new BigDecimal(amount * ratio), "USD",
                        "Tổng thanh toán", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(MyPaymentActivity.this,
                        PaymentActivity.class);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }
        });

    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(MyPaymentActivity.this,
                PayPalFuturePaymentActivity.class);

        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    PayPal_Confirm payPal_confirm;
                    PayPal_Response payPal_response;
                    String idOfResponse = "";
                    try {
                        String confirmString = confirm.toJSONObject().toString(4);
                        Log.d("Confirm", confirmString);
                        Log.d("Confirm.getPayment()", confirm.getPayment().toJSONObject()
                                .toString(4));

                        Toast.makeText(getApplicationContext(), "Đã thanh toán thành công",
                                Toast.LENGTH_LONG).show();
                        try{
                            ObjectMapper objectMapper = new ObjectMapper();
                            payPal_confirm = objectMapper.readValue(confirmString, PayPal_Confirm.class);
                            payPal_response = payPal_confirm.getPayPal_response();
                            idOfResponse += payPal_response.getId();
                        }
                        catch (Exception e){
                            Log.d("ErrorParseConfirm", e.getMessage());
                        }
                        //Callback to MapActivity:
                        if(listener != null){
                            listener.sendSubmitToServer("Thanh toán qua cổng PayPal", idOfResponse);
                        }
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);

        // TODO: Send correlationId and transaction details to your server for
        // processing with
        // PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
