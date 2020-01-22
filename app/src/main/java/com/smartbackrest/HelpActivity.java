package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    ArrayList<String> helpStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("list")) {
                    helpStrings = bundle.getStringArrayList("list");
                } else finish();
            }
        }

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

        for (String helpString : helpStrings) {
            View view1 = LayoutInflater.from(HelpActivity.this).inflate(R.layout.help_textview, null, false);
            ((TextView) view1.findViewById(R.id.txtHelp)).setText(helpString);
            ((LinearLayout) findViewById(R.id.mainLinear)).addView(view1);
        }
    }
}
