package com.example.moobiledevelopmentca1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 FeedbackActivity class handles the feedback form where users can rate the app,
 provide navigation feedback, and suggest improvements.
 */
public class FeedbackActivity extends Activity {

    private RatingBar appRating;
    private RadioGroup navigationRadioGroup;
    private EditText changeDescription;
    private EditText layoutDescription;
    private EditText improvementDescription;
    private DBHandler dbHandler;

    /**
     Called when the activity is first created.
     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        // Initialize UI elements
        appRating = findViewById(R.id.app_rating);
        navigationRadioGroup = findViewById(R.id.navigation_radio_group);
        changeDescription = findViewById(R.id.change_description);
        layoutDescription = findViewById(R.id.layout_description);
        improvementDescription = findViewById(R.id.improvement_description);
        dbHandler = new DBHandler(this);

        // Set default rating to 5
        appRating.setRating(5);

        // Set up button click listeners
        Button submitButton = findViewById(R.id.submit_button);
        Button homeButton = findViewById(R.id.home_button);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        // Set click listener for the home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to navigate to MainActivity
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     Handles the submission of the feedback form.
     Validates the input and saves the feedback to the database.
     */
    private void handleSubmit() {
        int rating = (int) appRating.getRating();
        String navigation = getSelectedNavigationOption();
        String change = changeDescription.getText().toString();
        String layout = layoutDescription.getText().toString();
        String improvement = improvementDescription.getText().toString();

        // Check if all fields are filled
        if (navigation == null || change.isEmpty() || layout.isEmpty() || improvement.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save feedback to the database
        saveFeedback(rating, navigation, change, layout, improvement);

        // Return to the previous page
        finish();

    }

    /**
     Retrieves the selected navigation option from the RadioGroup.
     @return The selected navigation option as a String.
     */
    private String getSelectedNavigationOption() {
        int selectedId = navigationRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.yes_button) {
            return "Yes";
        } else if (selectedId == R.id.no_button) {
            return "No";
        } else if (selectedId == R.id.somewhat_button) {
            return "Somewhat";
        } else {
            return null;
        }
    }

    /**
     Saves the feedback to the database.
     @param rating The rating given by the user.
     @param navigation The navigation feedback.
     @param change The change description provided by the user.
     @param layout The layout description provided by the user.
     @param improvement The improvement suggestions provided by the user.
     */
    private void saveFeedback(int rating, String navigation, String change, String layout, String improvement) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rating", rating);
        values.put("navigation", navigation);
        values.put("change", change);
        values.put("layout", layout);
        values.put("improvement", improvement);

        long newRowId = db.insert("feedback", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
        }

    }

}