package com.example.SensorTroubleshootApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Temperature#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Temperature extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    TextView txt_DegreesCalc;
    private SensorManager mSensorManager;
    private Sensor mTempSensor;
    Boolean isTemperatureSensorAvailable;

    public Temperature() {
        // Required empty public constructor
    }

    public static Temperature newInstance(String param1, String param2) {
        Temperature fragment = new Temperature();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_temperature, container, false);
        txt_DegreesCalc = (TextView) v.findViewById(R.id.degrees);
        //initialize sensor objects
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        }else{
            txt_DegreesCalc.setText("Temperature Sensor not available");
            isTemperatureSensorAvailable = false;
        }

        return v;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //DATA EXTRACTION
            double temperature = sensorEvent.values[0];


            //CLASSIFICATION
            txt_DegreesCalc.setText(temperature + "degrees Celsius");
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(isTemperatureSensorAvailable) {
            mSensorManager.registerListener(sensorEventListener, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if(isTemperatureSensorAvailable) {
            mSensorManager.unregisterListener(sensorEventListener);
        }
    }
}