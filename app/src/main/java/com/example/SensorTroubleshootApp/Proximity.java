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
    private int off, on;
    TextView statusBlock, statusInfo, proxTitle, txt_diagnosis;


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

        //Variables for tracking how long sensor was triggered/not triggered
        on = 0;
        off = 0;

        //setting up the different objects and matching them w/ layout elements such as text boxes
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        statusBlock = (TextView) v.findViewById(R.id.status_block);
        statusInfo = (TextView) v.findViewById(R.id.status_info);
        proxTitle = (TextView) v.findViewById(R.id.prox_title);
        txt_diagnosis = (TextView) v.findViewById(R.id.txt_diagnostic);

        //if the sensor is not available, then display a toast to say so
        if(proximitySensor == null){
            Toast.makeText(getActivity(), "Proximity Sensor not available!", Toast.LENGTH_SHORT).show();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //DATA EXTRACTION
                double data = sensorEvent.values[0];


                //FILTERING & FEATURE EXTRACTION
                if(data < proximitySensor.getMaximumRange()){//labelling data & updating text values
                    statusBlock.setText("aaaaaaaaaaaaaaaaaaaa");//filling up the block so we can get a bar of color
                    statusBlock.setTextColor(Color.RED);
                    statusBlock.setBackgroundColor(Color.RED);
                    statusInfo.setText("Near!");
                    statusInfo.setTextColor(Color.RED);
                    on++;//feature extraction, we want to know how long the sensor has been triggered
                }else{//labelling data & updating text values
                    statusBlock.setText("aaaaaaaaaaaaaaaaaaaa");//filling up the block so we can get a bar of color
                    statusBlock.setTextColor(Color.GREEN);
                    statusBlock.setBackgroundColor(Color.GREEN);
                    statusInfo.setText("Away!");
                    statusInfo.setTextColor(Color.GREEN);
                    off++;//feature extraction, we want to know how long the sensor has not been triggered
                }

                //CLASSIFICATION
                if(on == 0){//if the sensor has never been triggered yet
                    txt_diagnosis.setText("Sensor has not been triggered. Try doing so!");
                    txt_diagnosis.setTextColor(Color.YELLOW);
                }else if(on > off){//if sensor is triggered more than off, unlikely occurrence
                    txt_diagnosis.setText("Sensor keeps being triggered May not be working correctly.");
                    txt_diagnosis.setTextColor(Color.RED);
                }else{//the sensor is working normally
                    txt_diagnosis.setText("Working normally!");
                    txt_diagnosis.setTextColor(Color.GREEN);
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