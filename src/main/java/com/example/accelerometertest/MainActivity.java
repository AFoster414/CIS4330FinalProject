package com.example.accelerometertest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_currentAccel, txt_prevAccel, txt_acceleration;
    ProgressBar prog_shakeMeter;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_acceleration = findViewById(R.id.txt_accel);
        txt_currentAccel = findViewById(R.id.txt_currentAccel);
        txt_prevAccel = findViewById(R.id.txt_prevAccel);

        prog_shakeMeter = findViewById(R.id.prog_ShakeMeter);

        //initialize sensor objects
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


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
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

}