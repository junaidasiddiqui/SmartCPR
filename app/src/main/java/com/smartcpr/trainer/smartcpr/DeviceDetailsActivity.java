package com.smartcpr.trainer.smartcpr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smartcpr.junaid.smartcpr.R;
import com.smartcpr.trainer.smartcpr.DeviceDetailsFragments.CalibrateButtonFragment;
import com.smartcpr.trainer.smartcpr.DeviceDetailsFragments.DeviceDetailsFragment;

import java.util.Objects;


/**
 * DeviceDetailsActivity Activity
 *
 * Second activity after user opens app
 * Calibrate Button takes user to next activity which calibrates the device
 *
 * Primary Functions
 *
 * onCreate: Unpacks bundled device details from ScanDevicesActivity
 *            and verifies the device details by showing the name and MAC address in large text
 * connectDevice: starts new activity for calibrating the accelerometer
 *
 */

@SuppressWarnings("ALL")
public class DeviceDetailsActivity extends AppCompatActivity implements
        CalibrateButtonFragment.CalibrateButtonListener {

    private final static String TAG = "DeviceDetailsActivity";

    public static final String EXTRA_BLUETOOTH_DEVICE_NAME
            = "com.smartcpr.junaid.smartcpr.bluetoothdevicename";

    public static final String EXTRA_BLUETOOTH_DEVICE_PHYSICAL_ADDRESS
            = "com.smartcpr.junaid.smartcpr.bluetoothdevicephysicaladdress";


    /**
     * onCreate
     *
     *
     * Method:
     *  on creating of activity, the details of the bluetooth device are unpacked via Intents
     *  and sent to the fragment which displays the details to the user
     *
     * Params:
     *   No specified parameters
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);

        //Unpacks bundled info from ScanDevicesActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String mBTDeviceName = Objects.requireNonNull(bundle).getString(EXTRA_BLUETOOTH_DEVICE_NAME);
        String mBTDevicePhysicalAddress = bundle.getString(EXTRA_BLUETOOTH_DEVICE_PHYSICAL_ADDRESS);

        Log.d(TAG, "onCreate: DeviceName "  + mBTDeviceName);
        Log.d(TAG, "onCreate: Address "  + mBTDevicePhysicalAddress);

        //Shows Device Details in UI
        DeviceDetailsFragment deviceDetailsFragment = (DeviceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_device_details);
        Log.d(TAG, deviceDetailsFragment.toString());
        deviceDetailsFragment.setDetailsText(mBTDeviceName, mBTDevicePhysicalAddress);

    }

    /**
     * connectDevice
     *
     * Method:
     *  starts new activity for calibrating the accelerometer
     *
     */
    @Override
    public void connectDevice() {

        Intent intent = new Intent(DeviceDetailsActivity.this,
                CalibrateIMUActivity.class);

        //TODO may have to bundle victim to next activity
        Log.d(TAG, "connectDevice: Starting CalibrateIMUActivity Activity");
        startActivity(intent);


    }

}
