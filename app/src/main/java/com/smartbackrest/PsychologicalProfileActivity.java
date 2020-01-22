package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class PsychologicalProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychological_profile);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PsychologicalProfileActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the applciation asks if the patient is suffering from any anxiety issues or not the " +
                            "person can click “yes”, “no” or “not sure” respectively.");
                }});
                startActivity(intent);
            }
        });
    }

    public void onCantSayClick(View view) {
        ApplicationData.getInstance().getUser().setHasAnxietyIssues(true);
        startActivity(new Intent(this, PhysiologicalProfileActivity.class));
    }

    public void onNoClick(View view) {
        ApplicationData.getInstance().getUser().setHasAnxietyIssues(false);
        startActivity(new Intent(this, PhysiologicalProfileActivity.class));
    }

    public void onYesClick(View view) {
        ApplicationData.getInstance().getUser().setOnDigestiveMedication(true);
        startActivity(new Intent(this, PhysiologicalProfileActivity.class));
    }


}
