package com.skiwithuge.envimove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skiwithuge.envimove.R;

import java.util.Arrays;

/**
 * Created by skiwi on 3/19/18.
 */

public class BusStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        Intent intent = getIntent();
        int[] envIds = intent.getIntArrayExtra(MainActivity.ENVID);
        String s = Arrays.toString(envIds).replace(" ","")
                .replace("[","").replace("]","");
    }

}
