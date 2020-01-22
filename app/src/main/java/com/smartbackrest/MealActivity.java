package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;

public class MealActivity extends AppCompatActivity {

    private static final String TAG = "MealActivity";
    private MealType mealType = MealType.NOT_SURE;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("The application after saving the profile asks the user to enter the type of food the person ate " +
                            "and at what time the person ate the food which is in twelve hour format.");
                }});
                startActivity(intent);
            }
        });

        ((ChipGroup) findViewById(R.id.mealTypeGroup)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chipSolid: {
                        mealType = MealType.SOLID;
                        break;
                    }
                    case R.id.chipLiquid: {
                        mealType = MealType.LIQUID;
                        break;
                    }
                    case R.id.chipSemiSolid: {
                        mealType = MealType.SEMI_SOLID;
                        break;
                    }
                    default: {
                        mealType = MealType.NOT_SURE;
                        break;
                    }
                }
            }
        });
        timePicker = findViewById(R.id.timePicker);
    }

    public void goToTimerActivity(View view) {
        ApplicationData.getInstance().setMealType(mealType);
        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT < 23) {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
        }

        ApplicationData.getInstance().setMealTime(calendar.getTimeInMillis());
        startActivity(new Intent(this, MealContentActivity.class));
    }
}
