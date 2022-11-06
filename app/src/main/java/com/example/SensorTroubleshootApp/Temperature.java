package com.example.SensorTroubleshootApp;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Temperature.
     */
    // TODO: Rename and change types and number of parameters
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
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            txt_DegreesCalc.setText(sensorEvent.values[0] + "degrees Celsius");
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