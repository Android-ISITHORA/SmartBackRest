package com.smartbackrest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "TimerActivity";
    private View btnSkip, btnQuit;
    private TextView txtHour, txtMinute, txtSeconds, txtAmPm;
    private long declineTime = -1;
    private Calendar finalCalendar;
    private View btnStartTimer;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothChatService mChatService;
    private StringBuffer mOutStringBuffer;
    private StringBuffer mInStringBuffer;
    private Context context;
    private String deviceName, deviceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        context = this;
        btnSkip = findViewById(R.id.btnSkip);
        btnQuit = findViewById(R.id.btnQuit);
        btnSkip.setVisibility(View.GONE);
        btnQuit.setVisibility(View.GONE);

        txtHour = findViewById(R.id.txtHour);
        txtMinute = findViewById(R.id.txtMinute);
        txtSeconds = findViewById(R.id.txtSeconds);
        txtAmPm = findViewById(R.id.txtAmPm);
        btnStartTimer = findViewById(R.id.btnStartTimer);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        // Initialize the buffer for incoming messages
        mInStringBuffer = new StringBuffer("");


        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("decline_time")) {
                    declineTime = bundle.getLong("decline_time");
                }
            }
        }

        deviceName = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getString("device_name", "");
        deviceAddress = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getString("address", "");

        if (declineTime == -1) {
            int minutes = new TransitTimeCalculationAlgo()
                    .calculateTime(ApplicationData.getInstance().getUser(),
                            ApplicationData.getInstance().getMealType(),
                            ApplicationData.getInstance().getMealContent());
            finalCalendar = Calendar.getInstance();
            finalCalendar.setTimeInMillis(ApplicationData.getInstance().getMealTime());
            finalCalendar.add(Calendar.MINUTE, minutes);

            txtHour.setText(String.format("%02d", finalCalendar.get(Calendar.HOUR)));
            txtMinute.setText(String.format("%02d", finalCalendar.get(Calendar.MINUTE)));
            txtSeconds.setText(String.format("%02d", finalCalendar.get(Calendar.SECOND)));
            txtAmPm.setText(finalCalendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
            Log.i(TAG, String.format("onCreate: decline time is %s", android.text.format.DateFormat.format("hh:mm:ss a", (finalCalendar.getTime()))));
        } else {
            finalCalendar = Calendar.getInstance();
            finalCalendar.setTimeInMillis(declineTime);

            txtHour.setText(String.format("%02d", finalCalendar.get(Calendar.HOUR)));
            txtMinute.setText(String.format("%02d", finalCalendar.get(Calendar.MINUTE)));
            txtSeconds.setText(String.format("%02d", finalCalendar.get(Calendar.SECOND)));
            txtAmPm.setText(finalCalendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getBoolean("demo_mode", false)) {
                    send(String.valueOf(finalCalendar.getTimeInMillis()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(context, SuccessActivity.class));
                        }
                    }, 2 * 1000);
                } else
                    startActivity(new Intent(context, SuccessActivity.class));
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                a_builder.setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(context, FeatureSelectionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Alert !!!")
                        .show();

            }
        });


        findViewById(R.id.layoutHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerActivity.this , HelpActivity.class);
                intent.putStringArrayListExtra("list", new ArrayList<String>() {{
                    add("In this page the the application asks for the confirmation of the time that you have set" +
                            ". If you are satisfied with the time then you can click on the 'start timer' button to initiate the timer");
                    add("After you click on the start timer button the count down timer gets initiated:" +
                            "This page is the countdown timer which shows in how much time the Smart Bed Backrest " +
                            "will initiate it’s decline. " +
                            "Quit: When you will click on the button of “quit” it will terminate the countdown timer process and " +
                            "will lead you back to the home page. " +
                            "Skip: When you click on the button of “skip” It will fastforward and start the decline event " +
                            "immediately.");
                }});
                startActivity(intent);
            }
        });
    }

    public void startTimer(View view) {
        TextView txtHeadline = findViewById(R.id.txtHeadline);
        txtHeadline.setText("Time of Decline");
        Calendar calendar = Calendar.getInstance();
        if (declineTime == -1)
            calendar.setTimeInMillis(ApplicationData.getInstance().getMealTime());

        Log.i(TAG, String.format("onCreate: decline time is %s", android.text.format.DateFormat.format("hh:mm:ss a", (calendar.getTime()))));
        long diff = finalCalendar.getTime().getTime() - calendar.getTime().getTime();
        Log.i(TAG, String.format("startTimer: Diff is %s", diff));
        Toast.makeText(getApplicationContext(), "please wait, in" +
                "itializing incline...", Toast.LENGTH_SHORT).show();

        MyCount counter = new MyCount(diff, 1000);
        counter.start();

        btnStartTimer.setVisibility(View.GONE);
        connectWithDevice();
    }

    public class MyCount extends CountDownTimer {

        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            send("hello");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, SuccessActivity.class));
                }
            }, 2 * 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = (TimeUnit.MILLISECONDS.toDays(millis)) + "Day "
                    + (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)) + ":")
                    + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) + ":"
                    + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));

            txtHour.setText(String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis))));
            txtMinute.setText(String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
            txtSeconds.setText(String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
        }

    }


    private void connectWithDevice() {
        if (!getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getBoolean("demo_mode", false)) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startService();
                    }
                }, 2 * 1000);
            } else {
                startService();
            }
        } else {
            btnSkip.setVisibility(View.VISIBLE);
            btnQuit.setVisibility(View.VISIBLE);
        }
    }

    private void startService() {
        mChatService = new BluetoothChatService(this, mHandler);

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceAddress);
        // Attempt to connect to the device
        mChatService.connect(device, true);
    }

    private void disconnect() {
        try {
            if (mChatService != null) {
                mChatService.stop();
            }

            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            Log.d("status", "connected");
                            btnSkip.setVisibility(View.VISIBLE);
                            btnQuit.setVisibility(View.VISIBLE);
                            // send the protocol version to the server
                            //send("3," + Constants.PROTOCOL_VERSION + "," + Constants.CLIENT_NAME + "\n");
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            Log.d("status", "connecting");

                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            Log.d("status", "NONE/LISTEN");
                            Toast.makeText(context, "Disconnect or unable to connect", Toast.LENGTH_SHORT).show();
                            disconnect();
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readData = new String(readBuf, 0, msg.arg1);
                    // message received
                    //parseData(readData);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:

                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void parseData(String data) {
        //msg(data);

        // add the message to the buffer
        mInStringBuffer.append(data);

        // debug - log data and buffer
        //Log.d("data", data);
        //Log.d("mInStringBuffer", mInStringBuffer.toString());
        //msg(data.toString());

        // find any complete messages
        String[] messages = mInStringBuffer.toString().split("\\n");
        int noOfMessages = messages.length;
        // does the last message end in a \n, if not its incomplete and should be ignored
        if (!mInStringBuffer.toString().endsWith("\n")) {
            noOfMessages = noOfMessages - 1;
        }

        // clean the data buffer of any processed messages
        if (mInStringBuffer.lastIndexOf("\n") > -1)
            mInStringBuffer.delete(0, mInStringBuffer.lastIndexOf("\n") + 1);

        // process messages
        for (int messageNo = 0; messageNo < noOfMessages; messageNo++) {
            processMessage(messages[messageNo]);
        }

    }

    private void processMessage(String message) {
        // Debug
        // msg(message);
        String parameters[] = message.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        boolean invalidMessage = false;

        // Check the message
        if (parameters.length > 0) {
            // check length
            if (parameters.length == 5) {

                // set matrix
                if (parameters[0].equals("4")) {
                    if (!parameters[1].equals("")) {
                        try {
                            // convert color from #rrggbbaa to #aarrggbb
                            String color =
                                    parameters[1].substring(0, 1) +
                                            parameters[1].substring(7, 9) +
                                            parameters[1].substring(1, 7);


                        } catch (Exception i) {
                            invalidMessage = true;
                        }
                    }
                } else {
                    invalidMessage = true;
                }
            } else {
                invalidMessage = true;
            }
        } else {
            invalidMessage = true;
        }

        if (invalidMessage) {
            Toast.makeText(this, "Error - Invalid message received", Toast.LENGTH_SHORT).show();
        }
    }

    public void send(String message) {


        try {
            // Check that we're actually connected before trying anything
            if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
                Toast.makeText(this, "cant send message - not connected", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check that there's actually something to send
            if (message.length() > 0) {
                // Get the message bytes and tell the BluetoothChatService to write
                byte[] send = message.getBytes();
                mChatService.write(send);

                // Reset out string buffer to zero and clear the edit text field
                mOutStringBuffer.setLength(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
