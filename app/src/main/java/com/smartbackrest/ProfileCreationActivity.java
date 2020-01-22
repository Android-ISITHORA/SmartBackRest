package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProfileCreationActivity extends AppCompatActivity {

    private static final String TAG = "ProfileCreationActivity";

    private Context context;
    private long selectedDate = -1L;
    private boolean isMale, isFemale;
    private Chip chipMale, chipFemale;
    private boolean useSlider = true;
    private int age = -1;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        context = this;

        TextInputLayout textInputCustomEndIcon = findViewById(R.id.layoutBirthDate);
        textInputCustomEndIcon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(year, month, day);

                                selectedDate = calendar1.getTimeInMillis();
                                ((EditText) findViewById(R.id.edtBirthDate)).setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        chipMale = (Chip) findViewById(R.id.chipMale);
        chipFemale = (Chip) findViewById(R.id.chipFemale);
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();
        list.add("10 - 30");
        list.add("31 - 50");
        list.add("51 - 70");
        list.add("71 - 90");
        list.add("90 - 120");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_age_group, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_age_group);
        spinner.setAdapter(dataAdapter);

        if (useSlider) {
            findViewById(R.id.number_picker).setVisibility(View.GONE);
            findViewById(R.id.txtSelectAge).setVisibility(View.VISIBLE);

            (findViewById(R.id.layoutBirthDate)).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.number_picker).setVisibility(View.GONE);
            findViewById(R.id.txtSelectAge).setVisibility(View.GONE);

            (findViewById(R.id.layoutBirthDate)).setVisibility(View.VISIBLE);
        }

        ((NumberPicker) findViewById(R.id.number_picker)).setOnValueChangedListener((picker, oldVal, newVal) -> age = newVal);


        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileCreationActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the application asks for the basic details of the patient and the date of birth which is" +
                            " very important to enter as it is a part of calculating the time of the decline event of the Smart Bed\n" +
                            "Backrest.");
                }});
                startActivity(intent);
            }
        });
    }

    public void goForMedicalProfile(View view) {
        String fName = ((EditText) findViewById(R.id.edtFName)).getText().toString();
        String lName = ((EditText) findViewById(R.id.edtLName)).getText().toString();
        String ageET = ((EditText) findViewById(R.id.edtBirthDate)).getText().toString();

        if (!useSlider)
            Log.i(TAG, String.format("goForMedicalProfile: age is %s", ((EditText) findViewById(R.id.edtBirthDate)).getText().toString()));
        else
            Log.i(TAG, String.format("goForMedicalProfile: age is %s", age));

        if (!fName.isEmpty()) {
            if (!lName.isEmpty()) {

                if (!useSlider) {
                    if (!ageET.isEmpty() && Integer.valueOf(ageET) > 10) {
                        verifyGenderAndGoToNext(fName, lName, Integer.valueOf(ageET));
                    } else {
                        Toast.makeText(context, "Please enter valid age", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (age != -1 && age > 10) {
                        verifyGenderAndGoToNext(fName, lName, age);
                    } else {
                        Toast.makeText(context, "Please enter valid age", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(context, "Last name required", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "First name required", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyGenderAndGoToNext(String fName, String lName, int age) {
        if (chipMale.isChecked() || chipFemale.isChecked()) {
            ApplicationData.getInstance().getUser().setFirstName(fName).setLastName(lName).setDob(age).setGender(chipMale.isChecked() ? 1 : 0);
            startActivity(new Intent(this, MedicalProfileActivity.class));
        } else {
            Toast.makeText(context, "Please select your gender", Toast.LENGTH_SHORT).show();
        }
    }
}
