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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Menu_Fragment class for displaying the restaurant menu and handling user selections.
 */
public class Menu_Fragment extends Fragment {

    // Lists to store selected food descriptions, prices, and quantities
    private final ArrayList<String> selectedDescriptions = new ArrayList<>();
    private final ArrayList<String> selectedPrices = new ArrayList<>();
    private final HashMap<String, Integer> selectedQuantities = new HashMap<>();

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                  The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Initialize the database handler
        DBHandler dbHandler = new DBHandler(getActivity());
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        // Find the layout where menu items will be added
        LinearLayout menuItemsLayout = view.findViewById(R.id.menu_items_layout);

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
                LinearLayout itemLayout = new LinearLayout(getActivity());
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create a new CheckBox for each menu item
                CheckBox checkBox = new CheckBox(getActivity());
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
                Spinner quantitySpinner = new Spinner(getActivity());
                Integer[] quantities = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, quantities) {
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
                TextView space = new TextView(getActivity());
                space.setText("\n");
                menuItemsLayout.addView(space);

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
                Intent orderIntent = new Intent(getActivity(), OrderActivity.class);
                orderIntent.putStringArrayListExtra("selectedDescriptions", selectedDescriptions);
                orderIntent.putStringArrayListExtra("selectedPrices", selectedPrices);
                orderIntent.putExtra("selectedQuantities", selectedQuantities);
                startActivity(orderIntent);
            }
        });

        return view;
    }
}