package com.example.moobiledevelopmentca1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**

 Activity to handle the "Pay Now" option.
 */
public class PayNowActivity extends Activity {

    private EditText nameField;
    private EditText cardNumberField;
    private EditText expirationDateField;
    private EditText emailField;
    private TextView totalPriceTextView;
    private double totalPrice;
    private String address;

    /**

     Called when the activity is first created.
     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paynow);

        // Initialize UI elements
        nameField = findViewById(R.id.name_field);
        cardNumberField = findViewById(R.id.card_number_field);
        EditText cvvField = findViewById(R.id.cvv_field);
        expirationDateField = findViewById(R.id.expiration_date_field);
        emailField = findViewById(R.id.email_field);
        totalPriceTextView = findViewById(R.id.total_price);

        // Retrieve total price and address from intent
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        address = getIntent().getStringExtra("address");
        totalPriceTextView.setText("Total Price: €" + totalPrice);

        // Set up button click listeners
        Button payNowButton = findViewById(R.id.pay_now_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button homeButton = findViewById(R.id.home_button);

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String cardNumber = cardNumberField.getText().toString();
                String expirationDate = expirationDateField.getText().toString();
                String email = emailField.getText().toString();

                if (name.isEmpty() || cardNumber.isEmpty() || expirationDate.isEmpty() || email.isEmpty()) {
                    Toast.makeText(PayNowActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the user's name, address, and total price
                    // Explicit Intent to navigate to PaymentconfirmationActivity
                    Intent intent = new Intent(PayNowActivity.this, PaymentconfirmationActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("totalPrice", totalPrice);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MenuActivity
                Intent intent = new Intent(PayNowActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MainActivity
                Intent intent = new Intent(PayNowActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}