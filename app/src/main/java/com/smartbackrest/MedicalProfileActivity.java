package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class MedicalProfileActivity extends AppCompatActivity {

    private boolean hasDiabetes, hasAsthama, hasParkinsons, hasScleroderma, hasMultipleSclerosis;
    private boolean allowBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_profile);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalProfileActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the application asks if the patient suffers from the following diseases which are " +
                            "necessary to calculate the time of decline.");
                }});
                startActivity(intent);
            }
        });

        ((Chip) findViewById(R.id.chipDiabetes)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hasDiabetes = b;
            }
        });
        ((Chip) findViewById(R.id.chipAsthama)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hasAsthama = b;
            }
        });
        ((Chip) findViewById(R.id.chipParkinsons)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hasParkinsons = b;
            }
        });
        ((Chip) findViewById(R.id.chipScleroderma)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hasScleroderma = b;
            }
        });
        ((Chip) findViewById(R.id.chipMultipleSclerosis)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hasMultipleSclerosis = b;
            }
        });
    }

    public void goToMedicationProfile(View view) {
        ApplicationData.getInstance().getUser()
                .setHasAsthama(hasAsthama)
                .setHasDiabetes(hasDiabetes)
                .setHasParkinsons(hasParkinsons)
                .setHasScleroderma(hasScleroderma)
                .setHasMultipleSclerosis(hasMultipleSclerosis);
        startActivity(new Intent(this, MedicationActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
