package com.dotstudio.mujerjalisco;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {
    SharedPreferences data;
    public static String filename = "datos";
    int login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);

        data = getSharedPreferences(filename, 0);
        login = data.getInt("login", 0);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(login==0) {
                    Intent mainIntent = new Intent().setClass(ActivitySplash.this, CuentaActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent mainIntent = new Intent().setClass(ActivitySplash.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        };

        long splashDelay = 1000; //6s
        Timer timer1 = new Timer();
        timer1.schedule(task, splashDelay);
    }
}
