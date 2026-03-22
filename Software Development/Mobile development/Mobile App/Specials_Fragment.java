package com.example.moobiledevelopmentca1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**

 A fragment to display daily specials.
 */
public class Specials_Fragment extends Fragment {

    private String selectedFoodType;
    private double selectedPrice;
    private int selectedQuantity = 1; // Default quantity
    private RadioButton lastCheckedRadioButton = null;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specials, container, false);

        // Initialize the database handler
        DBHandler dbHandler = new DBHandler(getActivity());
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        // Find the layout where specials items will be added
        RadioGroup specialsItemsLayout = view.findViewById(R.id.specials_items_layout);

        // Query the specials table
        Cursor cursor = db.query("specials", null, null, null, null, null, null);

        // Iterate through the cursor and add each item to the layout
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String day = cursor.getString(cursor.getColumnIndex("day"));
                @SuppressLint("Range") String foodType = cursor.getString(cursor.getColumnIndex("foodtype"));
                @SuppressLint("Range") String allergies = cursor.getString(cursor.getColumnIndex("allergies"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));

                // Create a new LinearLayout to hold the RadioButton and Spinner
                LinearLayout itemLayout = new LinearLayout(getActivity());
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create a new RadioButton for each specials item
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setText(day + "\n" + foodType + "\nAllergies: " + allergies + "\nPrice: €" + String.format("%.2f", price));
                radioButton.setTextColor(getResources().getColor(android.R.color.white));
                radioButton.setTextSize(18); // Set text size to make it larger
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lastCheckedRadioButton != null && lastCheckedRadioButton == radioButton) {
                            radioButton.setChecked(false);
                            selectedFoodType = null;
                            selectedPrice = 0;
                            lastCheckedRadioButton = null;
                        } else {
                            selectedFoodType = foodType;
                            selectedPrice = price;
                            if (lastCheckedRadioButton != null) {
                                lastCheckedRadioButton.setChecked(false);
                            }
                            lastCheckedRadioButton = radioButton;
                        }
                    }
                });

                // Create a Spinner for selecting quantity
                Spinner quantitySpinner = new Spinner(getActivity());
                Integer[] quantities = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, quantities) {
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
                        selectedQuantity = (int) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedQuantity = 1; // Default quantity
                    }
                });

                // Add the RadioButton and Spinner to the item layout
                itemLayout.addView(radioButton);
                itemLayout.addView(quantitySpinner);

                // Add the item layout to the specials layout
                specialsItemsLayout.addView(itemLayout);

                // Add a space between items
                TextView space = new TextView(getActivity());
                space.setText("\n");
                specialsItemsLayout.addView(space);

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Find the buttons by their IDs
        Button homeButton = view.findViewById(R.id.home_button);
        Button orderButton = view.findViewById(R.id.order_button);

        // Set click listener for the Home button to return to MainActivity
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        // Set click listener for the Order button to go to OrderActivity
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFoodType != null) {
                    Intent orderIntent = new Intent(getActivity(), OrderActivity.class);
                    orderIntent.putExtra("selectedFoodType", selectedFoodType);
                    orderIntent.putExtra("selectedPrice", selectedPrice);
                    orderIntent.putExtra("selectedQuantity", selectedQuantity);
                    startActivity(orderIntent);
                }
            }
        });

        return view;
    }

}