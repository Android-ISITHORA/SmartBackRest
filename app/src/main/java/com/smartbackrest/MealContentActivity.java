package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MealContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_content);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealContentActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the application asks the patient to enter the content of the meal which helps the " +
                            "application calculate the time of decline even in the smart bed backrest.");
                    add("When you click the button skip then the application will not take the inputs given in this page " +
                            "into consideration.");
                }});
                startActivity(intent);
            }
        });

        ((ChipGroup) findViewById(R.id.cGroupFried)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                boolean isFriedMeal = false;
                switch (checkedId) {

                    case R.id.chipFriedNS:
                    case R.id.chipFriedY: {
                        isFriedMeal = true;
                        break;
                    }

                    case R.id.chipFriedN: {
                        isFriedMeal = false;
                        break;
                    }
                }

                ApplicationData.getInstance().getMealContent().setFried(isFriedMeal);
            }
        });

        ((ChipGroup) findViewById(R.id.cGroupFruit)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                MealSize mealSize = MealSize.NONE;
                switch (checkedId) {
                    case R.id.chipFruitS: {
                        mealSize = MealSize.SMALL;
                        break;
                    }
                    case R.id.chipFruitM: {
                        mealSize = MealSize.MEDIUM;
                        break;
                    }
                    case R.id.chipFruitL: {
                        mealSize = MealSize.LARGE;
                        break;
                    }
                    case R.id.chipFruitN: {
                        mealSize = MealSize.NOT_SURE;
                        break;
                    }
                }

                ApplicationData.getInstance().getMealContent().setFruit(mealSize);
            }
        });

        ((ChipGroup) findViewById(R.id.cGroupProtein)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                MealSize mealSize = MealSize.NONE;
                switch (checkedId) {
                    case R.id.chipProteinS: {
                        mealSize = MealSize.SMALL;
                        break;
                    }
                    case R.id.chipProteinM: {
                        mealSize = MealSize.MEDIUM;
                        break;
                    }
                    case R.id.chipProteinL: {
                        mealSize = MealSize.LARGE;
                        break;
                    }
                    case R.id.chipProteinN: {
                        mealSize = MealSize.NOT_SURE;
                        break;
                    }
                }

                ApplicationData.getInstance().getMealContent().setProtein(mealSize);
            }
        });

        ((ChipGroup) findViewById(R.id.cGroupRice)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                MealSize mealSize = MealSize.NONE;
                switch (checkedId) {
                    case R.id.chipRiceS: {
                        mealSize = MealSize.SMALL;
                        break;
                    }
                    case R.id.chipRiceM: {
                        mealSize = MealSize.MEDIUM;
                        break;
                    }
                    case R.id.chipRiceL: {
                        mealSize = MealSize.LARGE;
                        break;
                    }
                    case R.id.chipRiceN: {
                        mealSize = MealSize.NOT_SURE;
                        break;
                    }
                }

                ApplicationData.getInstance().getMealContent().setRice(mealSize);
            }
        });

        ((ChipGroup) findViewById(R.id.cGroupSoup)).setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                MealSize mealSize = MealSize.NONE;
                switch (checkedId) {
                    case R.id.chipSoupS: {
                        mealSize = MealSize.SMALL;
                        break;
                    }
                    case R.id.chipSoupM: {
                        mealSize = MealSize.MEDIUM;
                        break;
                    }
                    case R.id.chipSoupL: {
                        mealSize = MealSize.LARGE;
                        break;
                    }
                    case R.id.chipSoupN: {
                        mealSize = MealSize.NOT_SURE;
                        break;
                    }
                }

                ApplicationData.getInstance().getMealContent().setSoup(mealSize);
            }
        });
    }

    public void goToTimerActivity(View view) {
        startActivity(new Intent(this, TimerActivity.class));
    }

    public void skipToTimerActivity(View view) {
        ApplicationData.getInstance().setMealToNone();
        startActivity(new Intent(this, TimerActivity.class));
    }
}
