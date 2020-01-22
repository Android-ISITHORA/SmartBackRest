package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MedicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicationActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the application asks if the patient is in digestive medication or not the person " +
                            "can click “yes” or “no”.");
                }});
                startActivity(intent);
            }
        });
    }

    public void onYesClick(View view) {
        ApplicationData.getInstance().getUser().setOnDigestiveMedication(true);
        startActivity(new Intent(this, PsychologicalProfileActivity.class));
    }

    public void onNoClick(View view) {
        ApplicationData.getInstance().getUser().setOnDigestiveMedication(false);
        startActivity(new Intent(this, PsychologicalProfileActivity.class));
    }
}
