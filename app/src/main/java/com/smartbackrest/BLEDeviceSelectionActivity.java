package com.smartbackrest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BLEDeviceSelectionActivity extends AppCompatActivity {

    private static final String TAG = "BLEDeviceSelectionActiv";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 701;
    private Context context;
    private BluetoothAdapter myBluetooth;
    private ArrayList<BluetoothDevice> mDeviceList;
    private String mode;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");

            String action = intent.getAction();
            if (action != null) {
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDeviceList.add(device);
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                    mDeviceList = new ArrayList<>();
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                    if (mDeviceList != null && mDeviceList.size() > 0) {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        setUpRecyclerView();
                    } else {
                        startDeviceSearching();
                    }
                }
            }
        }
    };

    private void setUpRecyclerView() {
        RecyclerView bleDeviceRecycler = findViewById(R.id.bleDeviceRecycler);
        bleDeviceRecycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        final DeviceListAdpater adapter = new DeviceListAdpater();
        bleDeviceRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bledevice_selection);
        context = this;


        final Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("mode")) {
                    mode = bundle.getString("mode");
                }
            }
        }


        if (getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getBoolean("demo_mode", false)) {
            goToMealActivity();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");
        if (checkLocationPermission()) {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();

            if (myBluetooth == null) {
                Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                this.finish();
                System.exit(0);
            } else if (!myBluetooth.isEnabled()) {
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            } else {
                startDeviceSearching();
            }
        }
    }

    private void startDeviceSearching() {
        Log.i(TAG, "onResume: Searching.....");
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

        myBluetooth.startDiscovery();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                    Log.i(TAG, "onResume: Stopped Searching.....");
                    myBluetooth.cancelDiscovery();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    public boolean checkLocationPermission() {
        Log.d(TAG, "checkLocationPermission() called");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(true);
            return false;
        } else {
            boolean hasDeniedForPermission = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).getBoolean("dont_ask", false);
            if (hasDeniedForPermission) {
                requestPermission(true);
                return false;
            }
            return true;
        }
    }

    private void requestPermission(boolean goToSettings) {
        // Should we show an explanation?
        if (goToSettings) {
            new AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("Location permission required")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location permission")
                        .setMessage("Location permission required")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult() called with: requestCode = [" + requestCode + "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit().putBoolean("dont_ask", false).apply();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Log.i(TAG, "onRequestPermissionsResult: denied");
                        boolean showRationale = false;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            Log.i(TAG, "onRequestPermissionsResult: checked for M");
                            showRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                            if (!showRationale) {
                                Log.i(TAG, "onRequestPermissionsResult: not rationale");
                                // user also CHECKED "never ask again"
                                // you can either enable some fall back,
                                // disable features of your app
                                // or open another dialog explaining
                                // again the permission and directing to
                                // the app setting
                                getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit().putBoolean("dont_ask", true).apply();
                                checkLocationPermission();
                            } else {
                                checkLocationPermission();
                                // user did NOT check "never ask again"
                                // this is a good place to explain the user
                                // why you need the permission and ask if he wants
                                // to accept it (the rationale)
                            }
                        } else {
                            checkLocationPermission();
                        }
                    }
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit().putBoolean("dont_ask", false).apply();
            }
        }
    }


    class DeviceListAdpater extends RecyclerView.Adapter<DeviceListAdpater.DeviceViewHolder> {

        @NonNull
        @Override
        public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ble_device, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
            final BluetoothDevice bluetoothDevice = mDeviceList.get(position);
            holder.txtAddress.setText(bluetoothDevice.getAddress());
            String name = "";
            if (bluetoothDevice.getName() != null) {
                if (bluetoothDevice.getName().isEmpty()) {
                    name = "UNKNOWN";
                } else {
                    name = bluetoothDevice.getName();
                }
            } else {
                name = "UNKNOWN";
            }
            holder.txtName.setText(name);


            final String finalName = name;
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle(String.format("Save %s", bluetoothDevice.getName()))
                            .setMessage("Do you want to use same device every time?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, String.format("onClick: final name is %s", finalName));
                                    getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit()
                                            .putString("device_name", finalName).putString("address", bluetoothDevice.getAddress()).apply();

                                    goToMealActivity();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToMealActivity();
                                }
                            })
                            .setNeutralButton("Choose Another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDeviceList.size();
        }

        public class DeviceViewHolder extends RecyclerView.ViewHolder {

            private TextView txtName, txtAddress;
            private View mainLayout;


            public DeviceViewHolder(@NonNull View itemView) {
                super(itemView);
                txtName = itemView.findViewById(R.id.txtName);
                txtAddress = itemView.findViewById(R.id.txtAddress);
                mainLayout = itemView.findViewById(R.id.mainLayout);

            }
        }
    }

    private void goToMealActivity() {
        if (mode != null && mode.equals("expert_mode")) {
            startActivity(new Intent(context, TimerSetActivity.class));
        } else {
            startActivity(new Intent(context, MealActivity.class));

        }

    }
}
