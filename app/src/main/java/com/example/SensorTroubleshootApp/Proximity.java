package com.example.SensorTroubleshootApp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;


public class Proximity extends Fragment {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    TextView statusBlock, statusInfo, proxTitle;


    public Proximity() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Proximity newInstance() {
        Proximity fragment = new Proximity();
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
        View v = inflater.inflate(R.layout.fragment_proximity, container, false);
        super.onViewCreated(v, savedInstanceState);

        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        statusBlock = (TextView) v.findViewById(R.id.status_block);
        statusInfo = (TextView) v.findViewById(R.id.status_info);
        proxTitle = (TextView) v.findViewById(R.id.prox_title);

        if(proximitySensor == null){
            Toast.makeText(getActivity(), "Proximity Sensor not available!", Toast.LENGTH_SHORT).show();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    statusBlock.setText("aaaaaaaaaaaaaaaaaaaa");//filling up the block so we can get a bar of color
                    statusBlock.setTextColor(Color.RED);
                    statusBlock.setBackgroundColor(Color.RED);
                    statusInfo.setText("Near!");
                    statusInfo.setTextColor(Color.RED);
                }else{
                    statusBlock.setText("aaaaaaaaaaaaaaaaaaaa");//filling up the block so we can get a bar of color
                    statusBlock.setTextColor(Color.GREEN);
                    statusBlock.setBackgroundColor(Color.GREEN);
                    statusInfo.setText("Away!");
                    statusInfo.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i){

            }
        };
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000);


        return v;
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}