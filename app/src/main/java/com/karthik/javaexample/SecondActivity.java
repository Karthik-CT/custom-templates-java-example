package com.karthik.javaexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.ct_templates.nd.coachmark.CoachMarkHelper;
import java.util.ArrayList;
import java.util.Map;

public class SecondActivity extends BaseActivity {

    CleverTapAPI cleverTapAPI;

    //Define CleverTapDisplayUnit unit as a global variable
    CleverTapDisplayUnit unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CleverTapAPI.setDebugLevel(3);

        cleverTapAPI =  CleverTapAPI.getDefaultInstance(getApplicationContext());

        //for example we have called it on click of textview you have to call prepareDisplayView(unit) once your Activity gets rendered to the fullest
        findViewById(R.id.frame_layout_restaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareDisplayView(unit);
            }
        });
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        System.out.println("onDisplayUnitsLoaded: " + units);
        if (units != null) {
            for (int i = 0; i < units.size(); i++) {
                // store the value of unit in the global variable
                unit = units.get(i);
                System.out.println("unit: " + unit);
            }
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        System.out.println("unit CM: " + unit.getJsonObject());

        Map<String, String> customExtras = unit.getCustomExtras();
        if ("nd_coachmarks".equals(customExtras.get("nd_id"))) {
            CleverTapAPI clevertapInstance = CleverTapAPI.getDefaultInstance(this);
            if (clevertapInstance != null) {
                clevertapInstance.pushDisplayUnitViewedEventForID(unit.getUnitID());
            }

            new CoachMarkHelper().renderCoachMark(this, unit.getJsonObject(), () -> {
                CleverTapAPI clevertapInstanceClicked = CleverTapAPI.getDefaultInstance(this);
                if (clevertapInstanceClicked != null) {
                    clevertapInstanceClicked.pushDisplayUnitClickedEventForID(unit.getUnitID());
                }
                return null;
            });
        }
        else {
            Log.d("SecondActivity", "I am from else block of nd_id");
            System.out.println("NA");
        }
    }
}