package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this , HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("The is the page to show the decline process has been successfully finished");

                }});
                startActivity(intent);
            }
        });
    }
}
