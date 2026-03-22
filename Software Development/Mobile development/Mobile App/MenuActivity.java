package com.example.moobiledevelopmentca1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MenuActivity class displays the menu items from the database and allows users to select items and quantities.
 * It uses explicit intents to navigate between activities.
 */

public class MenuActivity extends Activity {

    private final ArrayList<String> selectedDescriptions = new ArrayList<>();
    private final ArrayList<String> selectedPrices = new ArrayList<>();
    private final HashMap<String, Integer> selectedQuantities = new HashMap<>();


    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Initialize the database handler
        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        // Find the layout where menu items will be added
        LinearLayout menuItemsLayout = findViewById(R.id.menu_items_layout);

        // Query the menu table
        Cursor cursor = db.query("menu", null, null, null, null, null, null);

        // Iterate through the cursor and add each item to the layout
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String foodType = cursor.getString(cursor.getColumnIndex("foodtype"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String allergies = cursor.getString(cursor.getColumnIndex("allergies"));
                @SuppressLint("Range") double pricing = cursor.getDouble(cursor.getColumnIndex("pricing"));

                // Create a new LinearLayout to hold the CheckBox and Spinner
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create a new CheckBox for each menu item
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(foodType + "\n" + description + "\nAllergies: " + allergies + "\nPrice: €" + pricing);
                checkBox.setTextSize(18); // Set text size
                checkBox.setTextColor(getResources().getColor(android.R.color.white));
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {
                            selectedDescriptions.add(description);
                            selectedPrices.add(String.valueOf(pricing));
                            selectedQuantities.put(description, 1); // Default quantity
                        } else {
                            selectedDescriptions.remove(description);
                            selectedPrices.remove(String.valueOf(pricing));
                            selectedQuantities.remove(description);
                        }
                    }
                });

                // Create a Spinner for selecting quantity
                Spinner quantitySpinner = new Spinner(this);
                Integer[] quantities = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, quantities) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                        ((TextView) view).setTextSize(18); // Set text size to make it larger
                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                        ((TextView) view).setTextSize(18); // Set text size to make it larger
                        return view;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                quantitySpinner.setAdapter(adapter);
                quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (checkBox.isChecked()) {
                            selectedQuantities.put(description, (int) parent.getItemAtPosition(position));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Do nothing
                    }
                });

                // Add the CheckBox and Spinner to the item layout
                itemLayout.addView(checkBox);
                itemLayout.addView(quantitySpinner);

                // Add the item layout to the menu layout
                menuItemsLayout.addView(itemLayout);

                // Add a space between items
                TextView space = new TextView(this);
                space.setText("\n");
                menuItemsLayout.addView(space);

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Find the buttons by their IDs
        Button homeButton = findViewById(R.id.home_button);
        Button orderButton = findViewById(R.id.order_button);

        // Set click listener for the Home button to return to MainActivity
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        // Set click listener for the Order button to go to OrderActivity
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(MenuActivity.this, OrderActivity.class);
                orderIntent.putStringArrayListExtra("selectedDescriptions", selectedDescriptions);
                orderIntent.putStringArrayListExtra("selectedPrices", selectedPrices);
                orderIntent.putExtra("selectedQuantities", selectedQuantities);
                startActivity(orderIntent);
            }
        });
    }
}