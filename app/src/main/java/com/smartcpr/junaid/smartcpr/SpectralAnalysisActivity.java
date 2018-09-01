package com.smartcpr.junaid.smartcpr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smartcpr.junaid.smartcpr.BluetoothData.ManageData;
import com.smartcpr.junaid.smartcpr.CalibrateIMUFragments.CompressionsButtonFragment;
import com.smartcpr.junaid.smartcpr.MathOperationsClasses.FastFourierTransform;
import com.smartcpr.junaid.smartcpr.MathOperationsClasses.SimpleMathOps;
import com.smartcpr.junaid.smartcpr.ObjectClasses.Complex;
import com.smartcpr.junaid.smartcpr.ObjectClasses.Victim;
import com.smartcpr.junaid.smartcpr.SpectralAnalysisFragments.CompressionRateFragment;

import java.util.ArrayList;
import java.util.Objects;

public class SpectralAnalysisActivity extends AppCompatActivity {

    private final static String TAG = "SpectralAnalysisActive";

    public static final String EXTRA_OFFSET_ACCELERATION_VALUE
            = "com.smartcpr.junaid.smartcpr.offsetaccelerationvalue";

    public static final String EXTRA_VICTIM_MIN_DEPTH
            = "com.smartcpr.junaid.smartcpr.minvictimdepth";

    public static final String EXTRA_VICTIM_MAX_DEPTH
            = "com.smartcpr.junaid.smartcpr.maxvictimdepth";

    private static float offsetAcceleration;

    private int txyz;
    private int desiredListSizeForCompression;

    private float minVictimDepth;
    private float maxVictimDepth;

    private float[] time;
    private float[] acceleration;

    CompressionRateFragment compressionRateFragment;

    Intent intent;
    Bundle bundle;

    private String getOffsetValue() { return Objects.requireNonNull(bundle).getString(EXTRA_OFFSET_ACCELERATION_VALUE); }
    private String getMinDepth() { return Objects.requireNonNull(bundle).getString(EXTRA_VICTIM_MIN_DEPTH);  }
    private String getMaxDepth() { return Objects.requireNonNull(bundle).getString(EXTRA_VICTIM_MAX_DEPTH); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectral_analysis);

        intent = getIntent();
        bundle = intent.getExtras();

        offsetAcceleration = Float.parseFloat(getOffsetValue());

        Log.d(TAG, "onCreate: "  + getMinDepth());
        Log.d(TAG, "onCreate: " + getMaxDepth());

        minVictimDepth = Float.parseFloat(getMinDepth());
        maxVictimDepth = Float.parseFloat(getMaxDepth());

        txyz = getResources().getInteger(R.integer.array_index_txyz);
        desiredListSizeForCompression = getResources().getInteger(R.integer.compression_list_size);

    }


    private void getIMUData() {
        ArrayList<float[]> formattedDataFromDevice =  ManageData.getData(desiredListSizeForCompression);

        float[][] accelerometerRawData = formattedDataFromDevice.toArray(new float[][]
                {new float[formattedDataFromDevice.size()]});


        acceleration = ManageData.getAccelerationFromRawData(accelerometerRawData, txyz);
        time = ManageData.getScaledTimeArray(accelerometerRawData);


        if (offsetAcceleration == 1){

        }
    }

    private void performSpectralAnalysis(float[] time, float[] acceleration) {
        int N = time.length;

        //May not need this thing
        N = FastFourierTransform.setArraySizeExponentOfTwo(N);
        Log.d(TAG, "performSpectralAnalysis: Array Size " + N);


        double hanningAppliedValues[] = SimpleMathOps.applyHanningWindow(acceleration, N);


        Complex[] baseComplexArray = FastFourierTransform.baseComplexArrayWithWindow(hanningAppliedValues, N);
        Complex[] complexArrayFFTValues = FastFourierTransform.simpleFFT(baseComplexArray);

        Complex[] fftPolarSingle = FastFourierTransform.fftDoubleToSingle(complexArrayFFTValues, N, 2);
        double[] fftSmooth = FastFourierTransform.smoothFFTValues(fftPolarSingle, N);


        //        double[] amplitudes = MathOps.peaksFromTransform(fftSmooth, peaks);
        //
        //        double[] thetaAngles = MathOps.phaseAngles(peaks, fftPolarSingle);
        //        double fundamentalFrequency = MathOps.getfundamentalFrequency(peaks, freqBins);
        //
        //        double depth = MathOps.compressionDepth(amplitudes, peaks.length, scaledTime, fundamentalFrequency, thetaAngles);
        //        double rate = MathOps.compressionRate(fundamentalFrequency);

    }


}
