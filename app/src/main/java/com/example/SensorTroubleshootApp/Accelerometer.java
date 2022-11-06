package com.example.SensorTroubleshootApp;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Accelerometer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Accelerometer extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txt_currentAccel, txt_prevAccel, txt_acceleration;
    ProgressBar prog_shakeMeter;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    public Accelerometer() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Accelerometer newInstance(String param1, String param2) {
        Accelerometer fragment = new Accelerometer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onViewCreated(v, savedInstanceState);
        prog_shakeMeter = (ProgressBar) v.findViewById(R.id.prog_shakeMeter);
        txt_acceleration = (TextView) v.findViewById(R.id.txt_accel);
        txt_currentAccel = (TextView) v.findViewById(R.id.txt_currentAccel);
        txt_prevAccel = (TextView) v.findViewById(R.id.txt_prevAccel);

        //initialize sensor objects
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return v;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z);
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            //update text values
            txt_currentAccel.setText("Current Acceleration = "+ (int) accelerationCurrentValue);
            txt_prevAccel.setText("Previous Acceleration = " + (int) accelerationPreviousValue);
            txt_acceleration.setText("Acceleration Change = " + (int) changeInAcceleration);

            prog_shakeMeter.setProgress((int)changeInAcceleration);

            if(changeInAcceleration > 14){
                prog_shakeMeter.setBackgroundColor(Color.RED);
            }else if(changeInAcceleration > 5){
                prog_shakeMeter.setBackgroundColor(Color.parseColor("#edc540"));
            }else if(changeInAcceleration > 2){
                prog_shakeMeter.setBackgroundColor(Color.YELLOW);
            }else{
                prog_shakeMeter.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_background));
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }; //object that listens for changes in accelerometer, triggers when sensor is used
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}