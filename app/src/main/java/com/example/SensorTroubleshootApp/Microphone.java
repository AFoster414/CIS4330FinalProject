package com.example.SensorTroubleshootApp;
import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Microphone extends Fragment {

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private Button StartRecording, StopRecording, StartPlaying, StopPlaying;
    private String AudioSavePath = null;

    public Microphone() {
        // Required empty public constructor
    }

    public static Microphone newInstance() {
        Microphone fragment = new Microphone();
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
        View v = inflater.inflate(R.layout.fragment_microphone, container, false);
        super.onViewCreated(v, savedInstanceState);

        StartRecording = (Button) v.findViewById(R.id.recordButton);
        StopRecording = (Button) v.findViewById(R.id.stopRecordButton);
        StartPlaying = (Button) v.findViewById(R.id.playButton);
        StopPlaying = (Button) v.findViewById(R.id.stopPlayButton);

        StartRecording.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkPermissions() == true){
                    AudioSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"testrecording.mp3";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mediaRecorder.setOutputFile(AudioSavePath);

                    try{
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        StopRecording.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_SHORT).show();
            }
        });

        StartPlaying.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(AudioSavePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getActivity(), "Started Playing", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        StopPlaying.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    Toast.makeText(getActivity(), "Playing Stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    private boolean checkPermissions(){
        int first = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED && second == PackageManager.PERMISSION_GRANTED;
    }



    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "testRecordingFile" + ".mp3");
        return file.getPath();
    }
}