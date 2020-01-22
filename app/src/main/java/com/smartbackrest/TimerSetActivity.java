package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class TimerSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_set);

        ViewGroup v = findViewById(R.id.timePicker);

        try {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                ((LinearLayout) v.getChildAt(0)).getChildAt(4).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerSetActivity.this , HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page you can set the time at which you wish the bed to decline. The time you set here " +
                            "is in 12 hour format so you will need to set the time to am or pm.");
                    add("When you click on the button of 'continue' then the application will show confirmation of the time that you have" +
                            " selected. If you are satisfied with the time then you can click the start timer button to initiate the timer");
                }});
                startActivity(intent);
            }
        });
    }

    public void goToTimerActivity(View view) {
        TimePicker timePicker = findViewById(R.id.timePicker);

        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT < 23) {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
        }

        startActivity(new Intent(this, TimerActivity.class).putExtra("decline_time", calendar.getTimeInMillis()));
    }
}
