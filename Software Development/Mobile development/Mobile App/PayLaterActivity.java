package com.example.moobiledevelopmentca1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**

 Activity to handle the "Pay Later" option.
 */
public class PayLaterActivity extends Activity {

    private TextView orderNumberTextView;
    private TextView totalPriceTextView;
    private TextView estimatedDeliveryTimeTextView;
    private TextView addressTextView;
    private double totalPrice;
    private String address;

    /**

     Called when the activity is first created.


     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paylater);

        // Initialize UI elements
        orderNumberTextView = findViewById(R.id.order_number);
        totalPriceTextView = findViewById(R.id.total_price);
        estimatedDeliveryTimeTextView = findViewById(R.id.estimated_delivery_time);
        addressTextView = findViewById(R.id.address_text);

        // Retrieve total price and address from intent
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        address = getIntent().getStringExtra("address");
        totalPriceTextView.setText("Total Price: €" + totalPrice);
        addressTextView.setText("Address: " + address);

        // Generate random order number
        Random random = new Random();
        int orderNumber = random.nextInt(900000) + 100000;
        orderNumberTextView.setText("Order Number: " + orderNumber);

        // Generate random delivery time between 35 and 45 minutes
        int deliveryTime = random.nextInt(11) + 35; // Random time between 35 and 45 minutes
        startCountdownTimer(deliveryTime);

        // Set up button click listeners
        Button feedbackButton = findViewById(R.id.feedback_button);
        Button mainButton = findViewById(R.id.main_button);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to FeedbackActivity
                Intent intent = new Intent(PayLaterActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MainActivity
                Intent intent = new Intent(PayLaterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**

     Starts a countdown timer for the estimated delivery time.


     @param minutes The number of minutes for the countdown.
     */
    private void startCountdownTimer(int minutes) {
        long millis = (long) minutes * 60 * 1000; // Convert minutes to milliseconds

        new CountDownTimer(millis, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                estimatedDeliveryTimeTextView.setText("Estimated Delivery Time: " + time);
            }

            @Override
            public void onFinish() {
                estimatedDeliveryTimeTextView.setText("Estimated Delivery Time: 00:00");
            }
        }.start();

    }

}