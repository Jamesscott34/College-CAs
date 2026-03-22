package com.example.moobiledevelopmentca1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**

 Activity to display the order summary.
 */
public class OrderActivity extends Activity {

    /**

     Called when the activity is first created.


     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        // Get the selected descriptions, prices, and quantities from the intent
        ArrayList<String> selectedDescriptions = getIntent().getStringArrayListExtra("selectedDescriptions");
        ArrayList<String> selectedPrices = getIntent().getStringArrayListExtra("selectedPrices");
        HashMap<String, Integer> selectedQuantities = (HashMap<String, Integer>) getIntent().getSerializableExtra("selectedQuantities");

        // Get the selected special from the intent
        String selectedSpecialFoodType = getIntent().getStringExtra("selectedFoodType");
        double selectedSpecialPrice = getIntent().getDoubleExtra("selectedPrice", 0);
        int selectedSpecialQuantity = getIntent().getIntExtra("selectedQuantity", 1);

        // Find the layout where order items will be added
        LinearLayout orderItemsLayout = findViewById(R.id.order_items_layout);

        // Display the selected menu items
        double totalPrice = 0;
        if (selectedDescriptions != null && selectedPrices != null && selectedQuantities != null) {
            for (int i = 0; i < selectedDescriptions.size(); i++) {
                String description = (String) selectedDescriptions.get(i);
                String price = (String) selectedPrices.get(i);
                double priceValue = Double.parseDouble(price);
                int quantity = selectedQuantities.get(description);
                totalPrice += priceValue * quantity;

                // Create a new TextView for each order item
                TextView textView = new TextView(this);
                textView.setText(description + " - €" + String.format("%.2f", priceValue) + " x " + quantity);
                textView.setTextSize(18); // Set text size
                textView.setTextColor(getResources().getColor(android.R.color.white));

                // Add the TextView to the layout
                orderItemsLayout.addView(textView);
            }
        }

        // Display the selected special item
        if (selectedSpecialFoodType != null) {
            // Create a new TextView for the selected special
            TextView specialTextView = new TextView(this);
            specialTextView.setText(selectedSpecialFoodType + " - €" + String.format("%.2f", selectedSpecialPrice) + " x " + selectedSpecialQuantity);
            specialTextView.setTextSize(18); // Set text size
            specialTextView.setTextColor(getResources().getColor(android.R.color.white));

            // Add the TextView to the layout
            orderItemsLayout.addView(specialTextView);

            // Add the special price to the total price
            totalPrice += selectedSpecialPrice * selectedSpecialQuantity;
        }

        // Display the total price
        TextView totalPriceTextView = new TextView(this);
        totalPriceTextView.setText("Total Price: €" + String.format("%.2f", totalPrice));
        totalPriceTextView.setTextSize(20); // Set text size
        totalPriceTextView.setTextColor(getResources().getColor(android.R.color.white));
        orderItemsLayout.addView(totalPriceTextView);

        // Find the address field
        EditText addressField = findViewById(R.id.address_field);

        // Set up the Home button to return to MainActivity
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MainActivity
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set up the Pay Now button to open PayNowActivity
        Button payNowButton = findViewById(R.id.pay_now_button);
        double finalTotalPrice = totalPrice;
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressField.getText().toString();
                saveAddress(address);

                // Explicit Intent to navigate to PayNowActivity
                Intent intent = new Intent(OrderActivity.this, PayNowActivity.class);
                intent.putExtra("totalPrice", finalTotalPrice);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });

        // Set up the Pay Later button to open PayLaterActivity
        Button payLaterButton = findViewById(R.id.pay_later_button);
        double finalTotalPrice1 = totalPrice;
        payLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressField.getText().toString();
                saveAddress(address);

                // Explicit Intent to navigate to PayLaterActivity
                Intent intent = new Intent(OrderActivity.this, PayLaterActivity.class);
                intent.putExtra("totalPrice", finalTotalPrice1);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });

    }

    /**

     Saves the address to SharedPreferences.
     @param address The address to save.
     */
    private void saveAddress(String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.moobiledevelopmentca1.PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("address", address);
        editor.apply();
    }

}