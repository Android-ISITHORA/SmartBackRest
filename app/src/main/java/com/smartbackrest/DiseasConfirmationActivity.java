package com.smartbackrest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class DiseasConfirmationActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseas_confirmation);

        context = this;
        if (!getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getBoolean("show_confirmation", true)) {
            startActivity(new Intent(context, FeatureSelectionActivity.class));
            finish();
        }

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiseasConfirmationActivity.this , HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("When you will click on the button “YES” it means that you do suffer from any of the " +
                            "following diseases like “Asthama, Appendicites, Gastroparesis”. If you suffer from any of the " +
                            "following conditions then you are not supposed to use these applications because using this kind of " +
                            "treatment could cause other kinds of complications thus the application will prompt you with a " +
                            "message of consulting a doctor before using this application.");
                    add("When you click on the button “NO” it means that you do not suffer from the following mentioned " +
                            "diseases thus the application will proceed to take you to the next page.");
                }});
                startActivity(intent);
            }
        });

    }

    public void onYesClick(View view) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        AlertDialog dialog = a_builder.setMessage("This device is not meant for patients suffering from these diseases.\n\nPlease consult with your doctor " +
                "before using this device. \n\nPress continue to use this device.")
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context, FeatureSelectionActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setTitle("Warning!").create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(30);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(30);
                    }
                });
                dialog.show();
    }

    public void onNoClick(View view) {
        getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit().putBoolean("show_confirmation", false).apply();
        startActivity(new Intent(context, FeatureSelectionActivity.class));
    }
}
