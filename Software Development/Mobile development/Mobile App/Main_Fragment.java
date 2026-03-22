package com.example.moobiledevelopmentca1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 Main_Fragment class represents the main fragment of the application.
 It contains buttons that navigate to different activities.
 */
public class Main_Fragment extends Fragment {

    /**
     Called to have the fragment instantiate its user interface view.
     @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Find the buttons by their IDs
        Button menuButton = view.findViewById(R.id.menu_button);
        Button specialsButton = view.findViewById(R.id.specials_button);
        Button feedbackButton = view.findViewById(R.id.feedback_button);

        // Set click listeners for each button to start the corresponding activity
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to start MenuActivity
                Intent menuIntent = new Intent(getActivity(), MenuActivity.class);
                startActivity(menuIntent);
            }
        });

        specialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to start SpecialActivity
                Intent specialsIntent = new Intent(getActivity(), SpecialActivity.class);
                startActivity(specialsIntent);
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent to start FeedbackActivity
                Intent feedbackIntent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(feedbackIntent);
            }
        });

        return view;

    }

}