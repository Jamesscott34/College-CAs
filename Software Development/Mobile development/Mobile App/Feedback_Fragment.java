package com.example.moobiledevelopmentca1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment for collecting user feedback.
 */
public class Feedback_Fragment extends Fragment {

    private RatingBar appRating;
    private RadioGroup navigationRadioGroup;
    private EditText changeDescription;
    private EditText layoutDescription;
    private EditText improvementDescription;
    private DBHandler dbHandler;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        // Initialize UI elements
        appRating = view.findViewById(R.id.app_rating);
        navigationRadioGroup = view.findViewById(R.id.navigation_radio_group);
        changeDescription = view.findViewById(R.id.change_description);
        layoutDescription = view.findViewById(R.id.layout_description);
        improvementDescription = view.findViewById(R.id.improvement_description);
        dbHandler = new DBHandler(getActivity());

        // Set default rating to 5
        appRating.setRating(5);

        // Set up button click listeners
        Button submitButton = view.findViewById(R.id.submit_button);
        Button homeButton = view.findViewById(R.id.home_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit intent to navigate to MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Handles the submission of the feedback form.
     */
    private void handleSubmit() {
        int rating = (int) appRating.getRating();
        String navigation = getSelectedNavigationOption();
        String change = changeDescription.getText().toString();
        String layout = layoutDescription.getText().toString();
        String improvement = improvementDescription.getText().toString();

        // Check if all fields are filled
        if (navigation == null || change.isEmpty() || layout.isEmpty() || improvement.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save feedback to the database
        saveFeedback(rating, navigation, change, layout, improvement);

        // Return to the previous page
        getActivity().finish();
    }

    /**
     * Retrieves the selected navigation option from the RadioGroup.
     *
     * @return The selected navigation option as a String.
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
     * Saves the feedback to the database.
     *
     * @param rating The rating given by the user.
     * @param navigation The navigation feedback.
     * @param change The change description provided by the user.
     * @param layout The layout description provided by the user.
     * @param improvement The improvement suggestions provided by the user.
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
            Toast.makeText(getActivity(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Error submitting feedback", Toast.LENGTH_SHORT).show();
        }
    }
}