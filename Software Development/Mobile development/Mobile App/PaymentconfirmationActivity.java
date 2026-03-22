package com.example.moobiledevelopmentca1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**

 Activity to handle the payment confirmation.
 */
public class PaymentconfirmationActivity extends Activity {

    private TextView nameTextView;
    private TextView totalPriceTextView;
    private TextView estimatedDeliveryTimeTextView;
    private TextView orderNumberTextView;
    private TextView paidTextView;
    private TextView addressTextView;
    private String name;
    private double totalPrice;
    private String address;

    /**

     Called when the activity is first created.


     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentconfirmation);

        // Initialize UI elements
        nameTextView = findViewById(R.id.name_text);
        totalPriceTextView = findViewById(R.id.total_price_text);
        estimatedDeliveryTimeTextView = findViewById(R.id.estimated_delivery_time_text);
        orderNumberTextView = findViewById(R.id.order_number_text);
        paidTextView = findViewById(R.id.paid_text);
        addressTextView = findViewById(R.id.address_text);

        // Retrieve data from intent
        name = getIntent().getStringExtra("name");
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        address = getIntent().getStringExtra("address");

        // Set data to UI elements
        nameTextView.setText("Name: " + name);
        totalPriceTextView.setText("Total Price: €" + totalPrice);
        addressTextView.setText("Address: " + address);

        // Generate random order number
        Random random = new Random();
        int orderNumber = random.nextInt(900000) + 100000;
        orderNumberTextView.setText("Order Number: " + orderNumber);

        // Generate random delivery time between 35 and 45 minutes
        int deliveryTime = random.nextInt(11) + 35; // Random time between 35 and 45 minutes
        startCountdownTimer(deliveryTime);

        // Set paid text
        paidTextView.setText("Paid");

        // Set up button click listeners
        Button homeButton = findViewById(R.id.home_button);
        Button feedbackButton = findViewById(R.id.feedback_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MainActivity
                Intent intent = new Intent(PaymentconfirmationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to FeedbackActivity
                Intent intent = new Intent(PaymentconfirmationActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

    }

    /**

     Starts a countdown timer for the estimated delivery time.
     @param minutes The number of minutes for the countdown.
     */
    private void startCountdownTimer(int minutes) {
        long millisInFuture = minutes * 60 * 1000; // Convert minutes to milliseconds
        new CountDownTimer(millisInFuture, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                estimatedDeliveryTimeTextView.setText(String.format("Estimated Delivery Time: %02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                estimatedDeliveryTimeTextView.setText("Estimated Delivery Time: 00:00");
            }
        }.start();

    }

}