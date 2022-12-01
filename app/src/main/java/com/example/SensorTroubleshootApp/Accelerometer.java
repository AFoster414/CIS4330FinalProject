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


public class Accelerometer extends Fragment {

    TextView txt_currentAccel, txt_prevAccel, txt_acceleration, txt_maxAccel, accelStatus;
    ProgressBar prog_shakeMeter;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private double accelerationCurrentValue, accelerationPreviousValue,currentMax;


    public Accelerometer() {
        // Required empty public constructor
    }

    public static Accelerometer newInstance() {
        Accelerometer fragment = new Accelerometer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentMax = 0.00;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onViewCreated(v, savedInstanceState);
        prog_shakeMeter = (ProgressBar) v.findViewById(R.id.prog_shakeMeter);
        txt_acceleration = (TextView) v.findViewById(R.id.txt_accel);
        txt_currentAccel = (TextView) v.findViewById(R.id.txt_currentAccel);
        txt_prevAccel = (TextView) v.findViewById(R.id.txt_prevAccel);
        txt_maxAccel = (TextView) v.findViewById(R.id.maxAccel);
        accelStatus = (TextView) v.findViewById(R.id.accel_status);

        //initialize sensor objects
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return v;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //SENSOR DATA EXTRACTION
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //FILTERING / FEATURE EXTRACTION
            //since we want the whole sensor reading, not much filtering is done (or any at all)
            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z);
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            accelerationCurrentValue = Math.ceil(accelerationCurrentValue * 10000) / 10000;
            accelerationPreviousValue = Math.ceil(accelerationPreviousValue * 10000) / 10000;
            changeInAcceleration = Math.ceil(changeInAcceleration * 10000) / 10000;

            //update text values / labelling data
            txt_currentAccel.setText("Current Acceleration = "+ accelerationCurrentValue);
            txt_prevAccel.setText("Previous Acceleration = " + accelerationPreviousValue);
            txt_acceleration.setText("Acceleration Change = " + changeInAcceleration);

            if(accelerationCurrentValue > currentMax){
                currentMax = accelerationCurrentValue;
                txt_maxAccel.setText("Max: " + (double) currentMax);
            }

            //CLASSIFICATION

            //GROUND TRUTH: -bc of gravity, accelerometer data should always be (at least) around 9.81 m/s
            //              -if the sensor is not working, it will produce a value of nul, 0, or 130+ (during testing, the max value we could obtain was just under 130 m/s)
            if(accelerationCurrentValue < 9.81 && accelerationCurrentValue > 7.35){ //if gathered sensor data is in the top quartile but below the assumed minimum
                accelStatus.setText("Sensor may not be working perfectly.");
                accelStatus.setTextColor(Color.YELLOW);
            }else if(accelerationCurrentValue == 0){//if sensor is not gathering data at all
                accelStatus.setText("Sensor is not working!");
                accelStatus.setTextColor(Color.RED);
            }else if(accelerationCurrentValue < 7.35 || accelerationCurrentValue >= 130){//if the sensor readings are not within the top quartile
                accelStatus.setText("Sensor may be damaged!");
                accelStatus.setTextColor(Color.parseColor("#edc540"));
            }else{//if gathered data is following the average and ground truth
                accelStatus.setText("Sensor is working correctly!");
                accelStatus.setTextColor(Color.GREEN);
            }

            //updating shake meter
            prog_shakeMeter.setProgress((int)changeInAcceleration);
            if(changeInAcceleration > 14){
                prog_shakeMeter.setBackgroundColor(Color.RED);
            }else if(changeInAcceleration > 5){
                prog_shakeMeter.setBackgroundColor(Color.parseColor("#edc540"));
            }else if(changeInAcceleration > 2){
                prog_shakeMeter.setBackgroundColor(Color.YELLOW);
            }else{
                prog_shakeMeter.setBackgroundColor(getResources().getColor(R.color.white));
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