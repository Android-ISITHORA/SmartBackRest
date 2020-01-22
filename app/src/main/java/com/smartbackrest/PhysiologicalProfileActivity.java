package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smartbackrest.db.DBManager;

import java.util.ArrayList;

public class PhysiologicalProfileActivity extends AppCompatActivity {

    private int phase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiological_profile);

        setView();

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysiologicalProfileActivity.this, HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the application asks if the person has had any stomach surgeries the person can " +
                            "choose any of the button respectively.");
                    add("When you click on any of the button then:" +
                            "The application asks if the person has any pain related issues.");
                }});
                startActivity(intent);
            }
        });
    }

    private void setView() {
        if (phase == 1) {
            ((TextView) findViewById(R.id.txtQuestion)).setText("Have you had surgeries related to stomach ?");
            findViewById(R.id.btnNotSure).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.txtQuestion)).setText("Do you have pain related issues ?");
            findViewById(R.id.btnNotSure).setVisibility(View.VISIBLE);
        }
    }

    public void onNotSureClick(View view) {
        if (phase == 1) {
            ApplicationData.getInstance().getUser().setHasStomachSurgeries(true);
            phase += 1;
            setView();
        } else {
            ApplicationData.getInstance().getUser().setHasPain(true);
            saveProfile();
        }

    }

    private void saveProfile() {
        final DBManager dbManager = new DBManager(this).open();
        if (dbManager.insertUser(ApplicationData.getInstance().getUser()) > 0) {
            String storedDevice = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getString("device_name", "");
            if (storedDevice.isEmpty()) {
                startActivity(new Intent(this, BLEDeviceSelectionActivity.class));
            } else {
                startActivity(new Intent(this, MealActivity.class));
            }

        } else {
            Toast.makeText(this, "Error creating profile, please try again!!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNoClick(View view) {
        if (phase == 1) {
            ApplicationData.getInstance().getUser().setHasStomachSurgeries(false);
            phase += 1;
            setView();
        } else {
            ApplicationData.getInstance().getUser().setHasPain(false);
            saveProfile();
        }
    }

    public void onYesClick(View view) {
        if (phase == 1) {
            ApplicationData.getInstance().getUser().setHasStomachSurgeries(true);
            phase += 1;
            setView();
        } else {
            ApplicationData.getInstance().getUser().setHasPain(true);
            saveProfile();
        }
    }
}
