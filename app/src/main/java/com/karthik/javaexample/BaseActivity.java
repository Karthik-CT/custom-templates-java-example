package com.karthik.javaexample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements DisplayUnitListener {

    CleverTapAPI cleverTapAPI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Common setup for all activities
        setupListeners();
    }

    protected void setupListeners() {
        // Implement common event listeners if needed
        cleverTapAPI =  CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.setDisplayUnitListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Can be used for analytics tracking, session management, etc.
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Handle pause state, if needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cleanup resources if required
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {

    }
}
