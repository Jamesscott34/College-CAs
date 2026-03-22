package com.example.moobiledevelopmentca1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

/**
 MainActivity class serves as the entry point of the application.
 It initializes the ViewPager2 component and sets up the database handler.
 */
public class MainActivity extends AppCompatActivity {

    /**
     Called when the activity is first created.
     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize ViewPager2 and set its adapter
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Initialize the database handler and get writable database
        DBHandler dbhandler = new DBHandler(this);
        dbhandler.getWritableDatabase();

    }

}