package com.mrfrogman.pedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv01;
    TextView tv02;
    Button btn01;
    int flg_x = 0;
    int flg_y = 0;
    int count_flg = 0;
    int count = 0;
    public static float acceler_x, acceler_y, acceler_z;
    private SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv01 = findViewById(R.id.tv01);
        tv02 = findViewById(R.id.tv02);
        btn01 = findViewById(R.id.btn01);
        btn01.setOnClickListener(v -> {
            count = 0;
            tv02.setText(String.format(Locale.JAPAN, "歩数%d", count++));
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acceler_x = event.values[0];
        acceler_y = event.values[1];
        acceler_z = event.values[2];
        tv01.setText(String.format("%s,%s,%s", acceler_x, acceler_y, acceler_z));

        count_flg = 0;
        if (acceler_x < 0){
            if (flg_x == 1) {
                count_flg = 1;
                flg_x = 0;
            }
        }else{
            if (flg_x == 0){
                count_flg = 1;
                flg_x = 1;
            }
        }
        if (acceler_y < 0){
            if (flg_y == 1) {
                count_flg = 1;
                flg_y = 0;
            }
        }else{
            if (flg_y == 0){
                count_flg = 1;
                flg_y = 1;
            }
        }

        if (count_flg == 1) {
            tv02.setText(String.format(Locale.JAPAN, "歩数%d", count++));
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1){

    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0){
            Sensor sensor = sensors.get(0);
            sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener((SensorListener) this);
    }
}