package com.smartcpr.trainer.smartcpr.CalibrateIMUFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcpr.junaid.smartcpr.R;

/**
 * CalibratedIMUFragment
 *
 *
 * Functions:
 * onCreateView: instantiates fragment layout and identifies textview to display message
 *
 * setCalibratingMessageFeedback: Displays message
 *      Params:
 *          deviceCalibrated - string that details the calibration status
 */

public class CalibratedIMUFragment extends Fragment {

    private static final String TAG = "CalibratedIMUFragment";
    private TextView mCalibratingMessage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_calibrating_imu_, container, false);
        Log.d(TAG, "onCreateView: ");
        mCalibratingMessage =  view.findViewById(R.id.calibration_result_feedback);

        return view;
    }

    public void setCalibratingMessageFeedback(String deviceCalibrated){
        mCalibratingMessage.setText(deviceCalibrated);


        Log.d(TAG, "setDetailsText: isDeviceCalibrated Message set");
    }

}
