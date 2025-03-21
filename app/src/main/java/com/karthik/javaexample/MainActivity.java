package com.karthik.javaexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.ct_templates.nd.coachmark.CoachMarkHelper;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DisplayUnitListener {

    CleverTapAPI cleverTapAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CleverTapAPI.setDebugLevel(3);

        cleverTapAPI =  CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.setDisplayUnitListener(this);

        findViewById(R.id.btn_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("coachmarks_nd");
                try {
                    Thread.sleep(5000);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        System.out.println("onDisplayUnitsLoaded: " + units);
        if (units != null) {
            for (int i = 0; i < units.size(); i++) {
                CleverTapDisplayUnit unit = units.get(i);
                System.out.println("unit: " + unit);
                prepareDisplayView(unit);
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
        } else {
            System.out.println("NA");
        }
    }
}